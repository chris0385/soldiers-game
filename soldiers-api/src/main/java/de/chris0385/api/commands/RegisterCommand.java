package de.chris0385.api.commands;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class RegisterCommand extends Command {
	public static final String ID = "REGISTER";

	@Override
	public CommandType getCommandName() {
		return CommandType.REGISTER;
	}
	
	@JsonProperty(required = true)
	public String name;

	@JsonProperty(required = true)
	public String password;

}
