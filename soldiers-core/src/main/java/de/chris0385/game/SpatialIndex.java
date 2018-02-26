package de.chris0385.game;

import java.util.function.IntConsumer;

public interface SpatialIndex {
	
	void iterateIntersectingCircle(float x, float y, float radius, IntConsumer entityCallback);

}
