
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		In in = new In(StdIn.readString());
		String s = "";
		while (in.hasNextLine()) {
			s = s + in.readLine() + " ";
		}
		String[] c = s.split(" ");
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		for (String t : c) {
			q.enqueue(t);
		}

		for (int i = 0; i < k; i++) {
			StdOut.println(q.dequeue());
		}
	}
}
