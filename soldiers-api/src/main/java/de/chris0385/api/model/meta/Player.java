package de.chris0385.api.model.meta;

import de.chris0385.api.model.Id;

public class Player {

	private Id id;

	/**
	 * Login name.
	 */
	private String name;
	/**
	 * Short extra tag printed on screen. Can be used to write the version of
	 * the AI, or other stuff.
	 */
	private String tag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
