package de.chris0385.api.model;

import java.util.List;

/**
 * Both units and structures.
 * TODO: What about Flags?
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
	 * Set in the case of units.
	 * null for structures.
	 */
	private Boolean isAmphibious;
	
	/**
	 * List of names. Can be null (for units and maybe some buildings).
	 */
	private List<String> objectsThatCanBeBuild;
}
