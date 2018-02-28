package de.chris0385.game;

import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.antag99.retinazer.Component;
import com.github.antag99.retinazer.Engine;
import com.github.antag99.retinazer.EntityListener;
import com.github.antag99.retinazer.EntitySet;
import com.github.antag99.retinazer.Mapper;
import com.github.antag99.retinazer.util.IntBag;

import de.chris0385.components.HealthComponent;
import de.chris0385.components.LocationComponent;
import de.chris0385.components.PhysicsComponent;
import de.chris0385.components.ShootingComponent;
import de.chris0385.utils.ObjectPool;
import de.chris0385.utils.PartitionedObjectPool;
import de.chris0385.utils.PooledObjectFactory;

public class EntityFactory {

	private static final Logger LOG = LoggerFactory.getLogger(EntityFactory.class);
	
	private Engine engine;

	@Deprecated
	private Mapper<HealthComponent> health;
	
	public EntityFactory(Engine engine) {
		this.engine = engine;
		health = engine.getMapper(HealthComponent.class);
		
		engine.addEntityListener(new EntityListener() {

			@Override
			public void removed(EntitySet entities) {
				IntBag indices = entities.getIndices();
				for (int i = 0, n = entities.size(); i < n; i++) {
					int entity = indices.get(i);
					
					// TODO: get list of components from componentFactory
					HealthComponent hc = health.get(entity);
					if (hc != null) {
						Supplier<ObjectPool<HealthComponent>> pool = PartitionedObjectPool.get(HealthComponent.class);
						if (pool != null) {
							pool.get().returnDisposed(hc);
						}
					}
					// TODO: rest of components
				}
			}

			@Override
			public void inserted(EntitySet entities) {
			}
		});
	}

	public int createEntity(String entityType) {
		int entity = engine.createEntity(); // empty entity

		ComponentFactory componentFactory = null; // TODO

		List<String> componentsForEntity = getComponentListFor(entityType);
		for (int i = 0; i < componentsForEntity.size(); i++) {
			String componentName = componentsForEntity.get(i);
			Component component = componentFactory.buildComponent(componentName, entityType);
			Mapper<Component> mapper = (Mapper<Component>) engine.getMapper(component.getClass());
			mapper.add(entity, component);
		}

		return entity;
	}

	private List<String> getComponentListFor(String entityType) {
		// TODO Auto-generated method stub
		// Load from config
		return null;
	}

}
