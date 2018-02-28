package de.chris0385.utils.mapper;

import java.util.Map;

public class MappingDelegatePropertySetter implements BeanPropertySetter {

	private MapToBeanConverter<?> mapper;
	private BeanPropertySetter delegate;

	public MappingDelegatePropertySetter(MapToBeanConverter<?> mapper, BeanPropertySetter delegate) {
		this.mapper = mapper;
		this.delegate = delegate; 
	}

	@Override
	public void set(Object target, Object value) {
		delegate.set(target, mapper.deserialize((Map) value));
	}

}
