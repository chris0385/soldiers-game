package de.chris0385.api.model;

public class TileInfo {

	private int id;
	/**
	 * A human readable name.
	 */
	private String name;
	
	/**
	 * The higher, the faster one can pass.
	 * Value of zero in case of walls.
	 */
	private float velocityFactor;
	/**
	 * If true, needs amphibious capability to cross.
	 */
	private boolean isWater;
}
