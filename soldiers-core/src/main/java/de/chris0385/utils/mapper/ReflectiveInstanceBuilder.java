package de.chris0385.utils.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectiveInstanceBuilder<T> implements InstanceBuilder<T> {

	private final Constructor cons;

	public ReflectiveInstanceBuilder(Class cls) {
		try {
			cons = cls.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public T create() {
		try {
			return (T) cons.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

}
