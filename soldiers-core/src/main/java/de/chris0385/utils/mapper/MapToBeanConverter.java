package de.chris0385.utils.mapper;

/**
 * All implementations should be threadsafe.
 */
public interface MapToBeanConverter<T> {

	T deserialize(Object source);

}
