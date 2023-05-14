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
		// les deux listes pour 21 pays
		// population
		ArrayList<String> population = new ArrayList<String>();
		population.add("Afghanistan     32738376");
		population.add("Akrotiri    15700");
		population.add("Albania  3619778 ");
		population.add("Algeria  33769669 ");
		population.add("American Samoa  57496");
		population.add("Andorra  72413");
		population.add("Angola  12531357");
		population.add("Anguilla  14108");
		population.add("Antigua and Barbuda  69842");
		population.add("Argentina  40677348 ");
		population.add("Armenia  2968586 ");
		population.add("Aruba  101541 ");
		population.add("Australia  20600856 ");
		population.add("Austria  8205533 ");
		population.add("Azerbaijan  8177717 ");
		population.add("Bahamas, The  307451");
		population.add("Bahrain  718306 ");
		population.add("Bangladesh  153546901");
		population.add("Barbados  281968");
		population.add("Belarus  9685768");
		population.add("British Virgin Islands  24004");

		// surface
		ArrayList<String> surface = new ArrayList<String>();
		surface.add("Afghanistan  647500");
		surface.add(" Akrotiri  123");
		surface.add("Albania  28748 ");
		surface.add("Algeria  2381740 ");
		surface.add("American Samoa  199 ");
		surface.add("Andorra  468 ");
		surface.add("Angola  1246700 ");
		surface.add("Anguilla  102 ");
		surface.add("Antigua and Barbuda  443");
		surface.add("Argentina  2766890 ");
		surface.add("Armenia  29800");
		surface.add("Aruba  193 ");
		surface.add("Australia  7686850 ");
		surface.add("Austria  83870 ");
		surface.add("Azerbaijan  86600");
		surface.add("Bahamas, The  13940 ");
		surface.add("Bahrain  665 ");
		surface.add("Bangladesh  144000 ");
		surface.add("Barbados  431 ");
		surface.add("Belarus  207600 ");
		surface.add("British Virgin Islands  153 ");

		// creation d'un objet Population

		Population pop = new Population(population, surface);

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
	public ScenarioPopulation() {}
}
