package de.chris0385.api.model.meta;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "class")
@JsonSubTypes({ //
		@JsonSubTypes.Type(value = StructureType.class, name = "STRUCT"), //
		@JsonSubTypes.Type(value = UnitType.class, name = "UNIT"), //
})
public abstract class ObjectType {
	
	private String name;

	private int sizeX;
	private int sizeY;
	
}
