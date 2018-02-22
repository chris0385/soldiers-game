/*
 * Created on 03.03.2013
 */
package de.slyh.toolkit.entitysystem;

import java.util.ArrayList;
import java.util.List;


// TODO Better implement an EntityBuilder that creates an Entity with all Components already set instead
// of calling expensive methods on EntitySystemManager multiple times.
public class Entity
{
	private List<Class<? extends Component>> componentTypes = new ArrayList<>();
	private List<Component> components = new ArrayList<>();

	private final String id;
	private EntitySystemManager entitySystemManager;
	
	/*package*/ Entity(String id, EntitySystemManager entitySystemManager)
    {
		this.id = id;
		this.entitySystemManager = entitySystemManager;
    }
	
	/*package*/ List<Class<? extends Component>> getComponentTypes()
	{
		return componentTypes;
	}
	
	public void addComponent(Component newComponent)
	{
		Class<? extends Component> newComponentType = newComponent.getClass();
		
		// TODO Frage: macht es überaupt Sinn mehrere Componenten des gleichen typs zu haben? Man hkann danach eh nicht drauf zugreifen. 
		boolean alreadyHasComponentOfSameType = componentTypes.contains(newComponentType);
		if (!alreadyHasComponentOfSameType) {
			componentTypes.add(newComponentType);
		}
		
		components.add(newComponent);
		
		if (!alreadyHasComponentOfSameType) {
			entitySystemManager.addEntityToAllMatchingEntitySystems(this);
		}
		
		newComponent.onAdd(this);
	}
	
	public void removeComponent(Component component)
	{
		if (!components.contains(component)) {
			return;
		}
		
		component.onRemove(this);
		components.remove(component);

		Class<? extends Component> componentType = component.getClass();
		
		// Does it still have other components with this type? Then don't remove from component type list.
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).getClass() == componentType) {
				return;
			}
		}
		
		componentTypes.remove(componentType);
		entitySystemManager.removeEntityFromNoLongerMatchingEntitySystems(this);
	}
	
	public void dispose()
	{
		removeAllComponents();
	    
	    entitySystemManager.removeEntity(this);
		entitySystemManager = null;
	}
	
	private void removeAllComponents()
    {
	    // Remove backwards
	    for (int i = components.size() - 1; i >= 0; i--) {
	        removeComponent(components.get(i));
	    }
    }

    public String getId()
    {
	    return id;
    }
	
    public <T extends Component> T getOptionalComponent(Class<T> componentType)
	{
		for (int i = 0; i < components.size(); i++) {
	        Component component = components.get(i);
	        if (component.getClass() == componentType) {
	        	@SuppressWarnings("unchecked")
	        	T castComponent = (T)component;
	        	return castComponent;
	        }
        }
		
		return null;
	}
	
	public <T extends Component> T getComponent(Class<T> componentType)
	{
		for (int i = 0; i < components.size(); i++) {
	        Component component = components.get(i);
	        if (component.getClass() == componentType) {
	        	@SuppressWarnings("unchecked")
	        	T castComponent = (T)component;
	        	return castComponent;
	        }
        }
		
		throw new ComponentNotFoundException(this, componentType);
	}

	public <T extends Component> boolean hasComponent(Class<T> componentType)
    {
	    return getOptionalComponent(componentType) != null;
    }

	@Override
    public String toString()
    {
	    return "Entity [name=" + id + ", componentTypes=" + componentTypes + "]";
    }
}
