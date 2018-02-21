package de.chris0385.components;

import de.slyh.toolkit.entitysystem.Entity;
import de.slyh.toolkit.entitysystem.EntitySystemManager;

public class EntityFactory {

	
	EntitySystemManager manager;
	
	public Entity createSoldier() {
		// TODO: unique id
		Entity entity = manager.createEntity("soldier");
		entity.addComponent(new HealthComponent(100));
		entity.addComponent(new MovingComponent());
		entity.addComponent(new PositionedComponent());
		entity.addComponent(new ShootingComponent());
		return entity;
	}
}
