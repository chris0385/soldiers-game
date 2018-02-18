package de.chris0385.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import de.chris0385.utils.ObjectRegistry;

@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class OwnableObject {
	
	// Can be null (not yet owned)
	private Id playerId;
	
	private Id id;

	private String typeName;
	
	// What about the size? Rectangle?
	private Position position;
	private Object orientation;
	
//	List<Components> l;
	
	/*
	 * renderer,
	 * physic
	 */

	@JsonIgnore
	public Player getPlayer() {
		return ObjectRegistry.INSTANCE.getPlayerById(playerId);
	}

	@JsonIgnore
	public ObjectType getType() {
		return ObjectRegistry.INSTANCE.getTypeByName(typeName);
	}
}
