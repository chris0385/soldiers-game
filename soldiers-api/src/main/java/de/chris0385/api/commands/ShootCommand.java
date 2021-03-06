package de.chris0385.api.commands;

import de.chris0385.api.model.Id;
import de.chris0385.api.model.Position;
import de.chris0385.api.model.Unit;

public final class ShootCommand extends CommandOnObject {
	public static final String ID = "SHOOT";

	public ShootCommand(Id objectId, Position targetPosition) {
		super(objectId);
		this.targetPosition = targetPosition;
		this.targetUnit = null;
	}
	
	public ShootCommand(Id objectId, Unit target) {
		super(objectId);
		this.targetPosition = null;
		this.targetUnit = target;
	}

	@Override
	public CommandType getCommandName() {
		return CommandType.SHOOT;
	}
	
	private final Unit targetUnit;
	private final Position targetPosition;
	
	public Position getTargetPosition() {
		return targetPosition;
	}
	
	public Unit getTargetUnit() {
		return targetUnit;
	}
}
