package de.chris0385.components;

import de.slyh.toolkit.entitysystem.Component;

public class HealthComponent implements Component {

	public final int maxHealth;

	public int health;

	public HealthComponent(int maxHealth) {
		this.maxHealth = maxHealth;
	}

}
