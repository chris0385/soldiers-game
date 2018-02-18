/*
 * Created on 03.03.2013
 */

package de.slyh.toolkit.entitysystem;

public interface Component
{
    default void onAdd(Entity entity) {
    }
    
    default void onRemove(Entity entity) {
    }
}
