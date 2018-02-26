package de.chris0385.game;

import java.util.function.IntConsumer;

import com.github.antag99.retinazer.Engine;
import com.github.antag99.retinazer.EngineConfig;
import com.github.antag99.retinazer.FamilyConfig;
import com.github.antag99.retinazer.Priority;

import de.chris0385.api.model.Id;
import de.chris0385.components.LocationComponent;
import de.chris0385.systems.BuildSystem;
import de.chris0385.systems.KillSystem;

/**
 * One instance for each game, used to wire everything up. 
 */
public class GameContext {

	public final Engine engine;
	public final EntityFactory entityFactory;
	
	public final SpatialIndex spatialIndex;
	private long gameTime;
	
	
	public GameContext() {
		EngineConfig config = new EngineConfig();
		config.addSystem(new KillSystem(), Priority.LOWER);
		config.addSystem(new BuildSystem(this), Priority.DEFAULT);
		engine = new Engine(config);
		
		entityFactory = new EntityFactory(engine);
		
		spatialIndex = new SpatialIndexImpl(engine);
	}
	
	/**
	 * Time since game started (millis)
	 */
	public long gameTime() {
		return gameTime;
	}

	public int getEntityId(Id objectId) {
		String id = objectId.getId();
		try {
			return Integer.valueOf(id);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
}
