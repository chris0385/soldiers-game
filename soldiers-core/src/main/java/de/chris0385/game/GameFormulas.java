package de.chris0385.game;

public class GameFormulas {
	
	
	private GameFormulas() {
		// private
	}

	/**
	 * Up to 50% time reduction, when controlling the map.
	 * 
	 * @param percentOfZoneInControl Percentage of zones in control of the player.
	 * @return A factor, between 0 and 1, to be multiplied to base build time.
	 */
	public static float buildTimeFactor(float percentOfZoneInControl) {
		return 1 - 0.5f * percentOfZoneInControl;
	}

}
