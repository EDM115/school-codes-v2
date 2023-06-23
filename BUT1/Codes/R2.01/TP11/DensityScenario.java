import pays.Population;
import utilitaire.RWFile;
import java.util.HashMap;

/**
 * Classe DensityScenario, teste la densité de population
 */
public class DensityScenario {
    /**
     * Point d'entrée du programme
     * @param args Les arguments de la ligne de commande
     */

    public static void main(String[] args) {
        String populationFile = "../data/worldpop.txt";
        String areaFile = "../data/worldarea.txt";
        String densityFile = "../data/worldDensity.txt";

        Population population = new Population(populationFile, areaFile);
        HashMap<String, Double> densityMap = population.computeDensity();

        Population.writeMap(densityMap, densityFile);

        System.out.println("Fichier de densité créé : " + densityFile);
        System.out.println("Contenu du fichier :");
        RWFile.readFile(densityFile).forEach(System.out::println);
    }

    /**
     * Constructeur par défaut
     */
    public DensityScenario() {
        super();
    }
}
