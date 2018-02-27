package de.chris0385.api.commands;

import de.chris0385.api.model.Id;

public final class BuildCommand extends CommandOnObject {
	public static final String ID = "BUILD";

	private BuildCommand() {
		// Default for deserialisation
	}
	
	public BuildCommand(Id objectId) {
		super(objectId);
	}
	
	@Override
	public String getCommandName() {
		return ID;
	}

	private String typeToBuild;
}
