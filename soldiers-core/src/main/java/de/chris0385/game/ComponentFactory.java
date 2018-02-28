package de.chris0385.game;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.github.antag99.retinazer.Component;

import de.chris0385.utils.ObjectPool;
import de.chris0385.utils.PartitionedObjectPool;
import de.chris0385.utils.mapper.InstanceBuilder;
import de.chris0385.utils.mapper.MapToBeanConverter;
import de.chris0385.utils.mapper.MapToBeanMapper;

/**
 * Use reflection (jackson?). 
 * Fully configurable
 */
public class ComponentFactory {
	
	private Map<String, ComponentCreator> componentByName;
	
	public interface ComponentCreator<T extends Component> {
		
		Class<T> getCreatedClass();
		
		T createUsingConfig(Map<String, Object> cfg);
		
	}
	
	private static final MapToBeanMapper MAPPER = new MapToBeanMapper();
	static {
		Function<? super Class, ? extends InstanceBuilder> constructorFactory = MAPPER.getConstructorFactory();
		MAPPER.setConstructorFactory((cls) -> {
			Supplier<ObjectPool<Object>> pool = PartitionedObjectPool.get(cls);
			if (pool != null) {
				return new InstanceBuilder<Object>() {

					@Override
					public Object create() {
						return pool.get().take();
					}
				};
			}
			// TODO
			System.err.println("No pool found for " + cls + ". No further attempt to find a pool will be made");
			// in not found, delegate.
			return constructorFactory.apply(cls);
		});
	}

	public static class DefaultComponentCreator<T extends Component> implements ComponentCreator<T> {
		
		private MapToBeanConverter<T> mapper;

		public DefaultComponentCreator(Class<T> cls) {
			mapper = MAPPER.mapperFor(cls);
		}

		@Override
		public T createUsingConfig(Map<String, Object> cfg) {
			return mapper.deserialize(cfg);
		}

		@Override
		public Class<T> getCreatedClass() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	/**
	 * 
	 * @param componentName The component to be build (eg "health")
	 * @param entityType    The unit for which it is built. Decides on the configuration presets to use.
	 * @return
	 */
	public <T extends Component> T buildComponent(String componentName, String entityType) {
		ComponentCreator<T> componentCreator = getComponentCreatorByName(componentName);
		return componentCreator.createUsingConfig(getConfigForComponent(entityType, componentName));
	}

	private Map<String, Object> getConfigForComponent(String entityType, String componentName) {
		// TODO Auto-generated method stub
		return null;
	}

	private <T extends Component> ComponentCreator<T> getComponentCreatorByName(String componentName) { 
		// TODO Auto-generated method stub
		return null;
	}

}
