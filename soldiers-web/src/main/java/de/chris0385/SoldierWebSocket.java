package de.chris0385;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class SoldierWebSocket {
	
	{
		System.out.println("Created SoldierWebSocket");
	}
	
	@OnWebSocketMessage
	public void onText(Session session, String message) throws IOException {
		System.out.println("Message received:" + message + " thread: " + Thread.currentThread());
	}

	@OnWebSocketConnect
	public void onConnect(Session session) throws IOException {
		System.out.println(session.getRemoteAddress().getHostString() + " connected!");
	}

	@OnWebSocketClose
	public void onClose(Session session, int status, String reason) {
		System.out.println(session.getRemoteAddress().getHostString() + " closed!");
	}

}