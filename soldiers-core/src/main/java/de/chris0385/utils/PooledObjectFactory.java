package de.chris0385.utils;

import java.util.function.Supplier;

/**
 * Not threadsafe
 */
public class PooledObjectFactory<T extends PooledObject> {

	private Supplier<T> creator;
	private Object[] pool;
	private int cnt;

	public PooledObjectFactory(Supplier<T> creator, int maxSize) {
		this.creator = creator;
		pool = new Object[maxSize];
	}
	
	public T take() {
		if (cnt > 0) {
			return (T) pool[--cnt];
		}
		return create();
	}

	private T create() {
		T created = creator.get();
		created.factory = this;
		return created;
	}
	
	void returnDisposed(PooledObject obj) {
		if (cnt == pool.length) {
			// Garbage collect
			return;
		}
		pool[cnt++] = obj;
	}
	
}
