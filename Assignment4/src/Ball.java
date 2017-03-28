import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class Ball {
	private double rx, ry; // position
	private double vx, vy; // velocity
	private final double radius; // radius

	public Ball() {
		/* initialize position and velocity */
		radius = 0.01;
		rx = StdRandom.random() * 0.1;
		ry = StdRandom.random() * 0.1;
		vx = StdRandom.random() * 0.1;
		vy = StdRandom.random() * 0.1;
	}

	public void move(double dt) {
		if ((rx + vx * dt < radius) || (rx + vx * dt > 1.0 - radius)) {
			vx = -vx;
		}
		if ((ry + vy * dt < radius) || (ry + vy * dt > 1.0 - radius)) {
			vy = -vy;
		}
		rx = rx + vx * dt;
		ry = ry + vy * dt;
	}

	public void draw() {
		StdDraw.filledCircle(rx, ry, radius);
	}
}
