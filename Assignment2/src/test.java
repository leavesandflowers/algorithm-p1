import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class test {
	public static void main(String[] args) {
		String[] s = { "a", "b", "c", "d", "e", "f", "g", "h" };
		for (int i = 0; i < s.length; i++) {
			StdOut.println(s[StdRandom.uniform(s.length)]);
		}
	}
}
