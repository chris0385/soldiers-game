package de.chris0385.utils;

/**
 * Could be used for components...
 */
public abstract class PooledObject {

	PooledObjectFactory<? extends PooledObject> factory;
	
	public void dispose() {
		factory.returnDisposed(this); 
	}
}
