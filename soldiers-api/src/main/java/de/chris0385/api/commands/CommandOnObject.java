package de.chris0385.api.commands;

import de.chris0385.api.model.Id;


public abstract class CommandOnObject extends Command {

	final Id objectId;

	protected CommandOnObject() {
		// Default, for deserialization
		objectId = null;
	}
	
	protected CommandOnObject(Id objectId) {
		this.objectId = objectId;
	}

	public Id getObjectId() {
		return objectId;
	}
}
