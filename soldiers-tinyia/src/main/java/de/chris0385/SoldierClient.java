package de.chris0385;

import java.util.Arrays;

import de.chris0385.api.commands.MoveCommand;
import de.chris0385.api.model.Id;

public class SoldierClient {

	public static void main(String[] args) throws Exception {

		try (SoldierClientSocket socket = SoldierClientSocket.createConnected("ws://localhost:5643/soldier")) {

			socket.sendCommands(Arrays.asList(new MoveCommand(new Id("foo"), null)));
			socket.sendCommands(Arrays.asList(new MoveCommand(new Id("bar"), null)));
			// socket.sendObject(new MoveCommand(new Id("bar"), null));

			Thread.sleep(10000l);

		}
	}

}
