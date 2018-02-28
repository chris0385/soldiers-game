package de.chris0385.utils.mapper;

import java.lang.reflect.Field;

public class FieldPropertySetter implements BeanPropertySetter {

	private final Field field;

	public FieldPropertySetter(Field field) {
		this.field = field;
		field.setAccessible(true);
	}

	@Override
	public void set(Object target, Object value) {
		try {
			field.set(target, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
