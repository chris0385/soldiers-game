package de.chris0385;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import de.chris0385.api.commands.MoveCommand;
import de.chris0385.api.model.Id;

public class SoldierClient {

	
	public static void main(String[] args) throws Exception {
		WebSocketClient client = new WebSocketClient();
		try {
			
			SoldierClientSocket socket = buildSocket(client);
			
			socket.sendObject(new MoveCommand(new Id("foo"), null));
			socket.sendObject(new MoveCommand(new Id("bar"), null));
			
			Thread.sleep(10000l);

		} finally {
			try {
				client.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static SoldierClientSocket buildSocket(WebSocketClient client)
			throws Exception, URISyntaxException, IOException, InterruptedException {
		String dest = "ws://localhost:8080/soldier";
		SoldierClientSocket socket = new SoldierClientSocket();
		client.start();
		URI echoUri = new URI(dest);
		ClientUpgradeRequest request = new ClientUpgradeRequest();
		client.connect(socket, echoUri, request);
		socket.getLatch().await();
		return socket;
	}
}
