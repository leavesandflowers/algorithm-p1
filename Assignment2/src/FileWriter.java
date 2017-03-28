import edu.princeton.cs.algs4.Out;

public class FileWriter {
	public static void main(String[] args) {
		Out o = new Out("C:\\DrJava\\Assignment2\\src\\int100.txt");
		for (int i = 0; i < 100; i++) {
			o.println(i);
		}
	}
}
