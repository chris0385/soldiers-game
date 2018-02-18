package de.chris0385.utils;

import de.chris0385.api.model.Id;
import de.chris0385.api.model.ObjectType;
import de.chris0385.api.model.Player;

public final class ObjectRegistry {
	
	public static ObjectRegistry INSTANCE = new ObjectRegistry();

	private ObjectRegistry() {
		// private
	}
	
	public ObjectType getTypeByName(String name) {
		// TODO
		return null;
	}
	
	public void registerType(ObjectType type) {
		
	}
	
	public void registerPlayer(Player player) {
		
	}

	public Player getPlayerById(Id player) {
		// TODO Auto-generated method stub
		return null; 
	}
}
