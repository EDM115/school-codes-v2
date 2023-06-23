/**
 * Teste la classe Parking
 */
public class TestParking {

	/**
	 * Constructeur qui lance les tests
	 */
	public TestParking() {
		System.out.println("\t\t\u001B[33mTests :\u001B[0m");
		testConstructeurEtToString();
		testGarer();
		testSortir();
	}

	/**
	 * Méthode principale
	 * @param args Les arguments de la ligne de commande
	 */
	public static void main(String[] args) {
		new TestParking();
	}

	/**
	 * Teste le constructeur et la méthode toString() de Parking
	 */	
	public void testConstructeurEtToString() {
		System.out.println("");
		System.out.println("\u001B[36m*** testConstructeurEtToString()\u001B[0m");
		Parking p = new Parking();
		System.out.println(p);
		p.toString();
		System.out.println("\u001B[32m\tOK\u001B[0m");
	}

	/**
	 * Teste la méthode garer() de Parking
	 */
	public void testGarer() {
		System.out.println("");
		System.out.println("\u001B[36m*** testGarer()\u001B[0m");
		String[] marques = {"Peugeot", "Lamborghini", "BMW", "Koenigsegg"};
		String[] modeles = {"208", "Aventador", "M3", "Agera R"};
		int[] puissances = {75, 700, 431, 1140};
		int[] places = {0, 0, 5, 3};
		Parking p = new Parking();

		for (int i = 0; i < marques.length; i++) {
			try {
				Voiture v = new Voiture(marques[i], modeles[i], puissances[i]);
				p.garer(v, places[i]);
				System.out.println(p);
				p.toString();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			System.out.println("\u001B[32m\tOK\u001B[0m");
		}
	}

	/**
	 * Teste la méthode sortir() de Parking
	 */
	public void testSortir() {
		System.out.println("");
		System.out.println("\u001B[36m*** testSortir()\u001B[0m");
		String[] marques = {"Peugeot", "Lamborghini", "BMW", "Koenigsegg"};
		String[] modeles = {"208", "Aventador", "M3", "Agera R"};
		int[] puissances = {75, 700, 431, 1140};
		int[] places = {0, 1, 3, 4};
		int[] places2 = {-1, 1, 2, 4};
		Parking p = new Parking();

		for (int i = 0; i < marques.length; i++) {
			Voiture v = new Voiture(marques[i], modeles[i], puissances[i]);
			p.garer(v, places[i]);
		}

		for (int j = 0; j < places2.length; j++) {
			try {
				p.sortir(places2[j]);
				System.out.println(p);
				p.toString();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			System.out.println("\u001B[32m\tOK\u001B[0m");
		}
	}
}

