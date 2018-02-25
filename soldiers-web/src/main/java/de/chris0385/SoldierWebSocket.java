package de.chris0385;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.chris0385.api.commands.Command;
import de.chris0385.api.messages.Message;
import de.chris0385.api.messages.WorldUpdateMessage;
import de.chris0385.api.model.World;
import de.chris0385.components.EntityFactory;
import de.chris0385.lobby.Client;
import de.chris0385.lobby.ClientUpdateListener;
import de.chris0385.lobby.Lobby;

@WebSocket
public class SoldierWebSocket {
	
	private static final TypeReference<List<Command>> TYPE_LIST_OF_COMMAND = new TypeReference<List<Command>>() {
	};

	private static final Logger LOG = LoggerFactory.getLogger(SoldierWebSocket.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private final Lobby lobby;
	private ConcurrentHashMap<Session, Client> clients = new ConcurrentHashMap<>();
	
	/**
	 * Sends the response
	 */
	private class ClientCallback implements ClientUpdateListener {
		private final Session session;

		public ClientCallback(Session session) {
			this.session = session;
		}
		
		private void sendMessage(String message) {
			/*
			 * We could use a customized ObjectMapper to filter out what should be visible to each player.
			 */
			if (session.isOpen()) {
				try {
					session.getRemote().sendString(message);
				} catch (IOException e) {
					LOG.info("Exception while sending to client", e);
				}
			}
		}

		@Override
		public Future<Boolean> worldUpdate(World world) {
			WorldUpdateMessage message = new WorldUpdateMessage();
			message.world = world;
			return sendMessageAsync(message);
		}

		private CompletableFuture<Boolean> sendMessageAsync(WorldUpdateMessage message) {
			return CompletableFuture.supplyAsync(() -> {
				sendMessage(message);
				return Boolean.TRUE;
			});
		}

		private void sendMessage(Message message) {
			try {
				String json = mapper.writeValueAsString(message);
				sendMessage(json);
			} catch (JsonProcessingException e) {
				LOG.warn("Exception while sending to client", e);
			}
		}
	}

	public SoldierWebSocket() {
		LOG.info("Created SoldierWebSocket");
		lobby = SoldiersGame.get().getLobby();
	}
	
	@OnWebSocketMessage
	public void onText(Session session, String message) {
		try {
			LOG.debug("Message received:" + message + " thread: " + Thread.currentThread());
			List<Command> commands = mapper.readValue(message, TYPE_LIST_OF_COMMAND);
			Client client = getClient(session);
			client.setCommands(commands);
		} catch (JsonParseException | JsonMappingException e) {
			// TODO: signal to client
			LOG.debug("JSon exception when receiving message from client", e);
		} catch (IOException e) {
			LOG.warn("IOException when receiving message from client", e);
		} catch (RuntimeException e) {
			LOG.error("Unexpected error when processing message from client", e);
		}
	}

	private Client getClient(Session session) {
		return clients.get(session);
	}

	@OnWebSocketConnect
	public void onConnect(Session session) throws IOException {
		// TODO: security and IP banning? 
		
		LOG.info(session.getRemoteAddress().getHostString() + " connected!");
		Client client = lobby.createClient();
		clients.put(session, client);
		client.addClientUpdateListener(new ClientCallback(session));
		// session.getPolicy().
	}

	@OnWebSocketClose
	public void onClose(Session session, int status, String reason) {
		LOG.info(session.getRemoteAddress().getHostString() + " closed!");
		getClient(session).dispose();
		clients.remove(session);
	}

}