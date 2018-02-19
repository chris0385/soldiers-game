package de.chris0385;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.chris0385.JsonRPC.CallResponse;
import de.chris0385.api.model.Player;

public class JsonRPCTest {
	
	
	private JsonRPC rpc;

	@Before
	public void setup() throws Exception {
		rpc = new JsonRPC(this);
		
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
