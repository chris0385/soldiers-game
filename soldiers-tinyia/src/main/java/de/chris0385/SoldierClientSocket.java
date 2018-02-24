package de.chris0385;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.chris0385.api.commands.Command;

@WebSocket
public class SoldierClientSocket {

	private Session session;
	private ObjectMapper mapper = new ObjectMapper();


	@OnWebSocketMessage
	public void onText(Session session, String message) throws IOException {
		System.out.println("Message received from server:" + message);
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		System.out.println("Connected to server");
		this.session = session;
	}

	private void sendMessage(String str) {
		try {
			System.out.println("Send "+str);
			session.getRemote().sendString(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendCommands(List<Command> o) {
		try {
			sendMessage(mapper.writerFor(new TypeReference<List<Command>>() {
			}).writeValueAsString(o));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		
	}
	public void sendObject(Object o) {
		try {
			sendMessage(mapper.writeValueAsString(o));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
