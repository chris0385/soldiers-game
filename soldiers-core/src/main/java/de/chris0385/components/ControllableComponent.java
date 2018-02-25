package de.chris0385.components;

import com.github.antag99.retinazer.Component;

import de.chris0385.game.GamePlayer;

// Is that a separate OwnableComponent? Would it be useful to separate?
public abstract class ControllableComponent implements Component {
	
	// player who can control
	// can be null
	public GamePlayer player;

	// TODO: distinction, controlling build, controlling movement
}
