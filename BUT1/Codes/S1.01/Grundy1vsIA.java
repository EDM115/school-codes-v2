/**
 * Jeu de Grundy - Joueur contre IA
 * @author EDM115
 **/

import java.util.Arrays;

class Grundy1vsIA {
	void principal() {
		testGuessLigne();
		testGuessAllumettes();
		System.out.println(" ");
		partie();
	}

	/**
	 * Demande au joueur le nombre d'allumettes pour la partie, ça doit être supérieur ou égal à 3 (sinon bah y'a pas de jeu)
	 * @return le nombre d'allumettes
	 **/
	int choixAllumettes() {
		int allumettes = SimpleInput.getInt("Entrez le nombre d'allumettes : ");
		while (allumettes < 3) {
			allumettes = SimpleInput.getInt("Entrez le nombre d'allumettes : ");
		}
		return allumettes;
	}

	/**
	 * Demande le nom du joueur
	 * @return le nom du joueur
	 **/
	String nomJoueur() {
		String joueur = SimpleInput.getString("Nom du joueur : ");
		while (joueur.length() < 1) { // Test probablement inutile
			joueur = SimpleInput.getString("Nom du joueur : ");
		}
		return joueur;
	}

	/**
	 * Demande la ligne à séparer
	 * @return la ligne
	 **/
	int numLigne() {
		int ligne = SimpleInput.getInt("Ligne à séparer : ");
		return ligne;
	}

	/**
	 * Choisit la ligne comportant le plus d'allumettes
	 * @param reponses le tableau d'allumettes
	 * @return le numéro de la ligne
	 **/
	int guessLigne(String[] reponses) {
		int[] possibilites = new int[reponses.length];
		int i = 0;
		int j = 0;
		while (i < reponses.length) {
			possibilites[i] = allumettesDansLigne(reponses[i]);
			if (possibilites[i] > j) {
				j = i;
			}
			i++;
		}
		return j;
	}

	/**
	 * Teste la méthode guessLigne()
	 **/
	void testGuessLigne() {
		System.out.println();
		System.out.println("*** testGuessLigne()");
		String[] tab1 = {" | | "};
		String[] tab2 = {" | | ", " | | | "};
		testCasGuessLigne(0, tab1);
		testCasGuessLigne(1, tab2);
	}

