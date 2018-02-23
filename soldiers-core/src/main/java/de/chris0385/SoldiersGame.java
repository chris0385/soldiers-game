package de.chris0385;

import de.chris0385.lobby.Lobby;

/**
 * Singleton
 */
public class SoldiersGame {

	private static SoldiersGame INSTANCE ;

	public static SoldiersGame get() {
		if (INSTANCE == null) {
			synchronized (SoldiersGame.class) {
				if (INSTANCE == null) {
					INSTANCE = bootGame();
				}
			}
		}
		return INSTANCE;
	}

	private static SoldiersGame bootGame() {
		// TODO Auto-generated method stub
		SoldiersGame game = new SoldiersGame();
		return game;
	}

	private final Lobby lobby;
	
	public SoldiersGame() {
		lobby = new Lobby();
	}

	public Lobby getLobby() {
		return lobby;
	}
	
	// TODO: get configuration

}
