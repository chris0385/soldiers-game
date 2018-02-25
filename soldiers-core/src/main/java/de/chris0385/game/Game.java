package de.chris0385.game;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.chris0385.api.commands.Command;
import de.chris0385.api.model.World;
import de.chris0385.utils.LastNonNull;

public class Game implements Runnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(Game.class);

	private boolean running;
	private List<GamePlayer> players;
	private GameContext ctx;
	private LastNonNull<List<Command>> commandRetriever;

	
	public Game(GameContext ctx) {
		this.ctx = ctx;
	}
	
	@Override
	public void run() {
		initialize();
		
		while (running) {
			step();
		}
	}

	private void initialize() {
		// TODO
		List<IntFunction<List<Command>>> l = players.stream()
				.map(g -> ((IntFunction<List<Command>>) (int i) -> g.getClient().takeCommands(i)))
				.collect(Collectors.toList());
		commandRetriever = new LastNonNull<>(l);
		
		// TODO: trigger initial send to clients
//		x.get(53);
	}

	private List<List<Command>> waitForCommands(int timeout) {
		return commandRetriever.get(timeout);
	}
	
	private void sendWorldUpdate(World world) {
		for (int i = 0; i < players.size(); i++) {
			GamePlayer player = players.get(i);
			player.getClient().sendWorld(world);
		}
	}

	private void step() {
		
		// TODO wait (limited time) for incoming commands
		// TODO calculate changes
		
		// TODO: retrieve late commands before sending update to client (avoid confusion between old commands and new ones)
		// TODO send update to clients
		
	}

}
