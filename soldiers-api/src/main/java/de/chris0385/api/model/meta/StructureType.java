package de.chris0385.api.model.meta;

import java.util.List;

public final class StructureType extends ObjectType {
	// TODO: what purpose?
	private boolean canBeConquered; 
	

	/**
	 * List of names. Can be null (for units and maybe some buildings).
	 */
	private List<String> objectsThatCanBeBuild;
	
}
