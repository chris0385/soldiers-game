package de.chris0385.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.antag99.retinazer.Engine;
import com.github.antag99.retinazer.EntityListener;
import com.github.antag99.retinazer.EntitySet;
import com.github.antag99.retinazer.Mapper;
import com.github.antag99.retinazer.util.IntBag;

import de.chris0385.components.ComponentFactory;
import de.chris0385.components.HealthComponent;
import de.chris0385.components.LocationComponent;
import de.chris0385.components.PhysicsComponent;
import de.chris0385.components.ShootingComponent;

public class EntityFactory {

	private static final Logger LOG = LoggerFactory.getLogger(EntityFactory.class);
	
	private Engine engine;

	private Mapper<HealthComponent> health;
	private Mapper<PhysicsComponent> physics;
	private Mapper<ShootingComponent> shooting;
	private Mapper<LocationComponent> location;
	
	public EntityFactory(Engine engine) {
		this.engine = engine;
		health = engine.getMapper(HealthComponent.class);
		physics = engine.getMapper(PhysicsComponent.class);
		location = engine.getMapper(LocationComponent.class);
		shooting = engine.getMapper(ShootingComponent.class);
		
		
		engine.addEntityListener(new EntityListener() {

			@Override
			public void removed(EntitySet entities) {
				IntBag indices = entities.getIndices();
				ComponentFactory componentFactory = ComponentFactory.get();
				for (int i = 0, n = entities.size(); i < n; i++) {
					int entity = indices.get(i);
					
					HealthComponent hc = health.get(entity);
					if (hc != null) {
						componentFactory.hc.returnDisposed(hc);
					}
					// TODO: rest of components
				}
			}

			@Override
			public void inserted(EntitySet entities) {
			}
		});
	}

	public int createSoldier() {
		ComponentFactory componentFactory = ComponentFactory.get();
		int entity = engine.createEntity();
		// TODO: initialize
		health.add(entity, componentFactory.hc.take());
		physics.add(entity, componentFactory.pc.take());
		location.add(entity, componentFactory.lc.take());
		shooting.add(entity, componentFactory.sc.take());
		return entity;
	}

}
