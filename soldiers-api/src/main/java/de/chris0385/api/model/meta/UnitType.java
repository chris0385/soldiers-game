package de.chris0385.api.model.meta;

public final class UnitType extends ObjectType {
	private int maxHealth;
	
	/**
	 * Note: Velocity of an unit is either zero or max.
	 */
	private float maxVelocity;
	private boolean isAmphibious;
	// TODO: multiple weapons? List of Weapons?
}
