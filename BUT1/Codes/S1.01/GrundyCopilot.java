/**
 * Jeu de Grundy - Joueur contre IA
 * @author EDM115
 **/

import java.util.Arrays;

class GrundyCopilot {
	void principal() {
		grundy();
	}

	/**
	 * Jeu de Grundy
	 * L'utilisateur joue contre une IA
	 * L'utilisateur joue toujours en premier
	 * 
	 */
	void grundy() {
		// Initialisation du jeu
		int nbAllumettes = 21;
		int nbAllumettesMax = 3;
		int nbAllumettesPrises = 0;
		int tour = 0;
		int[] allumettes = new int[nbAllumettes];
		for (int i = 0; i < nbAllumettes; i++) {
			allumettes[i] = 1;
		}
		// Affichage du jeu
		afficherGrundy(allumettes);
		// Début du jeu
		while (nbAllumettes > 0) {
			// Tour de l'utilisateur
			if (tour % 2 == 0) {
				nbAllumettesPrises = saisirNbAllumettes(nbAllumettesMax);
				nbAllumettes -= nbAllumettesPrises;
				for (int i = 0; i < nbAllumettesPrises; i++) {
					allumettes[nbAllumettes + i] = 0;
				}
				// Affichage du jeu
				afficherGrundy(allumettes);
			}
		}
	}

	void afficherGrundy(int[] allumettes) {
		for (int i = 0; i < allumettes.length; i++) {
			if (allumettes[i] == 1) {
				System.out.print("|");
			} else {
				System.out.print(" ");
			}
		}
		System.out.println();
	}

	int saisirNbAllumettes(int nbAllumettesMax) {
		int nbAllumettesPrises = 0;
		do {
			System.out.print("Combien d'allumettes prenez-vous ? ");
			nbAllumettesPrises = Clavier.lireInt();
		} while (nbAllumettesPrises < 1 || nbAllumettesPrises > nbAllumettesMax);
		return nbAllumettesPrises;
	}
}