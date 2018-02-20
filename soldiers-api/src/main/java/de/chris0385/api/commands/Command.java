package de.chris0385.api.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import de.chris0385.api.model.Id;

@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "cmd")
@JsonSubTypes({ //
		@JsonSubTypes.Type(value = BuildCommand.class, name = "BUILD"), //
		@JsonSubTypes.Type(value = MoveCommand.class, name = "MOVE"),//
		@JsonSubTypes.Type(value = ShootCommand.class, name = "SHOOT"),//
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Command {

	private final Id objectId;

	protected Command() {
		// default
		objectId = null;
	}

	protected Command(Id objectId) {
		this.objectId = objectId;
	}
	
	public Id getObjectId() {
		return objectId;
	}
}
