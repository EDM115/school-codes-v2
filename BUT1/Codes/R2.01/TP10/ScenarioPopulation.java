import pays.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * classe de scenario de Population
 */
public class ScenarioPopulation {

	/**
	 * Point d'entrée du programme
	 * @param args Les arguments
	 */
	public static void main(String[] args) {
		String population = "../data/worldpop.txt";
		String area = "../data/worldarea.txt";

		// creation d'un objet Population

		Population pop = new Population(population, area);

		/*
		System.out.println("Nombre de données population " + pop.getPopMap().size());
		System.out.println("Nombre de données surface " + pop.getAreaMap().size());
		for (Pays p: pop.getListePays()) {
			System.out.println(p);
		}
		*/

		// test de la méthode extractCountry
		System.out.println("Test de la méthode extractCountry");
		System.out.println(pop.extractCountry("Afghanistan     32738376"));
		System.out.println(pop.extractCountry("Algeria  2381740 "));

		// test de la méthode extractValue
		System.out.println("Test de la méthode extractValue");
		System.out.println(pop.extractValue("Afghanistan     32738376"));
		System.out.println(pop.extractValue("Algeria  2381740 "));
	}

	/**
	 * Constructeur par défaut
	 */
	public ScenarioPopulation() {
		super();
	}
}
