package de.chris0385.api.commands;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class LoginCommand extends Command {
	public static final String ID = "LOGIN";
	
	@Override
	public String getCommandName() {
		return ID;
	}

	@JsonProperty(required = true)
	public String name;

	@JsonProperty(required = true)
	public String password;

	@JsonProperty(required = false)
	public String tag;
	
	@Override
	public String toString() {
		return name + (tag != null ? " (" + tag + ")" : "");
	}
}
