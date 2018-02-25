package de.chris0385.components;

import de.chris0385.utils.ObjectPool;
import de.chris0385.utils.PartitionedObjectPool;

public class ComponentFactory {
	
	private static final ThreadLocal<ComponentFactory> INSTANCE = ThreadLocal.withInitial(ComponentFactory::new);
	
	private ComponentFactory() {
		// private
	}
	
	/**
	 * One per thread, to be threadsafe.
	 * Assumes long living threads
	 */
	public static ComponentFactory get() {
		return INSTANCE.get();
	}
	
	public final ObjectPool<HealthComponent> hc = PartitionedObjectPool.createOrGet(HealthComponent.class, HealthComponent::new, 200).get();
	public final ObjectPool<LocationComponent> lc = PartitionedObjectPool.createOrGet(LocationComponent.class, LocationComponent::new, 200).get();
	public final ObjectPool<PhysicsComponent> pc = PartitionedObjectPool.createOrGet(PhysicsComponent.class, PhysicsComponent::new, 200).get();
	public final ObjectPool<ShootingComponent> sc = PartitionedObjectPool.createOrGet(ShootingComponent.class, ShootingComponent::new, 200).get();
	
}
