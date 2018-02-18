package de.chris0385;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.Before;
import org.junit.Test;

import de.chris0385.api.commands.MoveCommand;
import de.chris0385.api.model.Id;
import de.chris0385.api.model.Player;

public class JsonRPCTest {
	
	
	private JsonRPC rpc;

	@Before
	public void setup() throws Exception {
		rpc = new JsonRPC(this);
		
	}

	@Test
	public void testNoParam() throws Exception {
		
		Object result = call("{'method':'aMethod'}");
		
		assertThat(result, equalTo("aMethod()"));
		
	}
	
	@Test
	public void testParam() throws Exception {
		
		Object result = call("{'method':'aMethod','param':[{'name':'P-name'}]}");
		assertThat(result, equalTo(null));
	}
	
	@Test
	public void testNullParam() throws Exception {
		Object result = call("{'method':'aMethod','param':[null]}");
		assertThat(result, equalTo(null));
	}
	
	@Test
	public void testPrivateMethod() throws Exception {
		Object result = call("{'method':'privateMethod','param':[]}");
		assertThat(result, instanceOf(Map.class));
		Map<String, String> error = (Map) result;
		assertThat(error.get("ERROR"), notNullValue());
	}
	
	@Test
	public void testThrowException() throws Exception {
		Object result = call("{'method':'thrower','param':['oups']}");
		assertThat(result, instanceOf(Map.class));
		Map<String, String> error = (Map) result;
		assertThat(error.get("ERROR"), equalTo("oups"));
	}
	
	
	private Object call(String string) throws IOException {
		String replace = string.replace("'", "\"");
		return rpc.call(replace);
	}

	/* **
	 */
	public String aMethod() {
		return "aMethod()";
	}
	
	public void aMethod(Player p) {
		
	}
	
	private void privateMethod() {
		
	}
	
	public void thrower(String message) {
		throw new RuntimeException(message);
	}
}
