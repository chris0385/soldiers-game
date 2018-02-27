package de.chris0385.api.messages;

public final class InfoMessage extends Message {
	public static final String ID = "INFO";

	@Override
	public MessageType getMessageType() {
		return MessageType.INFO;
	}
	
	public InfoMessage() {
		// default
	}
	
	public InfoMessage(String message) {
		this.message = message;
	}

	public String message;

}
