import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class ThreeSum {
	public static int count(int[] a) {
		int N = a.length;
		int count = 0;
		for (int i = 0; i < N; i++)
			for (int j = i + 1; j < N; j++)
				for (int k = j + 1; k < N; k++)
					if (a[i] + a[j] + a[k] == 0)
						count++;
		return count;
	}

	public static void main(String[] args) {
		int[] a = In.readInts(args[0]);
		assert a != null && a.length > 0;
		Stopwatch stopwatch = new Stopwatch();
		StdOut.println(ThreeSum.count(a));
		double time = stopwatch.elapsedTime();
		StdOut.println("size:" + a.length);
		StdOut.println("time:" + time);

	}
}
