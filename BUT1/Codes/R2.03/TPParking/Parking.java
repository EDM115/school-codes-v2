/**
 * Classe Parking, gère un parking de 5 places
 */
public class Parking {

	/**
	 * Nombre de places dans le parking, initialisé à 5
	 */
	private static final int NB_PLACES = 5;

	/**
	 * Tableau de voitures, représentant les places du parking
	 */
	Voiture[] lesPlaces;

	/**
	 * Constructeur de la classe Parking
	 */
	public Parking() {
		lesPlaces = new Voiture[NB_PLACES];
	}

	/**
	 * Retourne une représentation textuelle du parking
	 * @return Une représentation textuelle du parking
	 */
	public String toString() {
		String s = "Parking [\n";

		for (int i = 0; i < this.lesPlaces.length; i++) {
			if (this.lesPlaces[i] != null) {
				s += "\t" + this.lesPlaces[i].toString();
			} else {
				s += "\tPlace libre";
			}
			if (i < this.lesPlaces.length - 1) {
				s += ",\n";
			}
		}
		s += "\n]";

		return s;
	}

	/**
	 * Vérifie si un numéro de place est valide
	 * @param numPlace Le numéro de place à vérifier
	 * @throws ArrayIndexOutOfBoundsException Si le numéro de place n'est pas valide
	 */
	private void numeroValide(int numPlace) throws ArrayIndexOutOfBoundsException {
		if (numPlace < 0 || numPlace >= NB_PLACES) {
			throw new ArrayIndexOutOfBoundsException("numeroValide() : numéro de place invalide (" + numPlace + ")");
		}
	}

	/**
	 * Gare une voiture à une place donnée
	 * @param voit La voiture à garer
	 * @param numPlace Le numéro de la place où garer la voiture
	 * @throws RuntimeException Si la place est déjà occupée
	 * @throws ArrayIndexOutOfBoundsException Si le numéro de place n'est pas valide
	 */
	public void garer(Voiture voit, int numPlace) throws RuntimeException {
		numeroValide(numPlace);
		if (this.lesPlaces[numPlace] != null) {
			throw new RuntimeException("garer() : place occupée");
		}
		this.lesPlaces[numPlace] = voit;
	}

	/**
	 * Sort une voiture d'une place donnée
	 * @param numPlace Le numéro de la place où sortir la voiture
	 * @return La voiture qui était garée à la place donnée
	 * @throws RuntimeException Si la place est déjà vide
	 * @throws ArrayIndexOutOfBoundsException Si le numéro de place n'est pas valide
	 */
	public Voiture sortir(int numPlace) throws RuntimeException {
		numeroValide(numPlace);
		if (this.lesPlaces[numPlace] == null) {
			throw new RuntimeException("sortir() : pas de voiture à la place " + numPlace);
		}
		Voiture v = this.lesPlaces[numPlace];
		this.lesPlaces[numPlace] = null;

		return v;
	}
}
