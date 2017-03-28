import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Solver {
	private int moves;

	private Stack<Board> resultStack;

	private class Node {
		Board board;
		Node pre;
		int steps;

		public Node(Board board, int steps, Node pre) {
			this.board = board;
			this.steps = steps;
			this.pre = pre;
		}

	}

	private class StepComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			int r = (o1.steps - o2.steps) + (o1.board.manhattan() - o2.board.manhattan());
			if (r == 0)
				return (o1.board.manhattan() - o2.board.manhattan());
			return r;
		}

	}

	public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		if (initial == null)
			throw new NullPointerException();
		Board initialBoard = initial;
		// Stopwatch s = new Stopwatch();
		StepComparator s = new StepComparator();
		MinPQ<Node> queues = new MinPQ<Node>(s);
		MinPQ<Node> twinQueues = new MinPQ<Node>(s);
		Node i = new Node(initialBoard, 0, null);
		queues.insert(i);
		Node ti = new Node(initialBoard.twin(), 0, null);
		twinQueues.insert(ti);
		resultStack = new Stack<Board>();
		moves = predict(queues, twinQueues);
		while (!queues.isEmpty()) {
			queues.min().pre = null;
			queues.delMin();
		}
		while (!twinQueues.isEmpty()) {
			twinQueues.min().pre = null;
			twinQueues.delMin();
		}
		// StdOut.println(s.elapsedTime());
	}

	private int predict(MinPQ<Node> queues, MinPQ<Node> twinQueues) {
		int processNum = 0;
		while (true) {
			if (queues.min().board.isGoal()) {
				Node r = queues.min();
				while (r != null) {
					resultStack.push(r.board);
					r = r.pre;
				}
				// StdOut.println("processNum:" + processNum);
				return queues.delMin().steps;
			} else if (twinQueues.min().board.isGoal()) {
				return -1;
			} else {
				processNum++;
				processNode(queues);
				processNode(twinQueues);
			}
		}
		// return -1;
	}

	private void processNode(MinPQ<Node> queues) {
		Node search = queues.delMin();
		Board b = search.board;
		Iterator<Board> i = b.neighbors().iterator();
		while (i.hasNext()) {
			Board n = i.next();
			if (search.pre == null || !n.equals(search.pre.board)) {
				Node insertNode = new Node(n, search.steps + 1, search);
				queues.insert(insertNode);
			}
		}
	}

	public boolean isSolvable() {
		// is the initial board solvable?
		return moves != -1;

	}

	public int moves() {
		// min number of moves to solve initial board; -1 if unsolvable
		if (isSolvable())
			return moves;
		return -1;

	}

	public Iterable<Board> solution() {
		// sequence of boards in a shortest solution; null if unsolvable
		if (isSolvable())
			return resultStack;
		return null;
	}

	public static void main(String[] args) {
		// solve a slider puzzle (given below)
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}