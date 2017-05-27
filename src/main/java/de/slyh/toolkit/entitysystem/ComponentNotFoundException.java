/*
 * Created on 13.03.2013
 */
package de.slyh.toolkit.entitysystem;

public class ComponentNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = -4424177332998679223L;
    
	private final Entity entity;
	private final Class<?> componentType;

	public ComponentNotFoundException(Entity entity, Class<?> componentType)
    {
	    super("Component of type '" + componentType + "' not found in Entity (name=" + entity.getId() + ").");
	    
		this.entity = entity;
		this.componentType = componentType;
    }
	
	public Class<?> getComponentType()
    {
	    return componentType;
    }
	
	public Entity getEntity()
    {
	    return entity;
    }
}
