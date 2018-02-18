package de.chris0385;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRPC {

	private final ObjectMapper mapper = new ObjectMapper();
	private final Object target;
	private final Class<? extends Object> clazz;
	private final Map<MethodKey, Method> methods;

	private static class MethodKey {
		final String name;
		final int parameterCount;

		public MethodKey(String name, int parameterCount) {
			super();
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
		@JsonProperty(required = true)
		private String method;
		@JsonProperty(required = true, defaultValue = "[]")
		private List<Object> param;

		public MethodCall() {
			param = Collections.emptyList();
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

	public JsonRPC(Object target) {
		this.target = target;
		clazz = target.getClass();
		methods = new HashMap<>();
		if (clazz.isInterface()) {
			throw new IllegalArgumentException();
		}
		for (Method method : clazz.getDeclaredMethods()) {
			// If identical key, remove it
			methods.merge(new MethodKey(method.getName(), method.getParameterCount()), method, (a, b) -> null);
		}
	}

	public Object call(String jsonString) throws IOException {
		MethodCall parsed;
		parsed = mapper.readValue(jsonString, MethodCall.class);
		MethodKey key = new MethodKey(parsed.getMethod(), parsed.getParam().size());

		System.out.println(parsed);
		Method method = methods.get(key);
		System.out.println(method);
		Class<?>[] parameterTypes = method.getParameterTypes();
		int paramId = 0;
		for (ListIterator<Object> iterator = parsed.getParam().listIterator(); iterator.hasNext(); paramId++) {
			Object param = iterator.next();
			Object converted = mapper.convertValue(param, parameterTypes[paramId]);
			iterator.set(converted);
		}

		try {
			return method.invoke(target, parsed.getParam().toArray());
		} catch (IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();// TODO
			return Collections.singletonMap("ERROR", e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO: log
			return Collections.singletonMap("ERROR", e.getTargetException().getMessage());
		}
	}

}
