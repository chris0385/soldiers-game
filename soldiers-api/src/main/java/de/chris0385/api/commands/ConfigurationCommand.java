package de.chris0385.api.commands;

import java.util.Map;

/**
 * Some idea.
 * TODO: discuss
 */
public final class ConfigurationCommand extends Command {

	public enum ConfKey {
		/**
		 * Configure if the server should send players in the World (Doesn't change)
		 */
		SEND_PLAYERS,
		/**
		 * Doesn't change.
		 */
		SEND_MAP_TILES,
		/**
		 * Doesn't change.
		 */
		SEND_OBJECT_TYPE_INFO,
		/**
		 * Don't want to play, just look.
		 */
		SET_OBSERVER_MODE,
	}
	public ConfigurationCommand() {
	}

	private Map<ConfKey, Object> conf;
	
}
