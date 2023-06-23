import pays.*;

/**
 * Classe DemoPays, démontrant l'utilisation de la classe Pays
 */
public class DemoPays {
	/**
	 * Main method
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("");
		System.out.println("\t\u001B[33mDemoPays :\u001B[0m");

		System.out.println("");
		System.out.println("\t\u001B[33mPays :\u001B[0m");

		System.out.println("");
		System.out.println("\u001B[36m*** Pays()\u001B[0m");

		Pays pays1 = new Pays("France", 67000000, 640679);
		System.out.println("Pays(\"France\", 67000000, 640679) -> \n" + pays1);
		Pays pays2 = new Pays("Allemagne", 83000000, 357021);
		System.out.println("Pays(\"Allemagne\", 83000000, 357021) -> \n" + pays2);
		Pays pays3 = new Pays("Italie", 60000000, 301338);
		System.out.println("Pays(\"Italie\", 60000000, 301338) -> \n" + pays3);
		Pays pays4 = new Pays("Japon", 126000000, 377975);
		System.out.println("Pays(\"Japon\", 126000000, 377975) -> \n" + pays4);

		System.out.println("");
		System.out.println("\u001B[36m*** getNom()\u001B[0m");

		System.out.println("pays1.getNom() -> " + pays1.getNom());
		System.out.println("pays2.getNom() -> " + pays2.getNom());
		System.out.println("pays3.getNom() -> " + pays3.getNom());
		System.out.println("pays4.getNom() -> " + pays4.getNom());

		System.out.println("");
		System.out.println("\u001B[36m*** getSurface()\u001B[0m");

		System.out.println("pays1.getSurface() -> " + pays1.getSurface());
		System.out.println("pays2.getSurface() -> " + pays2.getSurface());
		System.out.println("pays3.getSurface() -> " + pays3.getSurface());
		System.out.println("pays4.getSurface() -> " + pays4.getSurface());
		
		System.out.println("");
		System.out.println("\u001B[36m*** getPopulation()\u001B[0m");

		System.out.println("pays1.getPopulation() -> " + pays1.getPopulation());
		System.out.println("pays2.getPopulation() -> " + pays2.getPopulation());
		System.out.println("pays3.getPopulation() -> " + pays3.getPopulation());
		System.out.println("pays4.getPopulation() -> " + pays4.getPopulation());

		System.out.println("");
		System.out.println("\u001B[36m*** setNom()\u001B[0m");

		pays1.setNom("Listembourg");
		System.out.println("pays1.setNom(\"Listembourg\") -> \n" + pays1.getNom());
		pays1.setNom("France");
		System.out.println("pays1.setNom(\"France\") -> \n" + pays1.getNom());

		System.out.println("");
		System.out.println("\u001B[36m*** setSurface()\u001B[0m");

		pays1.setSurface(100000);
		System.out.println("pays1.setSurface(100000) -> \n" + pays1.getSurface());
		pays1.setSurface(640679);
		System.out.println("pays1.setSurface(640679) -> \n" + pays1.getSurface());

		System.out.println("");
		System.out.println("\u001B[36m*** setPopulation()\u001B[0m");

		pays1.setPopulation(100000);
		System.out.println("pays1.setPopulation(100000) -> \n" + pays1.getPopulation());
		pays1.setPopulation(67000000);
		System.out.println("pays1.setPopulation(67000000) -> \n" + pays1.getPopulation());

		System.out.println("");
		System.out.println("\u001B[36m*** toString()\u001B[0m");

		System.out.println("pays1.toString() -> \n" + pays1.toString());
		System.out.println("pays2.toString() -> \n" + pays2.toString());
		System.out.println("pays3.toString() -> \n" + pays3.toString());
		System.out.println("pays4.toString() -> \n" + pays4.toString());

		System.out.println("");
		System.out.println("\u001B[36m*** compareTo()\u001B[0m");

		System.out.println("pays1.compareTo(pays2) -> " + pays1.compareTo(pays2));
		System.out.println("pays1.compareTo(pays3) -> " + pays1.compareTo(pays3));
		System.out.println("pays1.compareTo(pays4) -> " + pays1.compareTo(pays4));
		System.out.println("pays2.compareTo(pays1) -> " + pays2.compareTo(pays1));
		System.out.println("pays2.compareTo(pays3) -> " + pays2.compareTo(pays3));
		System.out.println("pays2.compareTo(pays4) -> " + pays2.compareTo(pays4));
		System.out.println("pays3.compareTo(pays1) -> " + pays3.compareTo(pays1));
		System.out.println("pays3.compareTo(pays2) -> " + pays3.compareTo(pays2));
		System.out.println("pays3.compareTo(pays4) -> " + pays3.compareTo(pays4));
		System.out.println("pays4.compareTo(pays1) -> " + pays4.compareTo(pays1));
		System.out.println("pays4.compareTo(pays2) -> " + pays4.compareTo(pays2));
		System.out.println("pays4.compareTo(pays3) -> " + pays4.compareTo(pays3));
	}

	/**
	 * Constructeur par défaut
	 */
	public DemoPays() {
		super();
	}
}