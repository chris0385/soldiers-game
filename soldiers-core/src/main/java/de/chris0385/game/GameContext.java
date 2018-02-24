package de.chris0385.game;

import de.chris0385.api.model.Id;
import de.chris0385.components.EntityFactory;
import de.slyh.toolkit.entitysystem.Entity;
import de.slyh.toolkit.entitysystem.EntitySystemManager;

/**
 * One instance for each game, used to wire everything up. 
 */
public class GameContext {

	public final EntitySystemManager entitySystemManager = new EntitySystemManager("ESM");
	public final EntityFactory entityFactory = new EntityFactory(entitySystemManager);
	private float gameTime;
	
	/**
	 * Time since game started
	 */
	public float gameTime() {
		return gameTime;
	}
	
	public Entity getEntity(Id id) {
		return entitySystemManager.getEntity(id.getId());
	}
}
