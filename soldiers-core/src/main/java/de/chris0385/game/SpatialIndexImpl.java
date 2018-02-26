package de.chris0385.game;

import java.util.function.IntConsumer;

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

	SpatialIndexImpl(Engine engine) {
		family = engine.getFamily(new FamilyConfig().with(LocationComponent.class));
		entities = family.getEntities();
		locationMapper = engine.getMapper(LocationComponent.class);
	}

	@Override
	public void iterateIntersectingCircle(float x, float y, float radius, IntConsumer entityCallback) {
		IntBag indices = entities.getIndices();
		for (int i = 0, n = entities.size(); i < n; i++) {
			int entity = indices.get(i);
			LocationComponent location = locationMapper.get(entity);
			
			// TODO: if location match, call callback 
		}
	}

}
