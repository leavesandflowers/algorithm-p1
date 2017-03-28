import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		String s = "";
		// while (StdIn.hasNextLine()) {
		// s = s + StdIn.readLine() + " ";
		// }
		// String[] c = s.split(" ");
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			s = StdIn.readString();
			q.enqueue(s);
		}

		// for (String t : c)
		// q.enqueue(t);
		for (int i = 0; i < k; i++) {
			StdOut.println(q.dequeue());
		}
	}
}
