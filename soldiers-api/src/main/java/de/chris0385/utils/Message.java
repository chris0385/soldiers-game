package de.chris0385.utils;

/**
 * Can be used for general communication with the player,
 * or give automated warnings that some commands were ignored (like trying to move a structure).
 */
public class Message {
	
	public enum MessageType {
		INFO, WARNING, ERROR;
	}

	private MessageType type;
	private String message;
}
