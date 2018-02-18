package de.chris0385.api.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import de.chris0385.api.model.Id;

@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CUSTOM, include = As.PROPERTY, property = "type")
@JsonSubTypes({ //
		@JsonSubTypes.Type(value = BuildCommand.class, name = "BUILD"), //
		@JsonSubTypes.Type(value = MoveCommand.class, name = "MOVE"),//
})
public abstract class Command {

	private Id objectId;

}
