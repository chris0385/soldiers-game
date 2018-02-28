package de.chris0385.utils.mapper;

import java.util.function.BiConsumer;

public interface ReferenceObject extends AutoCloseable {
	void forEach(BiConsumer<? super String, ? super Object> action);
	
	@Override
	void close();
}
