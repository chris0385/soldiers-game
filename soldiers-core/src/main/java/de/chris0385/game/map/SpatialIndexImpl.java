package de.chris0385.game.map;

import java.util.function.IntConsumer;

import org.joml.Vector2d;

import com.github.antag99.retinazer.Engine;
import com.github.antag99.retinazer.EntitySet;
import com.github.antag99.retinazer.Family;
import com.github.antag99.retinazer.FamilyConfig;
import com.github.antag99.retinazer.Mapper;
import com.github.antag99.retinazer.util.IntBag;

import de.chris0385.components.LocationComponent;

public class SpatialIndexImpl implements SpatialIndex {

	private EntitySet entities;
	private Family family;
	private Mapper<LocationComponent> locationMapper;
	
	private PhysicsHelper physics = new PhysicsHelper();
	
	/* TODO: we could write an index into the tilemap:
	 * For each tile, a list of entities.
	 *
	 */

	public SpatialIndexImpl(Engine engine) {
		family = engine.getFamily(new FamilyConfig().with(LocationComponent.class));
		entities = family.getEntities();
		locationMapper = engine.getMapper(LocationComponent.class);
	}

	@Override
	public void iterateIntersectingCircle(Vector2d center, double radius, IntConsumer entityCallback) {
		IntBag indices = entities.getIndices();
		for (int i = 0, n = entities.size(); i < n; i++) {
			int entity = indices.get(i);
			LocationComponent location = locationMapper.get(entity);
			if (physics.circlesIntersect(location.location, location.size, center, radius)) {
				entityCallback.accept(entity);
			}
		}
	}

}
