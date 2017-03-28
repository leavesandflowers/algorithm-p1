
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
	private double[] results;
	private int trials;

	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException();

		results = new double[trials];
		this.trials = trials;

		for (int i = 0; i < trials; i++) {
			int count = 0;
			Percolation p = new Percolation(n);
			while (count <= n * n) {
				int num = StdRandom.uniform(n * n) + 1;
				int row = (num - 1) / n + 1;
				int col = (num - 1) % n + 1;
				if (!p.isOpen(row, col)) {
					p.open(row, col);
					count++;
				}
				if (p.percolates()) {
					results[i] = (double) count / (n * n);
					break;
				}

			}
		}
	}

	public double mean() {
		// sample mean of percolation threshold
		return StdStats.mean(results);
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		return StdStats.stddev(results);
	}

	public double confidenceLo() {
		// low endpoint of 95% confidence interval
		return StdStats.mean(results) - 1.96 * stddev() / Math.sqrt(trials);

	}

	public double confidenceHi() {
		// high endpoint of 95% confidence interval
		return StdStats.mean(results) + 1.96 * stddev() / Math.sqrt(trials);
	}

	public static void main(String[] args) {
		// test client (described below)
		int n = StdIn.readInt();
		int t = StdIn.readInt();
		Stopwatch w = new Stopwatch();
		PercolationStats s = new PercolationStats(n, t);
		StdOut.println("mean=" + s.mean());
		StdOut.println("stddev=" + s.stddev());
		StdOut.println("confidenceLo=" + s.confidenceLo());
		StdOut.println("confidenceHi=" + s.confidenceHi());
		StdOut.println("time:" + w.elapsedTime());
	}
}
