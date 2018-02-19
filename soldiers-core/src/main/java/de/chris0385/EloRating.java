package de.chris0385;

import java.util.Arrays;
import java.util.Collection;

import de.chris0385.EloRating.EloPlayer;

public class EloRating {

	public static class EloPlayer {

		public Object userData;
		private int elo;
		/**
		 * Place the player had in current game
		 */
		private int place;
		private int newElo;
		
		public EloPlayer(int elo, int place) {
			this.elo = elo;
			this.place = place;
		}
		
		public int getElo() {
			return elo;
		}
		
		public int getPlace() {
			return place;
		}

		public boolean wasBetterThan(EloPlayer playerB) {
			return place < playerB.place;
		}

		public boolean asGoodAs(EloPlayer playerB) {
			return place == playerB.place;
		}

		@Override
		public String toString() {
			return "EloPlayer [elo=" + elo + ", place=" + place + ", userData=" + userData + "]";
		}
		
	}
	
	public static EloPlayer createPlayer(int elo, int place) {
		return new EloPlayer(elo, place);
	}
	

	public void calculateELOs(Collection<EloPlayer> players) {
		int n = players.size();
		// K factor, adjusted for amount of players
		int K = 32 / (n - 1);

		for (EloPlayer player : players) {
			int elochange = 0;
			for (EloPlayer opponent : players) {
				if (opponent == player) {
					continue;
				}
				float S = placeRating(player, opponent);

				// Expected score
				double EA = 1 / (1.0 + Math.pow(10, (opponent.elo - player.elo) / 400.0));
				elochange += Math.round(K * (S - EA));
			}
			player.newElo = player.elo + elochange;
		}
		for (EloPlayer player : players) {
			player.elo = player.newElo;
		}
	}

	protected float placeRating(EloPlayer player, EloPlayer opponent) {
		if (player.wasBetterThan(opponent)) {
			return 1;
		} else if (player.asGoodAs(opponent)) {
			return 0.5f;
		} else {
			// stronger opponent
			return 0;
		}
	}


	public void calculateELOs(EloPlayer ... players) { 
		calculateELOs(Arrays.asList(players));
	}
}
