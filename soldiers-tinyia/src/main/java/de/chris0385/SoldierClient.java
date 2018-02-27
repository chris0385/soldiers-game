package de.chris0385;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

import de.chris0385.api.commands.MoveCommand;
import de.chris0385.api.messages.Message;
import de.chris0385.api.model.Id;

public class SoldierClient implements Consumer<Message> {

	public static void main(String[] args) throws Exception {
		
		new SoldierClient().run();

		
	}

	private Message message;

	private void run() throws Exception {
		//5643 9123
		try (SoldierClientSocket socket = SoldierClientSocket.createConnected("ws://localhost:5643/soldier")) {

//			socket.sendCommands(Arrays.asList(new MoveCommand(new Id("foo"), null)));
//			socket.sendCommands(Arrays.asList(new MoveCommand(new Id("bar"), null)));
			// socket.sendObject(new MoveCommand(new Id("bar"), null));

			socket.setCallback(this);
			
			for (int i = 0; i < 100; i++) {
				long before = System.currentTimeMillis();
				socket.sendCommands(Collections.singletonList(null));
				System.out.println("Sent at " + before);

				synchronized (this) {
					while (message == null) {
						wait();
					}
				}
				System.err.println("Roundtriuip : " + (System.currentTimeMillis() - before) + "ms");
				message = null;
			}
			
			Thread.sleep(10000l);

		}
	}

	@Override
	public synchronized void accept(Message t) {
		System.out.println("Received at "+System.currentTimeMillis());
		message = t;
		notifyAll();
	}

}
