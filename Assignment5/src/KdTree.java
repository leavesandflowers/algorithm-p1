
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
	private class Node {
		Point2D it;
		Node left;
		Node right;
		int step;

		public Node(Point2D it) {
			this.it = it;
			this.left = null;
			this.right = null;
			step = 0;
		}
	}

	private Node root;
	private int count;

	public KdTree() {
		// construct an empty set of points
	}

	public boolean isEmpty() {
		// is the set empty?
		return root == null;
	}

	public int size() {
		// number of points in the set
		return count;
	}

	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		if (p == null)
			throw new NullPointerException();
		Node t = root;
		int steps = 0;
		boolean over = false;
		if (t == null)
			over = true;
		while (!over) {
			steps++;
			if (t.step % 2 == 0) {
				if (p.x() < t.it.x()) {
					if (t.left != null)
						t = t.left;
					else {
						t.left = put(p, steps);
						over = true;
					}
				} else {
					if (p.x() == t.it.x() && p.y() == t.it.y())
						over = true;
					else if (t.right != null)
						t = t.right;
					else {
						t.right = put(p, steps);
						over = true;
					}
				}
			} else if (t.step % 2 == 1) {
				if (p.y() < t.it.y()) {
					if (t.left != null)
						t = t.left;
					else {
						t.left = put(p, steps);
						over = true;
					}
				} else {
					if (p.x() == t.it.x() && p.y() == t.it.y())
						over = true;
					else if (t.right != null)
						t = t.right;
					else {
						t.right = put(p, steps);
						over = true;
					}
				}
			}
		}
		if (root == null) {
			root = put(p, steps);
		}

		assert isFull();
	}

	private Node put(Point2D p, int steps) {
		Node t = new Node(p);
		t.step = steps;
		count++;
		return t;
	}

	public boolean contains(Point2D p) {
		// does the set contain point p?
		if (p == null)
			throw new NullPointerException();
		Node t = root;
		while (t != null) {
			if (p.equals(t.it)) {
				return true;
			} else if (t.step % 2 == 0) {
				if (p.x() < t.it.x())
					t = t.left;
				else
					t = t.right;
			} else if (t.step % 2 == 1) {
				if (p.y() < t.it.y())
					t = t.left;
				else
					t = t.right;
			}
		}
		return false;
	}

	public void draw() {
		// draw all points to standard draw
		Queue<Node> q = new Queue<Node>();
		inorder(root, q);
		Iterator<Node> i = q.iterator();
		while (i.hasNext()) {
			i.next().it.draw();
		}
	}

	private void inorder(Node x, Queue<Node> q) {
		if (x == null)
			return;
		inorder(x.left, q);
		q.enqueue(x);
		inorder(x.right, q);
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle
		if (rect == null)
			throw new NullPointerException();
		RectHV cover = new RectHV(0.0, 0.0, 1.0, 1.0);
		Queue<Node> q = new Queue<Node>();
		searchRect(root, cover, rect, q);
		return new Iterable<Point2D>() {

			@Override
			public Iterator<Point2D> iterator() {
				return new Iterator<Point2D>() {

					@Override
					public boolean hasNext() {
						return !q.isEmpty();
					}

					@Override
					public Point2D next() {
						return q.dequeue().it;
					}
				};
			}
		};
	}

	private void searchRect(Node t, RectHV cover, RectHV rect, Queue<Node> q) {
		if (t == null)
			return;
		if (t.step % 2 == 0) {
			RectHV coverLeft = new RectHV(cover.xmin(), cover.ymin(), t.it.x(), cover.ymax());
			RectHV coverRight = new RectHV(t.it.x(), cover.ymin(), cover.xmax(), cover.ymax());
			if (coverLeft.intersects(rect)) {
				searchRect(t.left, coverLeft, rect, q);
			}
			if (coverRight.intersects(rect)) {
				searchRect(t.right, coverRight, rect, q);
			}
			if (rect.contains(t.it)) {
				q.enqueue(t);
			}
		} else if (t.step % 2 == 1) {
			RectHV coverBottom = new RectHV(cover.xmin(), cover.ymin(), cover.xmax(), t.it.y());
			RectHV coverTop = new RectHV(cover.xmin(), t.it.y(), cover.xmax(), cover.ymax());
			if (coverBottom.intersects(rect)) {
				searchRect(t.left, coverBottom, rect, q);
			}
			if (coverTop.intersects(rect)) {
				searchRect(t.right, coverTop, rect, q);
			}
			if (rect.contains(t.it)) {
				q.enqueue(t);
			}
		}
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty
		if (p == null)
			throw new NullPointerException();
		RectHV cover = new RectHV(0.0, 0.0, 1.0, 1.0);
		if (root == null)
			return null;
		return searchNearPoint(p, root, cover, Integer.MAX_VALUE).it;
	}

	private Node searchNearPoint(Point2D goal, Node t, RectHV cover, double distance) {
		Node nearP = t;
		if (t.step % 2 == 0) {
			RectHV coverLeft = new RectHV(cover.xmin(), cover.ymin(), t.it.x(), cover.ymax());
			RectHV coverRight = new RectHV(t.it.x(), cover.ymin(), cover.xmax(), cover.ymax());
			double left = coverLeft.distanceTo(goal);
			double right = coverRight.distanceTo(goal);
			if (distance > t.it.distanceTo(goal))
				distance = t.it.distanceTo(goal);
			if (distance > right || distance > left) {
				if ((t.left != null && t.right != null) && distance > left && distance > right) {
					if (left == 0) {
						Node a = searchNearPoint(goal, t.left, coverLeft, distance);
						if (distance > a.it.distanceTo(goal))
							distance = a.it.distanceTo(goal);
						Node b = searchNearPoint(goal, t.right, coverRight, distance);
						nearP = min(a, b, goal);
					} else {
						Node b = searchNearPoint(goal, t.right, coverRight, distance);
						if (distance > b.it.distanceTo(goal))
							distance = b.it.distanceTo(goal);
						Node a = searchNearPoint(goal, t.left, coverLeft, distance);
						nearP = min(b, a, goal);
					}
				} else if (t.left != null && distance > left) {
					nearP = searchNearPoint(goal, t.left, coverLeft, distance);
				} else if (t.right != null && distance > right) {
					nearP = searchNearPoint(goal, t.right, coverRight, distance);
				}
			}

		} else if (t.step % 2 == 1) {
			RectHV coverBottom = new RectHV(cover.xmin(), cover.ymin(), cover.xmax(), t.it.y());
			RectHV coverTop = new RectHV(cover.xmin(), t.it.y(), cover.xmax(), cover.ymax());
			double bottom = coverBottom.distanceTo(goal);
			double top = coverTop.distanceTo(goal);
			if (distance > t.it.distanceTo(goal))
				distance = t.it.distanceTo(goal);
			if (distance > top || distance > bottom) {
				if ((t.left != null && t.right != null) && distance > top && distance > bottom) {
					if (bottom == 0) {
						Node a = searchNearPoint(goal, t.left, coverBottom, distance);
						if (distance > a.it.distanceTo(goal))
							distance = a.it.distanceTo(goal);
						Node b = searchNearPoint(goal, t.right, coverTop, distance);
						nearP = min(a, b, goal);
					} else {
						Node b = searchNearPoint(goal, t.right, coverTop, distance);
						if (distance > b.it.distanceTo(goal))
							distance = b.it.distanceTo(goal);
						Node a = searchNearPoint(goal, t.left, coverBottom, distance);
						nearP = min(b, a, goal);
					}
				} else if (t.left != null && distance > bottom) {
					nearP = searchNearPoint(goal, t.left, coverBottom, distance);
				} else if (t.right != null && distance > top) {
					nearP = searchNearPoint(goal, t.right, coverTop, distance);
				}
			}
		}
		nearP = min(t, nearP, goal);
		return nearP;
	}

	private Node min(Node a, Node b, Point2D goal) {
		if (b == null || a == null) {
			if (b == null)
				return a;
			return b;
		}
		if (a.it.distanceTo(goal) < b.it.distanceTo(goal))
			return a;
		return b;

	}

	private boolean isFull() {
		Queue<Node> q = new Queue<Node>();
		inorder(root, q);
		return q.size() == count;
	}

	public static void main(String[] args) {
		// unit testing of the methods (optional)
		// KdTree t = new KdTree();
		// Point2D p1 = new Point2D(0.206107, 0.095492);
		// t.insert(p1);
		// assert t.isEmpty() == false;
		// assert t.size() == 1;
		// Point2D p2 = new Point2D(0.5, 0.0);
		// Point2D p3 = new Point2D(0.6, 0.6);
		// t.insert(p2);
		// t.insert(p3);
		// assert t.isEmpty() == false;
		// assert t.size() == 3;
		// assert t.contains(p2) == true;
		// assert t.contains(p3) == true;
		// Iterator<Point2D> i = t.range(new RectHV(0, 0, 1, 1)).iterator();
		// while (i.hasNext())
		// StdOut.println(i.next());

		// String filename = args[0];
		// In in = new In(filename);

		KdTree kdtree = new KdTree();
		kdtree.nearest(new Point2D(0.214, 0.397));
		// while (!in.isEmpty()) {
		// double x = in.readDouble();
		// double y = in.readDouble();
		// Point2D p = new Point2D(x, y);
		// kdtree.insert(p);
		// }
		//
		// Point2D p = new Point2D(0.5, 0.09);
		// kdtree.nearest(p);
		// StdOut.println(kdtree.nearest(p));
	}
}
