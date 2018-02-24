package de.chris0385.lobby;

import java.util.List;

import de.chris0385.api.commands.Command;

/**
 * A client represents a connection to the client.
 * It is distinct to a player insofar that the client may also be a simple observer.
 * 
 * A single person may also have multiple open connections open with the server.
 */
public class Client {

	private List<Command> commands;
	
	public void dispose() {
		// TODO Auto-generated method stub
		// disconnected
	}
	
	public void addClientUpdateListener(ClientUpdateListener listener) {
		// TODO
	}

	/**
	 * Async processing
	 */
	public synchronized void setCommands(List<Command> commands) {
//		for (Command command : commands) {
//			if(command instanceof CommandOnObject) {
//				CommandOnObject cmdOnObject = (CommandOnObject) command;
//				if(cmdOnObject.getObjectId())
//			}
//		}
		this.commands = commands;
		notifyAll();
	}
	
	public synchronized List<Command> takeCommands(long timeout) {
		if (commands == null && timeout > 0) {
			long endTime = System.currentTimeMillis() + timeout;
			while (commands == null) {
				long waitTime = endTime - System.currentTimeMillis();
				if (waitTime <= 0) {
					break;
				}
				try {
					wait(waitTime);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return null;
				}
			}
		}
		List<Command> result = commands;
		commands = null;
		return result;
	}
	


}
