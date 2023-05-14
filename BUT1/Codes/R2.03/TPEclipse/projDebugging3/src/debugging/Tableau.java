package debugging;

import java.util.Arrays;

/**
 * Classe Tableau
 */
public class Tableau {

	/**
	 * Attribut tab
	 */
	private int[] tab;
	
	/**
	 * Méthode main
	 * @param args Les arguments de la ligne de commande
	 */
	public static void main(String[] args) {
		System.out.println("\t\t\u001B[33mTests :\u001B[0m");
		testGetMode();
	}

	/**
	 * Constructeur de la classe Tableau
	 * @param tab Le tableau
	 */
	public Tableau(int[] tab) {
		super();
		this.tab = tab;
	}

	/**
	 * Méthode getMode
	 * @return L'occurence la plus fréquente du tableau
	 */
	public int getMode() {
		int ret = -1;
		int nbMax = 0;
		int nbOcc = 0;
		if ((this.tab != null) && (this.tab.length > 0)) {
			ret = this.tab[0];
			for (int i = 0; i < this.tab.length; i++) {
				nbOcc = 1;
				for (int j = i + 1; j < this.tab.length; j++) {
					if (this.tab[i] == this.tab[j]) {
						nbOcc++;
					}
				}
				if (nbOcc > nbMax) {
					nbMax = nbOcc;
					ret = i;
				}
			}
			ret = tab[ret];
			System.out.println("Le nombre maximum d'occurence est : " + nbMax);
		} else {
			System.out.println("Ce calcul est impossible");
		}
		
		return ret;
	}

	/**
	 * Tests de la méthode getMode
	 */
	private static void testGetMode() {
		System.out.println("");
		System.out.println("\u001B[36m*** testGetMode()\u001B[0m");
		// Cas normaux
		int[] t1 = {4, 1, 1, 3, 4};
		testCasGetMode(t1, 4);
		int[] t2 = {4, 1, 1, 3, 4, 3, 3, 3, 3};
		testCasGetMode(t2, 3);
		// Cas limites
		int[] t3 = {8};
		testCasGetMode(t3, 8);
		// Cas d'erreurs
		int[] t4 = null;
		testCasGetMode(t4, -1);
		int[] t5 = {};
		testCasGetMode(t5, -1);
	}

	/**
	 * Tests des cas de la méthode getMode
	 * @param tab Le tableau
	 * @param resAttendu Le résultat attendu
	 */
	private static void testCasGetMode(int[] tab, int resAttendu) {
		System.out.println("testCasGetMode(" + Arrays.toString(tab) + ", " + resAttendu + ") : ");
		Tableau t = new Tableau(tab);
		int res = t.getMode();
		if (res == resAttendu) {
			System.out.println("\u001B[32mOK\u001B[0m");
		} else {
			System.out.println("\u001B[31mERREUR\u001B[0m");
			System.out.println("Attendu : " + resAttendu);
			System.out.println("Obtenu  : " + res);
		}
	}
}