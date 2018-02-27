package de.chris0385.game.map;

import org.joml.Intersectiond;
import org.joml.Vector2d;
import org.joml.Vector2i;

public class TileMap {
	
	
	private int sx;
	private int sy;
	private Tile[][] tiles;

	public TileMap(int sx, int sy) {
		this.sx = sx;
		this.sy = sy;
		tiles = new Tile[sx][sy];
	}

	public Tile getTile(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public double getWeightedDistance(Vector2d a, Vector2d b) {
		// Use Vector2i (int) for calculation, to get control over errors?
		
		Vector2d unitVector = new Vector2d(b);
		unitVector.sub(a);
		unitVector.normalize();
		
		Vector2d curPos = a;
		
		double distW = 0;

		while (!curPos.equals(b)) {
			/* FIXME: algorithm is kinda right.
			 * But it isn't because floating point error and 
			 * unclear behavior when curPos is on a tile edge.
			 * 
			 * TODO: have fun with edge cases
			 */
			
			Tile t = getTileAt(curPos);
			double l = getIntersectionDist(curPos, b, t);
			distW += l * t.weight();
			curPos.fma(l, unitVector);
		}
		
		
		return distW;
	}

	private double getIntersectionDist(Vector2d a, Vector2d b, Tile t) {
		// TODO Auto-generated method stub
		// Calculate intersection points from [a,b] with border of t.
		// Calculate length
		int lower = t.y();
		int left = t.x();
		
//		Intersectiond.intersectLineLine(lower, left, pe1x, pe1y, ps2x, ps2y, pe2x, pe2y, p)
//		new PhysicsHelper().lineIntersectCircle(la, lb, c)
		
//		new PhysicsHelper().lineIntersectCircle(vec, vec, vec, 3.4);
		return 0;
	}

	private Tile getTileAt(Vector2d a) {
		// TODO Auto-generated method stub
		return null;
	}

}
