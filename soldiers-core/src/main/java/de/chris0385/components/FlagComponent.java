package de.chris0385.components;

import com.github.antag99.retinazer.Component;

public class FlagComponent implements Component {

	public int[] linkedBuildings = new int[8];

	public FlagComponent() {
		reset();
	}

	public void reset() {
		for (int i = 0; i < linkedBuildings.length; i++) {
			linkedBuildings[i] = -1;
		}
	}
}
