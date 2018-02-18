package de.chris0385;

import java.net.URI;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.Test;

import de.chris0385.api.commands.MoveCommand;
import de.chris0385.api.model.Id;

public class SoldierClient {

	@Test
	public void test() throws Exception {
		String dest = "ws://localhost:8080/soldier";
		WebSocketClient client = new WebSocketClient();
		try {
			
			SoldierClientSocket socket = new SoldierClientSocket();
			client.start();
			URI echoUri = new URI(dest);
			ClientUpgradeRequest request = new ClientUpgradeRequest();
			client.connect(socket, echoUri, request);
			socket.getLatch().await();
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
}
