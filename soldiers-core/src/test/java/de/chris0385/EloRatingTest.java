package de.chris0385;

import org.junit.Before;
import org.junit.Test;

import de.chris0385.EloRating.EloPlayer;

public class EloRatingTest {

	private EloRating elorating;

	@Before
	public void setup() {
		elorating = new EloRating();
	}

	@Test
	public void testEloRating() {
		EloPlayer p1 = EloRating.createPlayer(100, 1);
		EloPlayer p2 = EloRating.createPlayer(100, 2);
		EloPlayer p3 = EloRating.createPlayer(100, 2);

		elorating.calculateELOs(p1, p2, p3);
		// elorating.calculateELOs(p1, p2);

		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p3);
	}
}
