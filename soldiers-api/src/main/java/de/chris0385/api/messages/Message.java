package de.chris0385.api.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include = As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({ //
		@JsonSubTypes.Type(value = WorldUpdateMessage.class, name = WorldUpdateMessage.ID), //
		@JsonSubTypes.Type(value = InfoMessage.class, name = InfoMessage.ID), //
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Message {
	

	@JsonProperty("type")
	public abstract MessageType getMessageType();
	
	protected Message() {
	}
	
}
