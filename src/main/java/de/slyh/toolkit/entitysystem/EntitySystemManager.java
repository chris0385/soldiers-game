/*
 * Created on 03.03.2013
 */
package de.slyh.toolkit.entitysystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class EntitySystemManager
{
	private final String name;
	
	private List<EntitySystem> systems = new ArrayList<>();
	private Map<String, EntitySystem> name2System = new HashMap<>();

	private List<Entity> entities = new ArrayList<>();
	private Map<String, Entity> entityMap = new HashMap<>();
	
	
	public EntitySystemManager(String name)
    {
		this.name = name;
    }
	
	public Entity createEntity(String entityId) {
		Entity entity = new Entity(entityId, this);
		registerEntity(entity);
		return entity;
	}
	
	public void registerSystem(EntitySystem system)
	{
		if (name2System.containsKey(system.getName())) {
			return;
		}
		
		// TODO Check for duplicate system/system name.
		systems.add(system);
		name2System.put(system.getName(), system);
		
		addAllMatchingEntitiesToSystem(system);
	}

	public void removeSystem(String systemName)
	{
		EntitySystem removedSystem = name2System.remove(systemName);
				
		if (removedSystem == null) {
			return;
		}
		
		removedSystem.dispose();
		systems.remove(removedSystem);
		removedSystem.removeAllEntities();
	}
	
	public void suspendSystem(String systemName)
	{
		EntitySystem entitySystem = name2System.get(systemName);
		
		if (entitySystem == null) {
			throw new BugException("Tried to suspend entity system ('" + systemName + "'). There is no entity"
			                     + " system with this name.");
		}
		
		entitySystem.suspend();
	}
	
	public void resumeSystem(String systemName)
	{
		EntitySystem entitySystem = name2System.get(systemName);
		
		if (entitySystem == null) {
			throw new BugException("Tried to resume entity system ('" + systemName + "'). There is no entity"
			                     + " system with this name.");
		}
		
		entitySystem.resume();
	}
	
	private void addAllMatchingEntitiesToSystem(EntitySystem system)
    {
		for (int i = 0; i < entities.size(); i++) {
	        system.addEntityIfMatches(entities.get(i));
        }
    }
	
	// Only called from Entity constructor
	/*package*/ void registerEntity(Entity entity)
	{
		if (entities.contains(entity)) {
			return;
		}
		
		entities.add(entity);
		entityMap.put(entity.getId(), entity); // TODO Check for duplicate name?
		
		addEntityToAllMatchingEntitySystems(entity);
	}
	
	// Only called from Entity class.
	/*package*/ void removeEntity(Entity entity)
	{
		boolean didContain = entities.remove(entity);

		if (!didContain) {
			return;
		}
		
		entityMap.remove(entity.getId());
		
		// Remove from entity systems (only some have this entity in their list, of course.)
		for (int i = 0; i < systems.size(); i++) {
			EntitySystem entitySystem = systems.get(i);
			entitySystem.removeEntity(entity);
		}
	}

	// Add entity to all systems which now support the new set of components of this entity.
	/*package*/ void addEntityToAllMatchingEntitySystems(Entity entity)
    {
		for (int i = 0; i < systems.size(); i++) {
			EntitySystem entitySystem = systems.get(i);
			entitySystem.addEntityIfMatches(entity);
		}
    }

	// Remove entity from all systems which need this particular component
	/*package*/ void removeEntityFromNoLongerMatchingEntitySystems(Entity entity)
    {
		for (int i = 0; i < systems.size(); i++) {
			EntitySystem entitySystem = systems.get(i);
			entitySystem.removeEntityIfNoLongerMatches(entity);
		}
    }
	
	public Entity getEntity(String name)
	{
		return entityMap.get(name);
	}

	public void updateSystems(float tpf)
    {
		for (int i = 0; i < systems.size(); i++) {
			EntitySystem entitySystem = systems.get(i);
			if (!entitySystem.isSuspended()) {
				entitySystem.updateAllEntities(tpf);
			}
		}
    }

	public <T extends Component> Stream<Entity> findEntitiesWithComponent(Class<T> componentType) {
		return entities.stream().filter(e -> e.hasComponent(componentType));
	}
	
	public <T extends Component> Stream<T> findComponents(Class<T> componentType) {
		return entities.stream().filter(e -> e.hasComponent(componentType)).map(e -> e.getOptionalComponent(componentType));
	}

	/**
	 * Removes all entities and all systems.
	 */
	public void purgeAll()
    {
	    for (int i = entities.size() - 1; i >= 0; i--) {
            entities.get(i).dispose();
        }
	    
		for (int i = systems.size() - 1; i >= 0; i--) {
	        systems.get(i).dispose();
        }
		
		systems.clear();
		name2System.clear();

		entities.clear();
		entityMap.clear();
    }

	public boolean hasEntity(String entityId)
    {
	    return entityMap.containsKey(entityId);
    }
}
