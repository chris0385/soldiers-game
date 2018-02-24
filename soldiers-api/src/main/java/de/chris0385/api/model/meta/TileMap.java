package de.chris0385.api.model.meta;

import java.util.List;

public final class TileMap {

	/**
	 * Comma (',') separated id's of tiles for each row (of size {@code width}) of the map.
	 * The different rows are separated by semicolon (';').
	 * There are {@code height} rows.
	 */
	private String map;
	private int width;
	private int height;
	
	/**
	 * Metadata, to make sense of all the id's.
	 */
	private List<TileInfo> tileInfos;

}
