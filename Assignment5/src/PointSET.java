
import java.util.Iterator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	private SET<Point2D> set;

	public PointSET() {
		// construct an empty set of points
		set = new SET<Point2D>();
	}

	public boolean isEmpty() {
		// is the set empty?
		return set.isEmpty();
	}

	public int size() {
		// number of points in the set
		return set.size();
	}

	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		if (!contains(p))
			set.add(p);
	}

	public boolean contains(Point2D p) {
		// does the set contain point p?
		return set.contains(p);
	}

	public void draw() {
		// draw all points to standard draw
		Iterator<Point2D> i = set.iterator();
		while (i.hasNext()) {
			i.next().draw();
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle
		if (rect == null)
			throw new NullPointerException();
		Iterator<Point2D> i = set.iterator();
		Queue<Point2D> rangeList = new Queue<Point2D>();
		while (i.hasNext()) {
			Point2D t = i.next();
			if (rect.contains(t)) {
				rangeList.enqueue(t);
			}
		}
		return rangeList;
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty
		Iterator<Point2D> i = set.iterator();
		Point2D near = null;
		double distance = Integer.MAX_VALUE;
		while (i.hasNext()) {
			Point2D t = i.next();
			if (p.distanceTo(t) < distance) {
				near = t;
				distance = p.distanceTo(t);
			}
		}
		return near;
	}

	public static void main(String[] args) {
		// unit testing of the methods (optional)
	}
}
