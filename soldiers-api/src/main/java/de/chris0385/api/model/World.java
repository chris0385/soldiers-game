package de.chris0385.api.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import de.chris0385.api.commands.Command;
import de.chris0385.utils.ObjectRegistry;

public class World {

	private List<Unit> units;
	private List<Structure> structures;
	
	// TODO: bullets+grenades... to permit dodging
	private List<Object> bullets;

	// ** Metadata **
	private List<ObjectType> types;
	private List<Player> players;
	
	//  TODO
	private List<Command> activeCommands;
	
	/**
	 * Errors, warnings, messages from admin,...
	 */
	private List<Object> messages;

	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		aInputStream.defaultReadObject();
		types.forEach(ObjectRegistry.INSTANCE::registerType);
		players.forEach(ObjectRegistry.INSTANCE::registerPlayer);
	}
}
