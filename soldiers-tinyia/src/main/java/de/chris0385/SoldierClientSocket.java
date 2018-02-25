package de.chris0385;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.chris0385.api.commands.Command;
import de.chris0385.api.messages.Message;

@WebSocket
public class SoldierClientSocket implements AutoCloseable {

	private static final TypeReference TYPE = new TypeReference<List<Command>>() {
			};
	private Session session; 
	private ObjectMapper mapper = new ObjectMapper();
	private final WebSocketClient client;
	private Consumer<Message> callback;

	private SoldierClientSocket(WebSocketClient client) {
		this.client = client;
	}
	
	public static SoldierClientSocket createConnected(String dest) throws Exception {
		return buildSocket(dest);
	}
	
	public void setCallback(Consumer<Message> callback) {
		this.callback = callback;
		
	}
	
	private static SoldierClientSocket buildSocket(String dest)
			throws Exception {
		WebSocketClient client = new WebSocketClient();
//		client.getExtensionFactory().register("permessage-deflate", PerMessageDeflateExtension.class);
		client.start();
		SoldierClientSocket socket = new SoldierClientSocket(client);
		URI serverURI = new URI(dest);
		ClientUpgradeRequest request = new ClientUpgradeRequest();
		request.addExtensions("permessage-deflate");
		client.connect(socket, serverURI, request).get();
		return socket;
	}

	@OnWebSocketMessage
	public void onText(Session session, String messageS) throws IOException {
		Message message = mapper.readValue(messageS, Message.class);
		try {
			if (callback != null) {
				callback.accept(message);
			} else {
				System.out.println("Message received from server without callback:" + messageS);
			}
		} catch (Exception e) {
			System.err.println("Error processing " + messageS);
			System.err.println("Error is " + e);
			e.printStackTrace();
		}
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		System.out.println("Connected to server");
		this.session = session;
	}

	private void sendMessage(String str) {
		try {
			session.getRemote().sendString(str);
		} catch (IOException e) {
			System.err.println("Connection error: " + e);
			e.printStackTrace();
		}
	}
	
	public void sendCommands(List<Command> o) {
		try {
			sendMessage(mapper.writerFor(TYPE).writeValueAsString(o));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		client.stop();
	}

}
