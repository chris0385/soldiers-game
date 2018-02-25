package de.chris0385.lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.chris0385.api.commands.Command;
import de.chris0385.api.model.World;
import de.chris0385.game.Game;

/**
 * A client represents a connection to the client.
 * It is distinct to a player insofar that the client may also be a simple observer.
 * 
 * A single person may also have multiple open connections open with the server.
 */
public class Client {
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);

	private List<Command> commands;
	private List<ClientUpdateListener> clientUpdateListeners= new ArrayList<>();
	
	public void dispose() {
		// TODO Auto-generated method stub
		// disconnected
	}
	
	public void addClientUpdateListener(ClientUpdateListener listener) {
		clientUpdateListeners.add(listener);
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

	public void sendWorld(World world) {
		for (int i = 0; i < clientUpdateListeners.size(); i++) {
			ClientUpdateListener listener = clientUpdateListeners.get(i);
			Future<Boolean> future = listener.worldUpdate(world);
			
			try {
				future.get(); // TODO: parallel async
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return; 
			} catch (ExecutionException e) {
				LOG.info(e.getMessage(), e);
			} 
		}
	}
	


}
