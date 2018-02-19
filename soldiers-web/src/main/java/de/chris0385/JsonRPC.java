package de.chris0385;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRPC {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private final Object target;
	private final Class<? extends Object> clazz;
	private final Map<MethodKey, Method> methods;

	private static class MethodKey {
		final String name;
		final int parameterCount;

		public MethodKey(String name, int parameterCount) {
			this.name = name;
			this.parameterCount = parameterCount;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + parameterCount;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MethodKey other = (MethodKey) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (parameterCount != other.parameterCount)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "MethodKey [name=" + name + ", parameterCount=" + parameterCount + "]";
		}

	}

	public static class MethodCall {
		private String id;
		@JsonProperty(required = true)
		private String method;
		@JsonProperty(required = true, defaultValue = "[]")
		private List<Object> param;

		public MethodCall() {
			param = Collections.emptyList();
		}
		
		public String getId() {
			return id;
		}

		public String getMethod() {
			return method;
		}

		public List<Object> getParam() {
			return param;
		}

		@Override
		public String toString() {
			return "MethodCall [method=" + method + ", param=" + param + "]";
		}

	}
	
	public static class CallResponse {
		private String id;
		private Object result;
		private Object error;
		public CallResponse(String id, Object result, Object error) {
			this.id = id;
			this.result = result;
			this.error = error;
		}
		public Object getResult() {
			return result;
		}
		public Object getError() {
			return error;
		}
		public String getId() {
			return id;
		}
		
	}

	public JsonRPC(Object target) {
		this.target = target;
		clazz = target.getClass();
		methods = new HashMap<>();
		if (clazz.isInterface()) {
			throw new IllegalArgumentException();
		}
		for (Method method : clazz.getDeclaredMethods()) {
			// If identical key, remove it - TODO: incorrect impl
			methods.merge(new MethodKey(method.getName(), method.getParameterCount()), method, (a, b) -> null);
		}
	}

	public CallResponse call(String jsonString) throws IOException {
		MethodCall methodCall = MAPPER.readValue(jsonString, MethodCall.class);
		return call(methodCall);
	}

	public CallResponse call(MethodCall methodCall) {
		MethodKey key = new MethodKey(methodCall.getMethod(), methodCall.getParam().size());

		System.out.println(methodCall);
		Method method = methods.get(key);
		System.out.println(method);
		Class<?>[] parameterTypes = method.getParameterTypes();
		int paramId = 0;
		for (ListIterator<Object> iterator = methodCall.getParam().listIterator(); iterator.hasNext(); paramId++) {
			Object param = iterator.next();
			Object converted = MAPPER.convertValue(param, parameterTypes[paramId]);
			iterator.set(converted);
		}

		String callId = methodCall.getId();
		try {
			Object result = method.invoke(target, methodCall.getParam().toArray());
			return new CallResponse(callId, result, null);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();// TODO
			return new CallResponse(callId, null, e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO: log
			return new CallResponse(callId, null, e.getTargetException().getMessage());
		}
	}

}
