import pays.Pays;
import pays.Population;
import tri.TriParSelectionG;

/**
 * Classe TriGScenario, teste le tri par sélection générique
 */
public class TriGScenario {
	/**
	 * Point d'entrée du programme
	 * @param args Les arguments de la ligne de commande
	 */
	public static void main(String[] args) {
		Pays[] listePays = new Population("../data/worldpop.txt", "../data/worldarea.txt").getListePays();
		System.out.println("Liste avant le tri :");
		for (Pays pays : listePays) {
			System.out.println(pays);
		}
		TriParSelectionG<Pays> triG = new TriParSelectionG<Pays>(listePays);
		listePays = triG.getTab();
		System.out.println("\nListe après le tri :");
		for (Pays pays : listePays) {
			System.out.println(pays);
		}
	}

	/**
	 * Constructeur par défaut
	 */
	public TriGScenario() {
		super();
	}
}
