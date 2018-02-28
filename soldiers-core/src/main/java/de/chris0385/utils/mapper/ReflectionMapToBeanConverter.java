package de.chris0385.utils.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionMapToBeanConverter<T> implements MapToBeanConverter<T> {

	private final Class<T> cls;
	private final MapToBeanMapper mapper;
	private final InstanceBuilder<T> constructor;
	private Map<String, BeanPropertySetter> setters;

	public ReflectionMapToBeanConverter(MapToBeanMapper mapper, Class<T> vc) {
		this.mapper = mapper;
		this.cls = vc;
		constructor = mapper.constructorFor(vc);
	}

	protected T newObject() {
		return constructor.create();
	}

	@Override
	public T deserialize(Object nd) {
		final T bean = newObject();

		try (ReferenceObject reference = mapper.referenceFor(nd)) {
			reference.forEach((key, val) -> {
				BeanPropertySetter setter = getSetter(key);
				if (setter == null) {
					// TODO
					System.err.println("No setter for " + key + " in " + cls);
				} else {
					setter.set(bean, val);
				}
			});
		}

		return bean;
	}

	private BeanPropertySetter getSetter(String name) {
		if (setters == null) {
			initSetters();
		}
		return setters.get(name);
	}

	private void initSetters() {
		setters = new HashMap<>();
		createFieldSetters();
		createMethodSetters();
	}

	private void createMethodSetters() {
		// TODO Auto-generated method stub
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			boolean isSetter = method.getName().startsWith("set") && method.getParameterCount() == 1;
			if (!isSetter) {
				continue;
			}
		}
	}

	private BeanPropertySetter createSetter(Field field) {
		return new FieldPropertySetter(field);
	}

	private void createFieldSetters() {
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			setters.put(field.getName(), wrapIntoDeserializer(field.getType(), createSetter(field)));
		}
	}

	private BeanPropertySetter wrapIntoDeserializer(Class<?> cls, BeanPropertySetter setter) {
		if (cls.isPrimitive()) {
			return setter;
		}
		if (String.class == cls) {
			return setter;
		}
		// TODO: annotations?
		MapToBeanConverter mapper = mapperFor(cls);
		return new MappingDelegatePropertySetter(mapper, setter);
	}

	private <T> MapToBeanConverter<T> mapperFor(Class<T> cls) {
		return mapper.mapperFor(cls);
	}

}
