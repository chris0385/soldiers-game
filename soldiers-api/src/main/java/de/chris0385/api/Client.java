package de.chris0385.api;

import java.util.List;

import de.chris0385.api.commands.Command;
import de.chris0385.api.model.World;

/**
 * Important: Method overloading is only allowed if the number of parameter differs.
 * Otherwise, JSON-RPC-deserialization won't work properly.
 */
public interface Client {
	
	void register(String name, String password);
	void login(String name, String password, String tag);

	void sendCommands(List<Command> commands);
	
	/**
	 * 
	 * TODO: push to client?
	 */
	World getWorld();
}
