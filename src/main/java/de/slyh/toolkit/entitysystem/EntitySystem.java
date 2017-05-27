/*
 * Created on 03.03.2013
 */
package de.slyh.toolkit.entitysystem;

import java.util.ArrayList;
import java.util.List;


public abstract class EntitySystem
{
	private final String name;
	private boolean suspended;
	private final List<Entity> entities = new ArrayList<>();
	private final Class<? extends Component>[] supportedComponents;

	@SuppressWarnings("unchecked")
    public EntitySystem(String name, Class<? extends Component>... supportedComponents)
    {
		this.name = name != null ? name : getClass().getSimpleName();
		
		this.supportedComponents = supportedComponents != null ? supportedComponents.clone()
		                                                       : new Class[0];
    }
	
	@SuppressWarnings("unchecked")
	public EntitySystem(Class<? extends Component>... supportedComponents)
	{
		this(null, supportedComponents);
	}
	
	public List<Entity> getEntities()
    {
	    return entities;
    }

	/*package*/ void addEntityIfMatches(Entity entity)
    {
		if (entities.contains(entity)) {
			// Entity is already covered by this entity system.
			return;
		}
		
		List<Class<? extends Component>> componentTypesOfEntity = entity.getComponentTypes();
		
		for (int i = 0; i < supportedComponents.length; i++) {
	        Class<? extends Component> supportedComponent = supportedComponents[i];
	        if (!componentTypesOfEntity.contains(supportedComponent)) {
	        	return;
	        }
        }
		
		entities.add(entity);
    }

	/*package*/ void removeEntityIfNoLongerMatches(Entity entity)
	{
		if (!entities.contains(entity)) {
			// Entity not part of this system.
			return;
		}
		
		List<Class<? extends Component>> componentTypesOfEntity = entity.getComponentTypes();
		
		for (int i = 0; i < supportedComponents.length; i++) {
	        Class<? extends Component> supportedComponent = supportedComponents[i];
	        if (!componentTypesOfEntity.contains(supportedComponent)) {
	        	entities.remove(entity);
	        	return;
	        }
        }
		
		// Here: Still supported by this system. All supported components of this system matched
		// the components of the entity.
	}
	
	// Only to be called by EntitySystemManager
	/*package*/ void removeEntity(Entity entity)
    {
		entities.remove(entity);
    }

	/*package*/ void removeAllEntities()
    {
		entities.clear();
    }

	/*package*/ void updateAllEntities(float tpf)
	{
		prepareUpdate(tpf);
		
		for (int i = 0; i < entities.size(); i++) {
	        update(entities.get(i), tpf);
        }
	}
	
	public String getName()
	{
		return name;
	}
	
	public void suspend()
	{
		suspended = true;
	}
	
	public void resume()
	{
		suspended = false;
	}
	
	public boolean isSuspended()
    {
	    return suspended;
    }
	
	// Can be used by systems that don't have any components.
	protected abstract void prepareUpdate(float tpf);
	
	protected abstract void update(Entity entity, float tpf);
	
	protected abstract void dispose();
}
