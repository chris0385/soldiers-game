package de.chris0385.api.commands;

import de.chris0385.api.model.Id;
import de.chris0385.api.model.Position;

public final class MoveCommand extends Command {

	public MoveCommand(Id objectId, Position targetPosition) {
		super(objectId);
		this.targetPosition = targetPosition;
	}

	private final Position targetPosition;
	
	public Position getTargetPosition() {
		return targetPosition;
	}
}
