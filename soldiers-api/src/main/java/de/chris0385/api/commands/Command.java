package de.chris0385.api.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "cmd")
@JsonSubTypes({ //
		// --- Command on Objects ---
		@JsonSubTypes.Type(value = BuildCommand.class, name = BuildCommand.ID), //
		@JsonSubTypes.Type(value = MoveCommand.class, name = MoveCommand.ID), //
		@JsonSubTypes.Type(value = ShootCommand.class, name = ShootCommand.ID),//
		// --- Control Commands ---
		@JsonSubTypes.Type(value = LoginCommand.class, name = LoginCommand.ID), //
		@JsonSubTypes.Type(value = RegisterCommand.class, name = RegisterCommand.ID), //
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Command {
	

	@JsonProperty("cmd")
	public abstract String getCommandName();
	
	protected Command() {
	}
	
}
