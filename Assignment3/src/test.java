import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class test {
	public static void main(String[] args) {
		Point p1 = new Point(20, 30);
		Point p2 = new Point(30, 20);
		Point p3 = new Point(50, 70);
		Point p4 = new Point(60, 60);
		Point p5 = new Point(60, 20);
		Point[] p = new Point[5];
		p[0] = p1;
		p[1] = p2;
		p[2] = p3;
		p[3] = p4;
		p[4] = p5;
		Arrays.sort(p, p[0].slopeOrder());
		StdOut.println(p[0]);
		Arrays.sort(p, p[0].slopeOrder());
		StdOut.println(p[0]);
		Arrays.sort(p, p[0].slopeOrder());
		StdOut.println(p[0]);
		Arrays.sort(p, p[0].slopeOrder());
		StdOut.println(p[0]);
		Arrays.sort(p, p[0].slopeOrder());
		StdOut.println(p[0]);
	}
}
