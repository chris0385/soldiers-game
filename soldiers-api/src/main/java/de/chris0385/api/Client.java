package de.chris0385.api;

import java.util.List;

import de.chris0385.api.commands.Command;
import de.chris0385.api.model.World;

/**
 * 
 */
public interface Client {
	
	void register(String name, String password);
	void login(String name, String password, String tag);

	/**
	 * TODO: Json-Serialization of abstract classes?
	 */
	void sendCommands(List<Command> commands);
	
	World getWorld();
}
