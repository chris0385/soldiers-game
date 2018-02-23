package de.chris0385;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.chris0385.components.EntityFactory;
import de.chris0385.lobby.Client;
import de.chris0385.lobby.ClientUpdateListener;
import de.chris0385.lobby.Lobby;

@WebSocket
public class SoldierWebSocket {
	
	private static final Logger LOG = LoggerFactory.getLogger(EntityFactory.class);
	
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
		
		private void sendMessage(String message) throws IOException {
			/*
			 * We could use a customized ObjectMapper to filter out what should be visible to each player.
			 */
			if (session.isOpen()) {
				session.getRemote().sendString(message);
			}
		}
	}

	public SoldierWebSocket() {
		LOG.info("Created SoldierWebSocket");
		lobby = SoldiersGame.get().getLobby();
	}
	
	@OnWebSocketMessage
	public void onText(Session session, String message) throws IOException {
		System.out.println("Message received:" + message + " thread: " + Thread.currentThread());
		Client client = getClient(session);
		throw new RuntimeException(); // TODO
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