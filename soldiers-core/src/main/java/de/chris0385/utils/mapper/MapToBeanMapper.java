package de.chris0385.utils.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Threadsafe.
 * Capable of running without creating any garbage (GC free).
 * Use {@link #setConstructorFactory(Function)} to delegate to object pools.
 */
public class MapToBeanMapper {

	private final Map<Class, MapToBeanConverter> mappers = new ConcurrentHashMap<>();
	private final Map<Class, InstanceBuilder> constructors = new ConcurrentHashMap<>();
	
	
	private Function<? super Class, ? extends MapToBeanConverter> mapperFactory;
	private Function<? super Class, ? extends InstanceBuilder> constructorFactory;
	
	/**
	 * Object pool, to avoid "memory leaks" / garbage collects.
	 */
	private final List<MapReferenceObject> mapDelegatePool = new ArrayList<>(); 
	
	private static class MapReferenceObject implements ReferenceObject {

		private Map<String, Object> map;
		private List<MapReferenceObject> mapDelegatePool;

		public MapReferenceObject(List<MapReferenceObject> mapDelegatePool) {
			this.mapDelegatePool = mapDelegatePool;
		}

		@Override
		public void close() {
			map = null;
			pushBack(mapDelegatePool, this);
		}

		@Override
		public void forEach(BiConsumer<? super String, ? super Object> action) {
			map.forEach(action);
		}
		
	}
	
	public MapToBeanMapper() {
		mapperFactory = (cls) -> new ReflectionMapToBeanConverter<>(this, cls);
		constructorFactory  =  (c) -> new ReflectiveInstanceBuilder(c);
	}
	
	static void pushBack(List list, Object obj) {
		synchronized (list) {
			list.add(obj);
		}
	}
	
	static <T> T take(List<T> list) {
		synchronized (list) {
			int last = list.size() - 1;
			if (last >= 0) {
				return list.remove(last);
			}
			return null;
		}
	}

	public void setMapperFactory(Function<? super Class, ? extends MapToBeanConverter> mapperFactory) {
		this.mapperFactory = mapperFactory;
	}
	
	public void setConstructorFactory(Function<? super Class, ? extends InstanceBuilder> constructorFactory) {
		this.constructorFactory = constructorFactory;
	}
	
	public Function<? super Class, ? extends MapToBeanConverter> getMapperFactory() {
		return mapperFactory;
	}
	
	public Function<? super Class, ? extends InstanceBuilder> getConstructorFactory() {
		return constructorFactory;
	}

	public <T> MapToBeanConverter<T> mapperFor(Class<T> cls) {
		return mappers.computeIfAbsent(cls, mapperFactory);
	}

	public <T> T deserialize(Object map, Class<T> cls) {
		return mapperFor(cls).deserialize(map);
	}

	<T> InstanceBuilder<T> constructorFor(Class<T> cls) {
		return (InstanceBuilder) constructors.computeIfAbsent(cls, constructorFactory);
	}
	
	ReferenceObject referenceFor(Object obj) {
		if (obj instanceof Map) {
			MapReferenceObject m = take(mapDelegatePool);
			if (m == null) {
				m = new MapReferenceObject(mapDelegatePool);
			}
			m.map = (Map<String, Object>) obj;
			return m;
		}
		// TODO: bean delegate
		throw new IllegalArgumentException();
	}
}
