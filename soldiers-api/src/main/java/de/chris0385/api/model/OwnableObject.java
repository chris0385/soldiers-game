package de.chris0385.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import de.chris0385.api.model.meta.ObjectType;
import de.chris0385.api.model.meta.Player;
import de.chris0385.utils.ObjectRegistry;
/**
 * Note: doesn't include type-infos.
 *  Only subclasses can be used.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class OwnableObject {
	
	// Can be null (not yet owned)
	private Id playerId;
	
	private Id id;

	private String typeName;
	
	// What about the size? Rectangle?
	private Position position;
	/**
	 * Use joml  vectors
	 */
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
