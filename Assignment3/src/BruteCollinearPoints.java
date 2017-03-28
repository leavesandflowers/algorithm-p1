import java.util.Arrays;

public class BruteCollinearPoints {
	private int count;
	private LineSegment[] segments;
	private LineSegment[] old;
	private Point[] sample;

	public BruteCollinearPoints(Point[] points) {
		// finds all line segments containing 4 points
		if (points == null)
			throw new NullPointerException();
		sample = points.clone();
		old = new LineSegment[points.length];
		Arrays.sort(sample);
		for (int i = 0; i < sample.length - 1; i++) {
			if (sample[i] == null || (i == sample.length - 2 && sample[i + 1] == null))
				throw new NullPointerException();
			if (sample[i].compareTo(sample[i + 1]) == 0)
				throw new IllegalArgumentException();
		}
		count = 0;
		Point p = null;
		Point q = null;
		Point r = null;
		Point s = null;
		for (int i = 0; i < sample.length; i++) {
			for (int j = i + 1; j < sample.length; j++) {
				for (int l = j + 1; l < sample.length; l++) {
					for (int k = l + 1; k < sample.length; k++) {
						p = sample[i];
						q = sample[j];
						r = sample[l];
						s = sample[k];
						if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
							LineSegment line = new LineSegment(p, s);
							// boolean isTheSame = false;
							// for (LineSegment origin : old) {
							// if (origin != null &&
							// origin.toString().equals(line.toString()))
							// isTheSame = true;
							// }
							// if (!isTheSame) {
							add(line, old);
							// }
						}
					}
				}
			}
		}

		segments = new LineSegment[count];
		for (int i = 0; i < count; i++) {
			segments[i] = old[i];
		}
	}

	private void add(LineSegment l, LineSegment[] lines) {
		if (count >= lines.length - 1) {
			LineSegment[] tempOld = old;
			old = new LineSegment[2 * count];
			for (int i = 0; i < count; i++) {
				old[i] = tempOld[i];
			}
			tempOld = null;
		} else {
			lines[count] = l;
			count++;
		}
	}

	public int numberOfSegments() {
		// the number of line segments
		return count;
	}

	public LineSegment[] segments() {
		// the line segments
		LineSegment[] result = segments.clone();
		return result;
	}

}