	/**
	 * teste un appel de guessLigne
	 * @param lg la ligne attendue
	 * @param rep un tableau d'alllumettes
	 **/
	void testCasGuessLigne(int lg, String[] rep) {
		// Arrange
		System.out.print("testCasGuessLigne(" + Arrays.toString(rep) + ") \t= " + lg + "\t : ");
		// Act
		int ligne = guessLigne(rep);
		// Assert
		if (ligne == lg) {
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}

	/**
	 * Demande le nombre d'allumettes à séparer
	 * @return le nombre d'allumettes
	 **/
	int numAllumettes() {
		int allumettes = SimpleInput.getInt("Nombre d'allumettes à séparer : ");
		return allumettes;
	}

	/**
	 * Choisit le nombre d'allumettes à séparer
	 * @param reponses le tableau d'allumettes
	 * @param ligne la ligne à séparer
	 * @return le nombre d'allumettes
	 **/
	int guessAllumettes(String[] reponses, int ligne) {
		int allumettes = 1;
		int nbAllumettes = allumettesDansLigne(reponses[ligne]);
		while (allumettes == Math.ceil(nbAllumettes) / 2) {
			allumettes++;
			if (allumettes > nbAllumettes) {
				allumettes = allumettes - 1;
			}	
		}
		return allumettes;
	}

	/**
	 * Teste la méthode guessAllumettes()
	 **/
	void testGuessAllumettes() {
		System.out.println();
		System.out.println("*** testGuessAllumettes()");
		String[] tab1 = {" | | | "};
		String[] tab2 = {" | | ", " | | | | | | "};
		testCasGuessAllumettes(0, tab1, 1);
		testCasGuessAllumettes(1, tab2, 1);
	}

	/**
	 * teste un appel de guessAllumettes
	 * @param lg la ligne attendue
	 * @param rep un tableau d'alllumettes
	 * @param result le résultat attendu
	 **/
	void testCasGuessAllumettes(int lg, String[] rep, int result) {
		// Arrange
		System.out.print("testCasGuessAllumettes(" + Arrays.toString(rep) + ", " + lg + ") \t= " + result + "\t : ");
		// Act
		int allumette = guessAllumettes(rep, lg);
		// Assert
		if (allumette == result) {
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}

	/**
	 * Essaie de trouver le meilleur coup
	 * @param reponses le tableau d'allumettes
	 * @return un tableau avec la ligne à choisir et le nombre d'allumettes à séparer
	 **/
	int[] guessMeilleurCoup(String[] reponses) {
		int[] choix = new int[2];
		choix[0] = 0;
		if (reponses.length > 1) { // Pour ne pas demander inutilement la ligne si on est au premier coup
			choix[0] = guessLigne(reponses);
		}
		choix[1] = guessAllumettes(reponses, choix[0]);
		return choix;
	}

	/**
	 * Affiche le joueur qui doit jouer
	 * @param joueur le joueur
	 **/
	void tour(String joueur) {
		System.out.println("C'est au tour de " + joueur);
	}

	/**
	 * Crée une chaîne de caractère représentant un tas d'allumettes
	 * @param lg le nombre d'allumettes
	 * @return la chaîne de caractères
	 **/
	String creerReponse(int lg) {
		String reponse = " ";
		for (int i = 0; i < lg; i++) {
			reponse = reponse + "| ";
		}
		return reponse;
	}

	/**
	 * Teste la méthode creerReponse()
	 **/
	void testCreerReponse() {
		System.out.println();
		System.out.println("*** testCreerReponse()");
		testCasCreerReponse(0, " ");
		testCasCreerReponse(7, " | | | | | | | ");
	}

	/**
	 * teste un appel de creerReponse
	 * @param lg le nombre d'allumettes
	 * @param rep valeur attendue de la chaîne représentant le tas d'alllumettes
	 **/
	void testCasCreerReponse(int lg, String rep) {
		// Arrange
		System.out.print("testCasCreerReponse(" + lg + ") \t= " + rep + "\t : ");
		// Act
		String reponse = creerReponse(lg);
		boolean resExec = rep.equals(reponse);
		// Assert
		if (resExec){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}

	/**
	 * Affiche les lignes d'un tableau d'allumettes
	 * @param reponses le tableau
	 **/
	void afficheLignes(String[] reponses) {
		for (int i = 0; i < reponses.length; i++) {
			System.out.println(i + " :\t" + reponses[i]);
		}
		System.out.println("");
	}

	/**
	 * Renvoie le nombre d'allumettes présentes dans une ligne
	 * @param ligne la ligne
	 * @return le nombre d'allumettes
	 **/
	int allumettesDansLigne(String ligne) {
		int allumettesDansLigne = 0;
		int allumettePresente;
		for (int i = 0; i < ligne.length(); i++) {
			allumettePresente = ligne.charAt(i);
			if (allumettePresente == '|') {
				allumettesDansLigne++;
			} 
		}
		return allumettesDansLigne;
	}

	/**
	 * Teste la méthode allumettesDansLigne()
	 **/
	void testAllumettesDansLigne() {
		System.out.println();
		System.out.println("*** testAllumettesDansLigne()");
		testCasAllumettesDansLigne(0, " ");
		testCasAllumettesDansLigne(7, " | | | | | | | ");
	}

	/**
	 * teste un appel de allumettesDansLigne
	 * @param lg le nombre d'allumettes attendu
	 * @param rep la chaîne représentant le tas d'alllumettes
	 **/
	void testCasAllumettesDansLigne(int lg, String rep) {
		// Arrange
		System.out.print("testCasAllumettesDansLigne(" + rep + ") \t= " + lg + "\t : ");
		// Act
		int allumettes = allumettesDansLigne(rep);
		boolean resExec = allumettes == lg;
		// Assert
		if (resExec){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}

	/**
	 * Ajoute une ligne à un tableau d'allumettes à une certaine position
	 * @param tabRep le tableau d'allumettes
	 * @param ligne le numéro de ligne où on doit insérer la ligne
	 * @param reponse la ligne
	 * @return le tableau complété
	 **/
	String[] ajouteLigne(String[] tabRep, int ligne, String reponse) {
		int len = tabRep.length;
		String[] tab = new String[len + 1];
		if (ligne < len) {
			int i = 0;
			while (i < ligne) {
				tab[i] = tabRep[i];
				i++;
			}
			tab[ligne] = reponse;
			i++;
			while (i < len + 1) {
				tab[i] = tabRep[i - 1];
				i++;
			}
		} else { // On ajoute simplement à la fin
			int i = 0;
			while (i < len) {
				tab[i] = tabRep[i];
				i++;
			}
			tab[i] = reponse;
		}
		return tab;
	}

	/**
	 * Teste la méthode ajouteLigne()
	 **/
	void testAjouteLigne() {
		System.out.println();
		System.out.println("*** testAjouteLigne()");
		String[] test1 = {};
		String[] test2 = {"val1", "val2", "val3"};
		String value = "val4";
		String[] result1 = {"val4"};
		String[] result2 = {"val1", "val2", "val4", "val3"};
		String[] result3 = {"val1", "val2", "val3", "val4"};
		testCasAjouteLigne(test1, 0, value, result1);
		testCasAjouteLigne(test2, 2, value, result2);
		testCasAjouteLigne(test2, 3, value, result3);
	}

	/**
	 * teste un appel de ajouteLigne
	 * @param tabRep le tableau d'allumettes
	 * @param ligne la ligne où on doit insérer value
	 * @param value un tas d'allumettes
	 * @param result le résultat attendu
	 **/
	void testCasAjouteLigne(String[] tabRep, int ligne, String value, String[] result) {
		// Arrange
		System.out.print("testCasAjouteLigne(" + Arrays.toString(tabRep) + ", " + ligne + ", " + value + ") \t= " + Arrays.toString(result) + "\t : ");
		// Act
		String[] reponse = ajouteLigne(tabRep, ligne, value);
		boolean resExec = Arrays.equals(result, reponse);
		// Assert
		if (resExec){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}

	/**
	 * Remplace une ligne d'un tableau d'allumettes à une certaine position
	 * @param tabRep le tableau d'allumettes
	 * @param ligne le numéro de ligne où on doit remplacer la ligne
	 * @param reponse la ligne
	 * @return le tableau complété
	 **/
	String[] remplaceLigne(String[] tabRep, int ligne, String reponse) {
		int len = tabRep.length;
		String[] tab = new String[len];
		int i = 0;
		while (i < ligne) {
			tab[i] = tabRep[i];
			i++;
		}
		tab[ligne] = reponse;
		i++;
		while (i < len) {
			tab[i] = tabRep[i];
			i++;
		}
		return tab;
	}

	/**
	 * Teste la méthode remplaceLigne()
	 **/
	void testRemplaceLigne() {
		System.out.println();
		System.out.println("*** testRemplaceLigne()");
		String[] test = {"val1", "val2", "val3"};
		String value = "val4";
		String[] result1 = {"val4", "val2", "val3"};
		String[] result2 = {"val1", "val4", "val3"};
		String[] result3 = {"val1", "val2", "val4"};
		testCasRemplaceLigne(test, 0, value, result1);
		testCasRemplaceLigne(test, 1, value, result2);
		testCasRemplaceLigne(test, 2, value, result3);
	}

	/**
	 * teste un appel de remplaceLigne
	 * @param tabRep le tableau d'allumettes
	 * @param ligne la ligne qu'on doit remplacer par value
	 * @param value un tas d'allumettes
	 * @param result le résultat attendu
	 **/
	void testCasRemplaceLigne(String[] tabRep, int ligne, String value, String[] result) {
		// Arrange
		System.out.print("testCasRemplaceLigne(" + Arrays.toString(tabRep) + ", " + ligne + ", " + value + ") \t= " + Arrays.toString(result) + "\t : ");
		// Act
		String[] reponse = remplaceLigne(tabRep, ligne, value);
		boolean resExec = Arrays.equals(result, reponse);
		// Assert
		if (resExec){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}

	/**
	 * Divise la ligne d'un tableau d'allumettes en 2 tas distincts, puis les réajoute au tableau
	 * @param ligne la ligne à couper
	 * @param nbAllumettes le nombre d'allumettes à séparer
	 * @param reponse le tableau d'allumettes
	 * @return le tableau avec la séparation
	 **/
	String[] divise(int ligne, int nbAllumettes, String[] reponse) {
		int len = reponse.length;
		String[] newReponse = new String[len + 1];
		String ligneACouper = reponse[ligne];
		int allumettesDansLigne = allumettesDansLigne(ligneACouper);
		int allumettesRestantes = allumettesDansLigne - nbAllumettes;
		String allumettes1 = creerReponse(nbAllumettes);
		newReponse = remplaceLigne(reponse, ligne, allumettes1);
		String allumettes2 = creerReponse(allumettesRestantes);
		newReponse = ajouteLigne(newReponse, ligne + 1, allumettes2);
		return newReponse;
	}

	/**
	 * Teste la méthode divise()
	 **/
	void testDivise() {
		System.out.println();
		System.out.println("*** testDivise()");
		String[] tableau = {creerReponse(2), creerReponse(1), creerReponse(5), creerReponse(4)};
		String[] result1 = {creerReponse(2), creerReponse(1), creerReponse(2), creerReponse(3), creerReponse(4)};
		String[] result2 = {creerReponse(2), creerReponse(1), creerReponse(1), creerReponse(4), creerReponse(4)};
		String[] result3 = {creerReponse(2), creerReponse(1), creerReponse(5), creerReponse(3), creerReponse(1)};
		testCasDivise(2, 2, tableau, result1);
		testCasDivise(2, 1, tableau, result2);
		testCasDivise(3, 3, tableau, result3);
	}

	/**
	 * teste un appel de divise
	 * @param ligne la ligne à couper
	 * @param nbAllumettes le nombre d'allumettes à séparer
	 * @param reponse le tableau d'allumettes
	 * @param result le tableau avec la séparation
	 **/
	void testCasDivise(int ligne, int nbAllumettes, String[] reponse, String[] result) {
		// Arrange
		System.out.print("testCasDivise(" + ligne + ", " + nbAllumettes + ", " + Arrays.toString(reponse) + ") \t= " + Arrays.toString(result) + "\t : ");
		// Act
		String[] rep = divise(ligne, nbAllumettes, reponse);
		boolean resExec = Arrays.equals(rep, result);
		// Assert
		if (resExec){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}

	/**
	 * Détermine si la partie est terminée
	 * @param partie le tableau d'allumettes
	 * @return vrai ssi la partie est terminée
	 */
	boolean estFinie(String[] partie) {
		int len = partie.length;
		boolean estFinie = true;
		int i = 0;
		while (estFinie && i < len) {
			int nbAllu = allumettesDansLigne(partie[i]);
			if (nbAllu > 2) {
				estFinie = false;
			}
			i++;
		}
		return estFinie;
	}

	/**
	 * Teste la méthode estFinie()
	 **/
	void testEstFinie() {
		System.out.println();
		System.out.println("*** testEstFinie()");
		String[] partie1 = {creerReponse(7)};
		String[] partie2 = {creerReponse(1), creerReponse(1)};
		String[] partie3 = {creerReponse(2)};
		testCasEstFinie(partie1, false);
		testCasEstFinie(partie2, true);
		testCasEstFinie(partie3, true);
	}

	/**
	 * teste un appel de estFinie
	 * @param partie le tableau d'allumettes
	 * @param result valeur attendue (true ou false)
	 **/
	void testCasEstFinie(String[] partie, boolean result) {
		// Arrange
		System.out.print("testCasEstFinie(" + Arrays.toString(partie) + ") \t= " + result + "\t : ");
		// Act
		boolean resExec = estFinie(partie);
		// Assert
		if (resExec == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}

	/**
	 * Vérifie quel coup est gagnant pour l'IA
	 * @param reponses le tableau d'allumettes
	 * @param nbAllumettes le nombre d'allumettes à retirer
	 * @return la ligne gagnante et le nombre d'allumettes à enlever
	 */
	int[] verifPartieFinie(String[] reponses, int nbAllumettes) {
		int len = reponses.length;
		int ligneGagnante[] = {-1, nbAllumettes}; // ligne gagnante et nombre d'allumettes à enlever
		int j = 0;
		int[] lignes = new int[len];
		while (j < len) { // On garde le nombre d'allumettes dans chaque ligne
			lignes[j] = allumettesDansLigne(reponses[j]);
			j++;
		}
		j = 0;
		boolean uneSeuleLigneUtilisable = true;
		int i = 0;
		while (uneSeuleLigneUtilisable && j < len) { // On vérifie qu'il n'y a qu'une seule ligne avec + de 2 allumettes
			if (lignes[j] > 2) {
				i++;
			}
			if (i > 1) {
				uneSeuleLigneUtilisable = false;
			}
			j++;
		}
		if (uneSeuleLigneUtilisable) {
			j = 0;
			while (j < len) {
				if (lignes[j] > 2) { // On cherche la ligne avec + de 2 allumettes
					ligneGagnante[0] = j;
					break;
				}
				j++;
			}
			int k = 0;
			while (k < lignes[j]) {
				String[] newReponse = Arrays.copyOf(reponses, len);
				newReponse = divise(j, k, newReponse);
				if (estFinie(newReponse)) { // Si il y a une combinaison gagnante, on la prend
					ligneGagnante[1] = k;
					break;
				}
				k++;
			}
		}
		return ligneGagnante;
	}

	/**
	 * Permet de jouer une partie
	 **/
	void partie() {
		boolean partieFinie = false;
		int allumettes = choixAllumettes();
		String j1 = nomJoueur();
		String j2 = "l'IA";
		int nbCoupsIA = 0;
		String reponse = creerReponse(allumettes);
		String[] reponses = {reponse};
		String joueur = j1; // On commencera toujours avec le joueur 1
		afficheLignes(reponses);
		while (!partieFinie) {
			tour(joueur);
			if (joueur == j1) {
				int divLigne = 0;
				if (reponses.length > 1) { // Pour ne pas demander inutilement la ligne si on est au premier coup
					divLigne = numLigne();
					while (divLigne < 0 || divLigne >= reponses.length || (divLigne <= reponses.length && allumettesDansLigne(reponses[divLigne]) < 3)) { // On vérifie que la ligne est utilisable, et qu'il y a plus de 2 allumettes dedans
						divLigne = numLigne();
					}
				}
				int divAllumettes = numAllumettes();
				while (divAllumettes < 1 || divAllumettes >= allumettesDansLigne(reponses[divLigne]) || divAllumettes == Math.ceil(allumettesDansLigne(reponses[divLigne])) / 2) { // On vérifie que le nombre d'allumettes ne dépasse pas celui disponible et qu'on peut faire 2 tas distincts
					divAllumettes = numAllumettes();
				}
				reponses = divise(divLigne, divAllumettes, reponses);
			} else { // C'est l'IA qui joue
				int[] choix = guessMeilleurCoup(reponses);
				if (allumettes % 2 != 0 && nbCoupsIA == 0) { // Si le nombre d'allumettes est impair et qu'on est au premier coup, on enlève 2 allumettes au lieu d'une seule
					choix[1] = 2;
				}
				System.out.println("Ligne à séparer : " + choix[0]);
				System.out.println("Nombre d'allumettes à séparer : " + choix[1]);
				int[] bestChoice = verifPartieFinie(reponses, choix[1]);
				if (bestChoice[0] == -1) { // Si on a pas déterminé de coup gagnant, on prend le coup de l'IA 
					reponses = divise(choix[0], choix[1], reponses);
				} else {
					reponses = divise(bestChoice[0], bestChoice[1], reponses);
				}
				nbCoupsIA++;
			}
			afficheLignes(reponses);

			partieFinie = estFinie(reponses);
			if (!partieFinie) { // Pour éviter de donner le mauvais nom pour l'annonce du vainqueur
				if (joueur == j1) {
					joueur = j2;
				} else {
					joueur = j1;
				}
			}
		}
		System.out.println("Bravo " + joueur + " ! Tu as gagné"); // 🥳
	}
}

// Difficulté de l'IA : choose ligne + random && allumettes / 2 - 1 si pair