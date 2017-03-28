import java.util.Arrays;

public class FastCollinearPoints {
	private Point[] origin;
	private Point[] sample;
	private int total;
	private LineSegment[] segments;
	private LineSegment[] old;

	/**
	 * the origin point must be the top of the line,
	 * because the temp array be sorted by y-coodinate 
	 * which is the same as the origin array.
	 */
	public FastCollinearPoints(Point[] points) {
		// finds all line segments containing 4 or more points
		if (points == null)
			throw new NullPointerException();
		sample = points.clone();
		Arrays.sort(sample);
		origin = sample.clone();
		for (int i = 0; i < sample.length - 1; i++) {
			if (sample[i] == null || (i == sample.length - 2 && sample[i + 1] == null))
				throw new NullPointerException();
			if (sample[i].compareTo(sample[i + 1]) == 0)
				throw new IllegalArgumentException();
		}
		old = new LineSegment[8 * sample.length];
		total = 0;
		for (int i = 0; i < sample.length; i++) {
			Arrays.sort(sample, origin[i].slopeOrder());
			int count = 0;
			int front = 0, end = 0;
			for (int j = 0; j < sample.length; j++) {
				// double tempSlope = origin[i].slopeTo(points[j]);
				if (between(j, sample, origin[i])) {
					count++;
				} else if (before(j, sample, origin[i])) {
					front = j;
					count++;
				} else if (behind(j, sample, origin[i])) {
					end = j;
					count++;
					if (count >= 3) {
						Point[] temp = new Point[end - front + 2];
						for (int k = front; k <= end; k++) {
							temp[k - front] = sample[k];
						}
						temp[end - front + 1] = origin[i];
						Arrays.sort(temp);
						LineSegment line = new LineSegment(temp[0], temp[temp.length - 1]);
						if (temp[0] == origin[i])
							// boolean isTheSame = false;
							// for (LineSegment o : old) {
							// if (o != null &&
							// o.toString().equals(line.toString()))
							// isTheSame = true;
							// }
							// if (!isTheSame) {
							// add(line, old);
							// }
							add(line, old);
					}
					count = 0;
					front = 0;
					end = 0;

				} else {
					count = 0;
					front = 0;
					end = 0;
				}

			}
		}
		segments = new LineSegment[total];
		for (int i = 0; i < total; i++) {
			segments[i] = old[i];
		}

	}

	private void add(LineSegment l, LineSegment[] lines) {
		if (total >= lines.length - 1) {
			LineSegment[] tempOld = old;
			old = new LineSegment[2 * total];
			for (int i = 0; i < total; i++) {
				old[i] = tempOld[i];
			}
			tempOld = null;
		} else {
			lines[total] = l;
			total++;
		}
	}

	private boolean before(int j, Point[] points, Point origin) {
		if (j + 1 < points.length && origin.slopeTo(points[j]) == origin.slopeTo(points[j + 1]))
			return true;
		return false;
	}

	private boolean between(int j, Point[] points, Point origin) {
		if (j <= 0 || j >= points.length - 1)
			return false;
		else if (origin.slopeTo(points[j]) == origin.slopeTo(points[j - 1])
				&& origin.slopeTo(points[j]) == origin.slopeTo(points[j + 1]))
			return true;
		else
			return false;
	}

	private boolean behind(int j, Point[] points, Point origin) {
		if (j - 1 >= 0 && origin.slopeTo(points[j]) == origin.slopeTo(points[j - 1]))
			return true;
		return false;
	}

	public int numberOfSegments() {
		// the number of line segments
		return total;
	}

	public LineSegment[] segments() {
		// the line segments
		LineSegment[] result = segments.clone();
		return result;
	}

}
