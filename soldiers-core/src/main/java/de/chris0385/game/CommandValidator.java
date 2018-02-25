package de.chris0385.game;

import java.util.HashMap;
import java.util.Map;

import de.chris0385.api.commands.Command;
import de.chris0385.api.commands.MoveCommand;
import de.chris0385.components.ControllableComponent;

public class CommandValidator {
	
	private enum Checker {
		MOVE {
			@Override
			public String isInvalid(Command command, GameContext ctx, GamePlayer player) {
				// TODO Auto-generated method stub
				MoveCommand move = (MoveCommand) command;
				
				ControllableComponent cc = ctx.engine.getMapper(ControllableComponent.class)
						.get(ctx.getEntityId(move.getObjectId()));
				if (!player.equals(cc.player)) {
					return "Object doesn't belong to player, cannot control, it";
				}
				return null;
			}
		};
		
		public abstract String isInvalid(Command command, GameContext ctx, GamePlayer player);
	}
	
	private final static Map<String, Checker> FAST_VALUEOF = new HashMap<>();
	static {
		// valueOf is slow if the key is not found (because it throws an exception).
		for (Checker s : Checker.values()) {
			FAST_VALUEOF.put(s.name(), s);
		}
	}
	
	
	private final GameContext ctx;
	
	public CommandValidator(GameContext ctx) {
		this.ctx = ctx;
	}

	public String isInvalid(GamePlayer player, Command command) {
		String name = command.getCommandName();
		Checker checker = FAST_VALUEOF.get(name);
		if (checker == null) {
			return "Unknown command";
		}
		return checker.isInvalid(command, ctx, player);
	}
}
