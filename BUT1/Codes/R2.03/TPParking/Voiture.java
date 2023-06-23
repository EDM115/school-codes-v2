/**
 * Classe Voiture, représe une voiture avec sa marque, son modèle et sa puissance
 */
public class Voiture {

	/**
	 * Marque de la voiture
	 */
	String marque;

	/**
	 * Modèle de la voiture
	 */
	String modele;

	/**
	 * Puissance de la voiture
	 */
	int puissance;

	/**
	 * Constructeur de la classe Voiture
	 * @param marque La marque de la voiture
	 * @param modele Le modèle de la voiture
	 * @param puissance La puissance de la voiture
	 * @throws NullPointerException Si la marque ou le modèle est null
	 * @throws RuntimeException Si la puissance est négative ou si la marque ou le modèle est vide
	 */
	public Voiture(String marque, String modele, int puissance) throws NullPointerException, RuntimeException {
		if (marque == null || modele == null) {
			throw new NullPointerException("Voiture : paramètre(s) null");
		}
		if (puissance <= 0 || marque.length() == 0 || modele.length() == 0) {
			throw new RuntimeException("Voiture : paramètre(s) invalide(s)");
		}
		this.marque = marque;
		this.modele = modele;
		this.puissance = puissance;
	}

	/**
	 * Retourne une représentation textuelle de la voiture
	 * @return Une représentation textuelle de la voiture
	 */
	public String toString() {
		return "Voiture [marque=" + this.marque + ", modele=" + this.modele + ", puissance=" + this.puissance + "]";
	}
}
