package de.chris0385.components;

import de.slyh.toolkit.entitysystem.Component;

public class MovingComponent implements Component {
	
	/**
	 * TODO: is maxVelocity part of MovingComponent, or ControllableComponent? 
	 */
	public float maxVelocity;
	public float curVelocity;
	// TODO: direction type?
	public float direction;

}
