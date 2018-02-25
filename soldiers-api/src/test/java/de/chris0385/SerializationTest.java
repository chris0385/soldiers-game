package de.chris0385;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.chris0385.api.commands.Command;
import de.chris0385.api.commands.MoveCommand;
import de.chris0385.api.model.Id;

public class SerializationTest {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Test
	public void testMoveCommand() throws IOException {
		Id id = new Id("foo");
		MoveCommand move = new MoveCommand(id, null);
		String json = MAPPER.writeValueAsString(move);

		MoveCommand read = (MoveCommand) MAPPER.readValue(json, Command.class);
		Assert.assertEquals(id, read.getObjectId());
	}
}
