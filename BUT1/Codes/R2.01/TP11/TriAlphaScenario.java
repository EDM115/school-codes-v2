import pays.*;
import tri.TriParSelectionAlpha;

/**
 * Classe TriAlphaScenario, teste le tri par sélection alphabétique
 */
public class TriAlphaScenario {

    /**
     * Point d'entrée du programme
     * @param args Les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        // Création de la liste de pays
        Pays[] listePays = new Population("../data/worldpop.txt", "../data/worldarea.txt").getListePays();

        // Affichage de la liste avant le tri
        System.out.println("Liste avant le tri :");
        for (Pays pays : listePays) {
            System.out.println(pays);
        }

        // Tri de la liste par ordre alphabétique des noms de pays
        TriParSelectionAlpha triAlpha = new TriParSelectionAlpha(listePays);
        listePays = triAlpha.getTab();

        // Affichage de la liste après le tri
        System.out.println("\nListe après le tri :");
        for (Pays pays : listePays) {
            System.out.println(pays);
        }
    }

    /**
     * Constructeur par défaut
     */
    public TriAlphaScenario() {
        super();
    }
}
