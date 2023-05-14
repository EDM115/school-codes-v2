package debugging;

public class Essai1 {
	public static void main(String[] args) {
		int ba = 3;
		int exp = 2;
		int res1;
		long res2;
		Nombre nb = new Nombre(ba, exp, 3);
		res1 = nb.calcul1();
		res2 = nb.calcul2(res1);
	}
}
