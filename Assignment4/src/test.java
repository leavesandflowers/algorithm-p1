
public class test {

	public static void main(String[] args) {
		K k = new K();
		K k2 = new K();
		k.t = k2;
		k.v = 1;
		K k3 = k.t;
		k.t = null;
		assert k3 == null;

	}
}

class K {
	K t;
	int v;
}
