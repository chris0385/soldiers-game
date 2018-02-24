package de.chris0385.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Id {

	private final String id;
	
	@JsonCreator
	public Id(String id) {
		this.id = id;
	}
	
	@JsonValue
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return id;
	}
}
