import java.awt.Font;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class TraceShuffle {

	public static void sort(String[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int r = StdRandom.uniform(i + 1);
			exch(a, i, r);
			draw(a, i, r);
		}
	}

	// exchange a[i] and a[j]
	private static void exch(String[] a, int i, int j) {
		String swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	private static void draw(String[] a, int i, int r) {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(-2.50, i, i + "");
		StdDraw.text(-1.25, i, r + "");
		for (int j = 0; j < a.length; j++) {
			if (j == i)
				StdDraw.setPenColor(StdDraw.BLACK);
			else if (j == r)
				StdDraw.setPenColor(StdDraw.BOOK_RED);
			else
				StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
			StdDraw.text(j, i, a[j]);
		}
	}

	// display header
	private static void header(String[] a) {
		int n = a.length;

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(n / 2.0, -3, "a[ ]");
		for (int i = 0; i < n; i++)
			StdDraw.text(i, -2, i + "");
		StdDraw.text(-2.50, -2, "i");
		StdDraw.text(-1.25, -2, "r");
		StdDraw.setPenColor(StdDraw.BOOK_RED);
		StdDraw.line(-3, -1.65, n - 0.5, -1.65);
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int i = 0; i < a.length; i++)
			StdDraw.text(i, -1, a[i]);
	}

	// display footer
	private static void footer(String[] a) {
		int n = a.length;
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int i = 0; i < n; i++)
			StdDraw.text(i, n, a[i]);
	}

	// test client
	public static void main(String[] args) {
		// parse command-line argument as an array of 1-character strings
		String s = args[0];
		int n = s.length();
		String[] a = new String[n];
		for (int i = 0; i < n; i++)
			a[i] = s.substring(i, i + 1);

		// number of rows needed
		int rows = a.length;

		// set canvas size
		StdDraw.setCanvasSize(30 * (n + 3), 30 * (rows + 3));
		StdDraw.setXscale(-3, n + 1);
		// StdDraw.setYscale(n+1, -4);
		StdDraw.setYscale(rows + 1, -3);
		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 13));

		// draw the header
		header(a);

		// sort the array
		sort(a);

		footer(a);
	}

}