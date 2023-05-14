package pays;

/**
 * Classe Pays
 */
public class Pays implements Comparable<Pays> {

	/**
	 * Le nom du pays
	 */
	private String nom;

	/**
	 * La surface du pays
	 */
	private double surface;

	/**
	 * La population du pays
	 */
	private double population;

	/**
	 * Constructeur de la classe Pays
	 * @param nom le nom du pays
	 * @param surface la surface du pays
	 * @param population la population du pays
	 */
	public Pays(String nom, double surface, double population) {
		if (nom == null || nom.length() == 0 || surface <= 0 || population <= 0) {
			System.err.println("\u001B[31mPays() : Invalid arguments\u001B[0m");
			this.nom = "";
			this.surface = 0;
			this.population = 0;
		} else {
			this.nom = nom;
			this.surface = surface;
			this.population = population;
		}
	}

	/**
	 * Retourne le nom du pays
	 * @return le nom du pays
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Retourne la surface du pays
	 * @return la surface du pays
	 */
	public double getSurface() {
		return this.surface;
	}

	/**
	 * Retourne la population du pays
	 * @return la population du pays
	 */
	public double getPopulation() {
		return this.population;
	}

	/**
	 * Change le nom du pays
	 * @param nom le nouveau nom du pays
	 */
	public void setNom(String nom) {
		if (nom == null || nom.length() == 0) {
			System.err.println("\u001B[31msetNom() : Invalid arguments\u001B[0m");
		} else {
			this.nom = nom;
		}
	}

	/**
	 * Change la surface du pays
	 * @param surface la nouvelle surface du pays
	 */
	public void setSurface(double surface) {
		if (surface <= 0) {
			System.err.println("\u001B[31msetSurface() : Invalid arguments\u001B[0m");
		} else {
			this.surface = surface;
		}
	}

	/**
	 * Change la population du pays
	 * @param population la nouvelle population du pays
	 */
	public void setPopulation(double population) {
		if (population <= 0) {
			System.err.println("\u001B[31msetPopulation() : Invalid arguments\u001B[0m");
		} else {
			this.population = population;
		}
	}

	/**
	 * Retourne une chaîne de caractères représentant le pays
	 * @return une chaîne de caractères représentant le pays
	 */
	public String toString() {
		String str = "Pays : " + this.nom + " (" + this.surface + " km2, " + this.population + " habitants)";

		return str;
	}

	/**
	 * Compare deux pays
	 * @param p le pays à comparer
	 * @return 1 si le pays courant est plus peuplé que le pays passé en paramètre, -1 si le pays courant est moins peuplé que le pays passé en paramètre, 0 si les deux pays ont la même population
	 */
	public int compareTo(Pays p) {
		if (p == null) {
			System.err.println("\u001B[31mcompareTo() : Invalid arguments\u001B[0m");
			throw new NullPointerException();
		} else {
			if (this.population > p.population) {
				return 1;
			} else if (this.population < p.population) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}