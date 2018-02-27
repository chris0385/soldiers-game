package de.chris0385.api.messages;

import de.chris0385.api.model.World;

public final class WorldUpdateMessage extends Message {
	public static final String ID = "UPDATE";

	@Override
	public MessageType getMessageType() {
		return MessageType.UPDATE;
	}

	public World world;
}
