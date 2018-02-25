package de.chris0385.utils;

public interface ObjectPool<T> {

	T take();
	
	void returnDisposed(T obj);
}
