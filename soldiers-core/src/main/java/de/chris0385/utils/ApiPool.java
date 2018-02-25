package de.chris0385.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.chris0385.api.model.Position;
import de.chris0385.api.model.Structure;
import de.chris0385.api.model.Unit;
import de.chris0385.api.model.World;

public class ApiPool {
	private static final Logger LOG = LoggerFactory.getLogger(ApiPool.class);
	
	private static final ThreadLocal<ApiPool> INSTANCE = ThreadLocal.withInitial(ApiPool::new);
	
	private ApiPool() {
		// private
	}
	
	/**
	 * One per thread, to be threadsafe.
	 * Assumes long living threads
	 */
	public static ApiPool get() {
		return INSTANCE.get();
	}
	
	// TODO: too small. Needs to be enough for all the games.
	public final ObjectPool<Unit> unit = PartitionedObjectPool.createOrGet(Unit.class, Unit::new, 50).get();
	public final ObjectPool<Structure> struct = PartitionedObjectPool.createOrGet(Structure.class, Structure::new, 20).get();
	public final ObjectPool<Position> position = PartitionedObjectPool.createOrGet(Position.class, Position::new, 50).get();
	public final ObjectPool<World> world = PartitionedObjectPool.createOrGet(World.class, World::new, 6).get();
	
}
