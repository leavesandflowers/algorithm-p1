import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int visualTop;
	private int visualButtom;
	private int n;
	private WeightedQuickUnionUF wVirtualUF;
	private WeightedQuickUnionUF wAuatualUF;
	private boolean[] blocks;

	public Percolation(int n) {
		// create n-by-n grid, with all sites blocked
		if (n <= 0)
			throw new IllegalArgumentException();
		else
			this.n = n;

		visualTop = 0;
		visualButtom = n * n + 1;
		wVirtualUF = new WeightedQuickUnionUF(n * n + 2);
		wAuatualUF = new WeightedQuickUnionUF(n * n + 1);
		blocks = new boolean[n * n + 2];
		blocks[visualTop] = true;
		blocks[visualButtom] = true;
		for (int i = 1; i <= n; i++) {
			wAuatualUF.union(visualTop, i);

			wVirtualUF.union(visualTop, i);
			wVirtualUF.union(visualButtom, visualButtom - i);
		}

	}

	public void open(int row, int col) {
		// open site (row, col) if it is not open already
		if (checkBound(row, col)) {
			blocks[(row - 1) * n + col] = true;
			if (checkBound(row - 1, col)) {// open bottom site
				if (blocks[(row - 2) * n + col]) {
					wVirtualUF.union((row - 1) * n + col, (row - 2) * n + col);
					wAuatualUF.union((row - 1) * n + col, (row - 2) * n + col);
				}
			}
			if (checkBound(row + 1, col)) {// open top site
				if (blocks[row * n + col]) {
					wVirtualUF.union((row - 1) * n + col, row * n + col);
					wAuatualUF.union((row - 1) * n + col, row * n + col);
				}
			}
			if (checkBound(row, col - 1)) {// open left site
				if (blocks[(row - 1) * n + col - 1]) {
					wVirtualUF.union((row - 1) * n + col, (row - 1) * n + col - 1);
					wAuatualUF.union((row - 1) * n + col, (row - 1) * n + col - 1);
				}
			}
			if (checkBound(row, col + 1)) {// open right site
				if (blocks[(row - 1) * n + col + 1]) {
					wVirtualUF.union((row - 1) * n + col, (row - 1) * n + col + 1);
					wAuatualUF.union((row - 1) * n + col, (row - 1) * n + col + 1);
				}
			}
		} else
			throw new IndexOutOfBoundsException();
	}

	public boolean isOpen(int row, int col) {
		// is site (row, col) open?
		if (checkBound(row, col)) {
			return blocks[(row - 1) * n + col];
		} else
			throw new IndexOutOfBoundsException();

	}

	public boolean isFull(int row, int col) {
		// is site (row, col) full?
		if (checkBound(row, col)) {
			return isOpen(row, col) && (wAuatualUF.connected((row - 1) * n + col, visualTop));
		} else
			throw new IndexOutOfBoundsException();
	}

	public boolean percolates() {
		// does the system percolate?
		for (int i = 1; i <= n; i++) {
			int row = (i - 1) / n + 1;
			int col = (i - 1) % n + 1;
			if (isOpen(row, col)) {
				return wVirtualUF.connected(visualTop, visualButtom);
			}
		}
		return false;

	}

	public int numberOfOpenSites() {
		// number of open sites
		int count = 0;
		for (int i = 1; i <= n * n; i++) {
			if (blocks[i])
				count++;
		}
		return count;
	}

	private boolean checkBound(int row, int col) {
		if (row < 1 || row > n || col < 1 || col > n)
			return false;
		return true;
	}

	public static void main(String[] args) {
		// test client (optional)
		// int count = 0;
		// for (int i = 1; i <= 400; i++) {
		// int num = StdRandom.uniform(400) + 1;
		// if (!p.isOpen((num - 1) / 20 + 1, (num - 1) % 20 + 1)) {
		// p.open((num - 1) / 20 + 1, (num - 1) % 20 + 1);
		// StdOut.println(i + ":");
		// StdOut.println(p.percolates());
		// count++;
		// if (count == 250)
		// break;
		// }
		// }
		// StdOut.println("total:" + count);
		In in = new In(args[0]); // input file
		int n = in.readInt(); // n-by-n percolation system

		Percolation perc = new Percolation(n);
		int count = 0;
		while (count < 232 && !in.isEmpty()) {
			int i = in.readInt();
			int j = in.readInt();
			perc.open(i, j);
			count++;
		}
		perc.isFull(18, 1);
		StdOut.println(perc.numberOfOpenSites());
		StdOut.println(perc.isFull(18, 1));
	}
}
