package de.chris0385.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.slyh.toolkit.entitysystem.Entity;
import de.slyh.toolkit.entitysystem.EntitySystemManager;

public class EntityFactory {

	private static final Logger LOG = LoggerFactory.getLogger(EntityFactory.class);
	
	private final EntitySystemManager manager;
	
	public EntityFactory(EntitySystemManager manager) {
		this.manager = manager;
	}

	public Entity createSoldier() {
		// TODO: unique id
		Entity entity = manager.createEntity("soldier");
		entity.addComponent(new HealthComponent(100));
		entity.addComponent(new PhysicsComponent());
		entity.addComponent(new LocationComponent());
		entity.addComponent(new ShootingComponent());
		return entity;
	}
}
