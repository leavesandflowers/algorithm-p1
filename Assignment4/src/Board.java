import java.util.Comparator;

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

	private char[] blocks;
	private int n;

	private int manhattanNum;

	public Board(int[][] blocks) {
		// construct a board from an n-by-n array of blocks
		this.n = blocks.length;
		this.blocks = new char[n * n];
		for (int i = 0; i < n * n; i++) {
			this.blocks[i] = (char) blocks[i / n][i % n];
		}
		manhattanNum = -1;
		assert checkInit(blocks);
	}

	// (where blocks[i][j] = block in row i, column j)
	public int dimension() {
		// board dimension n
		return n;
	}

	public int hamming() {
		// number of blocks out of place
		int count = 0;
		for (int i = 0; i < n * n - 1; i++) {// do not count blank square
			if (goal(i, n * n) != blocks[i])
				count++;
		}
		return count;
	}

	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		if (manhattanNum != -1)
			return manhattanNum;
		int distance = 0;
		for (int i = 0; i < n * n; i++) {// do not count blank square
			if (blocks[i] != 0 && blocks[i] != goal(i, n * n)) {
				for (int j = 0; j < n * n; j++) {
					if (goal(j, n * n) == blocks[i]) {
						distance += (Math.abs(j / n - i / n) + Math.abs(j % n - i % n));
						break;
					}
				}
			}

		}
		manhattanNum = distance;
		return distance;
	}

	public boolean isGoal() {
		// is this board the goal board?
		for (int i = 0; i < n * n; i++) {
			if (goal(i, n * n) != blocks[i])
				return false;
		}
		return true;
	}

	private int goal(int i, int n) {
		return (i + 1) % n;
	}

	public Board twin() {
		// a board that is obtained by exchanging any pair of blocks
		int i = StdRandom.uniform(n * n);
		int j = StdRandom.uniform(n * n);
		while (blocks[i] == 0 || blocks[j] == 0 || blocks[i] == blocks[j]) {
			i = StdRandom.uniform(n * n);
			j = StdRandom.uniform(n * n);
		}
		int[][] twinBlocks = new int[n][n];
		for (int num = 0; num < n * n; num++) {
			twinBlocks[num / n][num % n] = blocks[num];
		}

		int t = twinBlocks[i / n][i % n];
		twinBlocks[i / n][i % n] = twinBlocks[j / n][j % n];
		twinBlocks[j / n][j % n] = t;

		Board twin = new Board(twinBlocks);
		return twin;
	}

	public boolean equals(Object y) {
		// does this board equal y?
		if (y == null) {
			return false;
		} else if (y.getClass() == String.class) {
			String that = (String) y;
			return this.toString().equals(that);
		} else if (y.getClass() == this.getClass()) {
			Board that = (Board) y;
			return this.toString().equals(that.toString());
		} else {
			return false;
		}

	}

	public Iterable<Board> neighbors() {
		// all neighboring boards
		int num = 0;
		MaxPQ<Board> boardPQ = new MaxPQ<Board>(new Comparator<Board>() {

			@Override
			public int compare(Board o1, Board o2) {
				return o1.manhattan() - o2.manhattan();
			}
		});
		for (int i = 0; i < n * n; i++) {
			if (blocks[i] == 0) {
				num = i;
				break;
			}
		}
		if (num / n > 0 && num / n < n) {
			int[][] nBlock = new int[n][n];
			for (int i = 0; i < n * n; i++) {
				nBlock[i / n][i % n] = blocks[i];
			}

			nBlock[num / n][num % n] = nBlock[num / n - 1][num % n];
			nBlock[num / n - 1][num % n] = 0;

			Board nBoard = new Board(nBlock);
			boardPQ.insert(nBoard);
		}

		if (num / n < n - 1 && num / n >= 0) {
			int[][] nBlock = new int[n][n];
			for (int i = 0; i < n * n; i++) {
				nBlock[i / n][i % n] = blocks[i];
			}

			nBlock[num / n][num % n] = nBlock[num / n + 1][num % n];
			nBlock[num / n + 1][num % n] = 0;

			Board nBoard = new Board(nBlock);
			boardPQ.insert(nBoard);
		}

		if (num % n > 0 && num % n < n) {
			int[][] nBlock = new int[n][n];
			for (int i = 0; i < n * n; i++) {
				nBlock[i / n][i % n] = blocks[i];
			}

			nBlock[num / n][num % n] = nBlock[num / n][num % n - 1];
			nBlock[num / n][num % n - 1] = 0;

			Board nBoard = new Board(nBlock);
			boardPQ.insert(nBoard);
		}

		if (num % n < n - 1 && num % n >= 0) {
			int[][] nBlock = new int[n][n];
			for (int i = 0; i < n * n; i++) {
				nBlock[i / n][i % n] = blocks[i];
			}

			nBlock[num / n][num % n] = nBlock[num / n][num % n + 1];
			nBlock[num / n][num % n + 1] = 0;

			Board nBoard = new Board(nBlock);
			boardPQ.insert(nBoard);
		}

		return boardPQ;
	}

	public String toString() {
		// string representation of this board (in the output format specified
		// below)
		StringBuilder s = new StringBuilder();
		s.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s.append(String.format("%2d ", (int) blocks[i * n + j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	private boolean checkInit(int[][] blocks) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (this.blocks[i * n + j] != blocks[i][j])
					return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		// unit tests (not graded)
		Board b = new Board(new int[][] { { 3, 1 }, { 2, 0 } });
		StdOut.print(b.equals(b));
	}

}