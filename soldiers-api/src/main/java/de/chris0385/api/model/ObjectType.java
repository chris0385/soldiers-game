package de.chris0385.api.model;

import java.util.List;

/**
 * Both units and structures.
 */
public final class ObjectType {
	
	private String name;

	private int maxHealth;
	
	/**
	 * null for static structures.
	 */
	private Float maxVelocity;
	
	// TODO: multiple weapons? List of Weapons?
	private int sizeX;
	private int sizeY;
	
	// false for units, true for structures.
	private boolean canBeConquered;
	
	/**
	 * List of names. Can be null.
	 */
	private List<String> objectsThatCanBeBuild;
}
