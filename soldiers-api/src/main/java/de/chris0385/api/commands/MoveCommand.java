package de.chris0385.api.commands;

import de.chris0385.api.model.Id;
import de.chris0385.api.model.Position;

public final class MoveCommand extends CommandOnObject {
	
	public static final String ID = "MOVE";

	@Override
	public CommandType getCommandName() {
		return CommandType.MOVE;
	}
	
	private MoveCommand() {
		// default for deserialization
		targetPosition = null;
	}
	
	public MoveCommand(Id objectId, Position targetPosition) {
		super(objectId);
		this.targetPosition = targetPosition;
	}

	private final Position targetPosition;
	
	public Position getTargetPosition() {
		return targetPosition;
	}

	@Override
	public String toString() {
		return "MoveCommand [targetPosition=" + targetPosition + ", objectId=" + objectId +"]";
	}
	
}
