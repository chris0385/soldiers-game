package de.chris0385.game.map;

import org.joml.AABBd;
import org.joml.Circled;
import org.joml.Intersectiond;
import org.joml.RayAabIntersection;
import org.joml.Vector2d;
import org.joml.Vector3d;

public class PhysicsHelper {

	
	public static final double EPSILON = 1e-20;
	
	private Vector3d tmp3_a = new Vector3d();
	private Vector3d tmp3_b = new Vector3d();
	private Vector3d tmp3_c = new Vector3d();
	
	private Vector2d tmp2_a = new Vector2d();
	private Vector2d tmp2_b = new Vector2d();

	public static void main(String[] args) {
		Vector2d vec = null;
		RayAabIntersection a;
		// AABBd

		new PhysicsHelper().lineIntersectCircle(vec, vec, vec, 3.4);
	}

	public boolean lineIntersectCircle(Vector2d la, Vector2d lb, Vector2d center, double radius) {
		return Intersectiond.distancePointLine(center.x, center.y, la.x, la.y, lb.x, lb.y) < radius;
	}

	public boolean lineIntersectCircle(Vector2d la, Vector2d lb, Circled c) {
		return Intersectiond.distancePointLine(c.x, c.y, la.x, la.y, lb.x, lb.y) < c.r;
	}

	public boolean circlesIntersect(Vector2d ca, double ra, Vector2d cb, double rb) {
		assert ra >= 0;
		assert rb >= 0;
		return ca.distance(cb) < ra + rb;
	}
	
	public boolean lineLineIntersect(Vector2d l1a, Vector2d l1b, Vector2d l2c, Vector2d l2d, Vector2d out) {
//		Vector2d dir = new Vector2d(l1b).sub(l1a);
//		RayAabIntersection a = new RayAabIntersection();
//		a.set((float)la.x, la.y, 0, dir.x, dir.y, 0);
//		https://stackoverflow.com/questions/1585525/how-to-find-the-intersection-point-between-a-line-and-a-rectangle
		// TODO
		
		
		// From https://stackoverflow.com/questions/15545496/get-intersection-points-of-line-and-shape
		// Line/Line intersections
		
		// #tmp_a used
		Vector3d line_ab = tmp3_a.set(l1a.x, l1a.y, 1).cross(tmp3_c.set(l1b.x, l1b.y, 1));
		// #tmp_b used
		Vector3d line_cd = tmp3_b.set(l2c.x, l2c.y, 1).cross(tmp3_c.set(l2d.x, l2d.y, 1));
		Vector3d intersect3d = line_ab.cross(line_cd);
		if (safeEquals(intersect3d.z, 0)) {
			return false;
		}
		out.set(intersect3d.x / intersect3d.z, intersect3d.y / intersect3d.z);

		return true;
	}
	
	public int segmentSquareIntersect(Vector2d lowLeft, Vector2d upperRight, Vector2d pa, Vector2d pb, Vector2d out1, Vector2d out2) {
		int cntIntersects = 0;
		Vector2d lowRight = tmp2_a.set(upperRight.x, lowLeft.y);
		Vector2d upperLeft = tmp2_b.set(lowLeft.x, upperRight.y);
		Vector2d out = out1;
		if (segmentSegmentIntersect(lowLeft, lowRight, pa, pb, out)) {
			cntIntersects++;
			out = out2;
		}
		if (segmentSegmentIntersect(lowRight, upperRight, pa, pb, out)) {
			cntIntersects++;
			out = out2;
		}
		if (cntIntersects == 2) {
			return 2;
		}
		if (segmentSegmentIntersect(upperRight, upperLeft, pa, pb, out)) {
			cntIntersects++;
			out = out2;
		}
		if (cntIntersects == 2) {
			return 2;
		}
		if (segmentSegmentIntersect(upperLeft, lowLeft, pa, pb, out)) {
			cntIntersects++;
			out = out2;
		}
		return cntIntersects;
	}
	
	/**
	 * Note: may even overwrite out when there is no intersect.
	 */
	public boolean segmentSegmentIntersect(Vector2d l1a, Vector2d l1b, Vector2d l2c, Vector2d l2d, Vector2d out) {
		boolean intersects = lineLineIntersect(l1a, l1b, l2c, l2d, out);
		if (!intersects) {
			return false;
		}

		if (!isPointBetween(out, l1a, l1b)) {
			return false;
		}
		if (!isPointBetween(out, l2c, l2d)) {
			return false;
		}

		return true;
	}

	/**
	 * Assumes sa, sb is a line and p on the line.
	 */
	private boolean isPointBetween(Vector2d p, Vector2d sa, Vector2d sb) {
		//https://stackoverflow.com/questions/11907947/how-to-check-if-a-point-lies-on-a-line-between-2-other-points
		double dx = sb.x - sa.x;
		double dy = sb.y - sa.y;
		if (Math.abs(dx) > Math.abs(dy)) {
			if (dx > 0) {
				return sa.x <= p.x && p.x <= sb.x;
			}
			return sb.x <= p.x && p.x <= sa.x;
		} else {
			if (dy > 0) {
				return sa.y <= p.y && p.y <= sb.y;
			}
			return sb.y <= p.y && p.y <= sa.y;
		}
	}

	private boolean isPointOnLine(Vector2d point, Vector2d sa, Vector2d sb) {
		double dxc = point.x - sa.x;
		double dyc = point.y - sa.y;

		double dxl = sb.x - sa.x;
		double dyl = sb.y - sa.y;

		double cross = dxc * dyl - dyc * dxl;
		return safeEquals(cross, 0.);
	}
	
	private boolean safeEquals(double a, double b) {
		double d = a - b;
		return d > -EPSILON && d < EPSILON;
	}

	private boolean aabbContainsSegment (float x1, float y1, float x2, float y2, float minX, float minY, float maxX, float maxY) {  
	    // Completely outside.
	    if ((x1 <= minX && x2 <= minX) || (y1 <= minY && y2 <= minY) || (x1 >= maxX && x2 >= maxX) || (y1 >= maxY && y2 >= maxY))
	        return false;

	    float m = (y2 - y1) / (x2 - x1);

	    float y = m * (minX - x1) + y1;
	    if (y > minY && y < maxY) return true;

	    y = m * (maxX - x1) + y1;
	    if (y > minY && y < maxY) return true;

	    float x = (minY - y1) / m + x1;
	    if (x > minX && x < maxX) return true;

	    x = (maxY - y1) / m + x1;
	    if (x > minX && x < maxX) return true;

	    return false;
	}

}
