package de.chris0385.game;

import com.github.antag99.retinazer.Engine;
import com.github.antag99.retinazer.EngineConfig;
import com.github.antag99.retinazer.EntityListener;
import com.github.antag99.retinazer.EntitySet;
import com.github.antag99.retinazer.Priority;
import com.github.antag99.retinazer.util.IntBag;

import de.chris0385.api.model.Id;
import de.chris0385.components.ComponentFactory;
import de.chris0385.systems.KillSystem;

/**
 * One instance for each game, used to wire everything up. 
 */
public class GameContext {

	public final Engine engine;
	public final EntityFactory entityFactory;
	private float gameTime;
	
	
	public GameContext() {
		EngineConfig config = new EngineConfig();
		config.addSystem(new KillSystem(), Priority.LOWER);
		engine = new Engine(config);
		
		entityFactory = new EntityFactory(engine);
	}
	
	/**
	 * Time since game started
	 */
	public float gameTime() {
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
