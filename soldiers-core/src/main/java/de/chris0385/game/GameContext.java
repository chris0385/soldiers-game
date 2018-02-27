package de.chris0385.game;

import com.github.antag99.retinazer.Engine;
import com.github.antag99.retinazer.EngineConfig;
import com.github.antag99.retinazer.Priority;

import de.chris0385.api.model.Id;
import de.chris0385.game.map.SpatialIndex;
import de.chris0385.game.map.SpatialIndexImpl;
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

		// Fight
		// Move
		// Conquest
		config.addSystem(new BuildSystem(this), Priority.DEFAULT);
		config.addSystem(new KillSystem(), Priority.LOWER);
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
