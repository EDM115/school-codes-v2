/**
 * Teste la classe Voiture
 */
public class TestVoiture {

	/**
	 * Constructeur qui lance les tests
	 */
	public TestVoiture() {
		System.out.println("\t\t\u001B[33mTests :\u001B[0m");
		testConstructeurEtToString();
	}

	/**
	 * Méthode principale
	 * @param args Les arguments de la ligne de commande
	 */
	public static void main(String[] args) {
		new TestVoiture();
	}

	/**
	 * Teste le constructeur et la méthode toString() de Voiture
	 */
	public void testConstructeurEtToString() {
		String[] marques = {"Peugeot", "Lamborghini", "BMW", "", null};
		String[] modeles = {"208", "Aventador", "M3", "", null};
		int[] puissances = {75, 700, 431, -1, 0};

		System.out.println("");
		System.out.println("\u001B[36m*** testConstructeurEtToString()\u001B[0m");
		for (int i = 0; i < marques.length; i++) {
			try {
				Voiture v = new Voiture(marques[i], modeles[i], puissances[i]);
				System.out.println(v);
				v.toString();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			System.out.println("\u001B[32m\tOK\u001B[0m");
		}
	}
}
