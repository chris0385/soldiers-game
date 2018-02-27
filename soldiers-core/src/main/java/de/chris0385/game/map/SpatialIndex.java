package de.chris0385.game.map;

import java.util.function.IntConsumer;

import org.joml.Vector2d;

public interface SpatialIndex {
	
	void iterateIntersectingCircle(Vector2d center, double radius, IntConsumer entityCallback);

}
