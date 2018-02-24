package de.chris0385.api.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import de.chris0385.api.commands.Command;
import de.chris0385.api.model.meta.ObjectType;
import de.chris0385.api.model.meta.Player;
import de.chris0385.api.model.meta.StructureType;
import de.chris0385.api.model.meta.TileMap;
import de.chris0385.api.model.meta.UnitType;
import de.chris0385.utils.ObjectRegistry;

public class World {

	private List<Unit> units;
	private List<Structure> structures;
	
	// TODO: bullets+grenades... to permit dodging
	private List<Object> bullets;

	// ** Metadata **
	private List<UnitType> unitTypes;
	private List<StructureType> structureTypes;
	private List<Player> players;
	private List<Sector> sectors;
	private TileMap tiles;
	
	//  TODO
	private List<Command> activeCommands;
	
	/**
	 * Errors, warnings, messages from admin,...
	 */
	private List<Object> messages;
	
	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		// FIXME: this doesn't work for JSON
		aInputStream.defaultReadObject();
		unitTypes.forEach(ObjectRegistry.INSTANCE::registerType);
		structureTypes.forEach(ObjectRegistry.INSTANCE::registerType);
		players.forEach(ObjectRegistry.INSTANCE::registerPlayer);
	}
}
