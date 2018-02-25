package de.chris0385;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.chris0385.JsonRPC.CallResponse;
import de.chris0385.api.model.meta.Player;

public class JsonRPCTest {
	
	
	private JsonRPC rpc;

	@Before
	public void setup() throws Exception {
		rpc = new JsonRPC(this);
		Logger root = (Logger)LoggerFactory.getLogger(JsonRPC.class);
		root.setLevel(Level.INFO);
	}

	@Test
	public void testNoParam() throws Exception {
		
		CallResponse result = call("{'method':'aMethod'}");
		
		assertThat(result.getResult(), equalTo("aMethod()"));
		
	}
	
	@Test
	public void testParam() throws Exception {
		
		CallResponse result = call("{'method':'aMethod','param':[{'name':'P-name'}]}");
		assertThat(result.getResult(), equalTo(null));
	}
	
	@Test
	public void testNullParam() throws Exception {
		CallResponse result = call("{'method':'aMethod','param':[null]}");
		assertThat(result.getResult(), equalTo(null));
	}
	
	@Test
	public void testPrivateMethod() throws Exception {
		CallResponse result = call("{'method':'privateMethod','param':[]}");
		assertThat(result.getError(), notNullValue());
	}
	
	@Test
	public void testThrowException() throws Exception {
		CallResponse result = call("{'method':'thrower','param':['oups']}");
		assertThat(result.getError(), equalTo("oups"));
	}
	
	
	private CallResponse call(String string) throws IOException {
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
