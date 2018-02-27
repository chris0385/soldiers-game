package de.chris0385.game.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joml.Vector2d;
import org.junit.Before;
import org.junit.Test;

public class PhysicsHelperTest {

	private PhysicsHelper sut;
	private Vector2d out;

	@Before
	public void setup() {
		sut = new PhysicsHelper();
		out = new Vector2d();
	}
	
	@Test
	public void testLineIntersect_Middle() {
		boolean i = sut.lineLineIntersect(new Vector2d(1, 0), new Vector2d(1, 2), new Vector2d(0, 1),
				new Vector2d(2, 1),out);
		assertTrue(i); 
		assertEquals(new Vector2d(1, 1), out);
	}
	
	@Test
	public void testLineIntersect_Segment() {
		boolean i = sut.lineLineIntersect(new Vector2d(1, 0), new Vector2d(1, 0.5), new Vector2d(0, 1),
				new Vector2d(2, 1),out);

		assertTrue(i);
		// No bound checks. 
		assertEquals(new Vector2d(1, 1), out);
	}
	
	@Test
	public void testLineIntersect_Parallel() {
		boolean i = sut.lineLineIntersect(new Vector2d(1, 0), new Vector2d(1, 1), new Vector2d(2, 0), new Vector2d(2, 1),out);

		assertFalse(i);
	}
	
	@Test
	public void testSegmentIntersect_Segment() {
		boolean i = sut.segmentSegmentIntersect(new Vector2d(1, 0), new Vector2d(1, 0.5), new Vector2d(0, 1),
				new Vector2d(2, 1),out);

		// With bound checks. 
		assertFalse(i);
	}
	
}
