import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Cette classe effectue des opérations élémentaires sur un ou plusieurs tableaux d'entiers. La taille d'un tableau est par définition le nombre TOTAL de cases = tab.length. Un tableau d'entiers créé possède nb éléments qui est nécessairement inférieur ou égal à la taille du tableau : nb &lt;= tab.length (= taille)
 * @author EDM115
 **/
public class SimplesTableau {

	/**
	 * Le point d'entrée du programme
	 **/
	void principal() {
		testVerifTab();
		testAfficherTab();
		testTirerAleatoire();
		testRemplirAleatoire();
		testEgalite();
		testCopier();
		testMax();
		testInverse();
		testEchange();
		testMelange();
		testDecalerGauche();
		testSupprimerUneValeur();
		testInclusion();
		testRemplirToutesDiff();
	}

	/**
	 * Une méthode que j'utilise pour vider facilement un tableau (par exemple dans les tests)
	 * @param tab un tableau d'entiers
	 **/
	void viderTab(int[] tab) {
		if (tab != null) {
			int len = tab.length;
			for (int i = 0; i < len; i++) {
				tab[i] = 0;
			}
		}
	}

	/**
	 * Factorisation : appelé chaque fois qu'une vérification doit se faire sur la validité d'un tableau. Le tableau n'est pas valide si celui-ci est inexistant ou si le nombre d'éléments qu'il contiendra est incompatible avec sa taille (tab.length)
	 * @param tab le tableau dont on doit vérifier l'existence (tab différent de null)
	 * @param nb le nombre d'éléments que contiendra le tableau, doit vérifier la condition 0 &lt; nb &lt;= tab.length
	 * @return vrai ssi le tableau est valide
	 **/
	boolean verifTab(int[] tab, int nb) {
		boolean verif = true;
 		if (tab == null) {
			verif = false;
			System.err.println("Tableau null");
		} else if (nb < 1 || nb > tab.length) {
			verif = false;
			System.err.println("Nombre d'élements pas dans les bornes");
		}
		return verif;
	}

	void testVerifTab() {
		System.out.println("");
		System.out.println("*** testVerifTab()");
		// Cas normaux
		int[] tab1 = {11, 23, 14};
		testCasVerifTab(tab1, 2, true);
		testCasVerifTab(tab1, -1, false);
		// Cas limites
		int[] tab2 = null;
		testCasVerifTab(tab2, 0, false);
	}

	void testCasVerifTab(int[] tab, int nb, boolean result) {
		System.out.print("testCasVerifTab(" + Arrays.toString(tab) + ", " + nb + ") : ");
		boolean temp = verifTab(tab, nb);
		if (temp == result) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}

	/**
	 * Affiche le contenu des nb cases d'un tableau une par une. Tenir compte des cas d'erreurs concernant tab et nb (appel de verifTab)
	 * @param tab le tableau à afficher
	 * @param nb le nombre d'entiers que contient le tableau
	 **/
	void afficherTab(int[] tab, int nb) {
		boolean verif = verifTab(tab, nb);
		if (!verif) {
			System.err.println("Affichage impossible");
		} else {
			String tableau = "[";
			for (int i = 0; i < nb; i++) {
				tableau = tableau + tab[i];
				if (i != nb - 1) {
					tableau = tableau + ", ";
				}
			}
			tableau = tableau + "]";
			System.out.println(tableau);
		}
	}

	void testAfficherTab() {
		System.out.println("");
		System.out.println("*** testAfficherTab()");
		// Cas normaux
		int[] tab1 = {11, 105, -11};
		testCasAfficherTab(tab1, 2, false);
		testCasAfficherTab(tab1, 3, false);
		// Cas d'erreurs
		int[] tab2 = null;
		testCasAfficherTab(tab1, 4, true);
		testCasAfficherTab(tab1, 0, true);
		testCasAfficherTab(tab2, 2, true);
	}

	void testCasAfficherTab(int[] tab, int nb, boolean err) {
		System.out.print("testCasAfficherTab(" + Arrays.toString(tab) + ", " + nb + ") : ");
		if (err) {
			System.out.println("Il y aura une erreur");
		} else {
			System.out.println("Ca s'affichera correctement");
		}
		afficherTab(tab, nb);
	}

	/**
	 * Renvoie un entier aléatoire compris entre min et max (min &lt;= valeur &lt;= max). Vérifier que min &lt;= max et min &gt;= 0, sinon afficher une erreur (et dans ce cas retourner -1)
	 * @param min la valeur de l'entier minimum
	 * @param max la valeur de l'entier maximum
	 * @return l'entier aléatoire
	 **/
	int tirerAleatoire(int min, int max) {
		int aleatoire = 0;
		if (min > max || min < 0) {
			System.err.println("Les valeurs ne sont pas correctes");
			aleatoire = -1;
		} else { // https://stackoverflow.com/a/363692/18644204
			aleatoire = ThreadLocalRandom.current().nextInt(min, max + 1);
		}
		return aleatoire;
	}

	void testTirerAleatoire() {
		System.out.println("");
		System.out.println("*** testTirerAleatoire()");
		// Cas normaux
		testCasTirerAleatoire(1, 5, false);
		testCasTirerAleatoire(10, 50, false);
		// Cas limites
		testCasTirerAleatoire(3, 3, false);
		testCasTirerAleatoire(0, 0, false);
		// Cas d'erreur
		testCasTirerAleatoire(-5, 5, true);
		testCasTirerAleatoire(8, 5, true);
	}

	void testCasTirerAleatoire(int min, int max, boolean err) {
		System.out.print("testCasTirerAleatoire(" + min + ", " + max + ") : ");
		int temp = tirerAleatoire(min, max);
		System.out.println(temp);
		if (temp != -1 || (temp == -1 && err)) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}

	/**
	 * A partir d'un tableau créé, remplir aléatoirement ce tableau de nb valeurs comprises entre min et max. Tenir compte des cas d'erreurs concernant tab et nb (appel de verifTab). Vérifier que min &lt;= max, sinon afficher également une erreur. Utiliser obligatoirement la méthode "int tirerAleatoire(int min, int max)"
	 * @param tab le tableau à remplir de valeurs tirées aléatoirement
	 * @param nb le nombre d'entiers que contiendra le tableau
	 * @param min la valeur de l'entier minimum
	 * @param max la valeur de l'entier maximum
	 **/
	void remplirAleatoire(int[] tab, int nb, int min, int max) {
		boolean verif = verifTab(tab, nb);
		if (!verif) {
			System.err.println("Impossible de remplir ce tableau");
		} else {
			if (min > max) {
				System.err.println("min > max");
			} else {
				for (int i = 0; i < nb; i++) {
					tab[i] = tirerAleatoire(min, max);
				}
			}
		}
	}

	void testRemplirAleatoire() {
		System.out.println("");
		System.out.println("*** testRemplirAleatoire()");
		// Cas normaux
		int[] tab1 = new int[5];
		testCasRemplirAleatoire(tab1, 3, 0, 10, false);
		viderTab(tab1);
		testCasRemplirAleatoire(tab1, 5, 0, 8, false);
		viderTab(tab1);
		// Cas d'erreur
		int[] tab2 = new int[0];
		int[] tab3 = null;
		testCasRemplirAleatoire(tab1, 0, 0, 10, true);
		viderTab(tab1);
		testCasRemplirAleatoire(tab1, 3, 20, 10, true);
		viderTab(tab1);
		testCasRemplirAleatoire(tab2, 0, 0, 10, true);
		viderTab(tab2);
		testCasRemplirAleatoire(tab3, 3, 0, 10, true);
		viderTab(tab3);
	}

	void testCasRemplirAleatoire(int[] tab, int nb, int min, int max, boolean err) {
		System.out.print("testCasRemplirAleatoire(" + Arrays.toString(tab) + ", " + nb + ", " + min + ", " + max + ") : ");
		if (err) {
			System.err.println("Il y aura une erreur");
		}
		remplirAleatoire(tab, nb, min, max);
		System.out.println(Arrays.toString(tab));
	}

	/**
	 * Renvoie vrai si les 2 tableaux passés en paramètre sont exactement les mêmes en nombre d'éléments et en contenu (case par case). Tenir compte des cas d'erreurs concernant tab1, nb1 et tab2, nb2 (appel de verifTab 2 fois) et afficher un éventuel message d'erreur (et dans ce cas renvoyer faux)
	 * @param tab1 le 1er tableau à comparer
	 * @param tab2 le 2ème tableau à comparer
	 * @param nb1 le nombre d'entiers présents dans le 1er tableau
	 * @param nb2 le nombre d'entiers présents dans le 2ème tableau
	 * @return true si égalité parfaite sinon false
	 **/
	boolean egalite(int[] tab1, int[] tab2, int nb1, int nb2) {
		boolean egal = true;
		boolean verif1 = verifTab(tab1, nb1);
		if (!verif1) {
			System.err.println("Problème avec le premier tableau");
		}
		boolean verif2 = verifTab(tab2, nb2);
		if (!verif2) {
			System.err.println("Problème avec le second tableau");
		}
		if (verif1 && verif2) {
			if (nb1 != nb2) {
				System.err.println("Les tableaux n'ont pas la même taille (nb1 != nb2)");
				egal = false;
			} else {
				int i = 0;
				while (egal && i < nb1) {
					if (tab1[i] != tab2[i]) {
						egal = false;
					}
					i++;
				}
			}
		} else {
			egal = false;
		}
		return egal;
	}

	void testEgalite() {
		System.out.println("");
		System.out.println("*** testEgalite()");
		// Cas normaux
		int[] tab1 = {1, 2, 3, 4, 5};
		testCasEgalite(tab1, tab1, 5, 5, true);
		// Cas d'erreur
		int[] tab2 = {5, 4, 3, 2, 1};
		int[] tab3 = null;
		int[] tab4 = {1, 2, 3};
		testCasEgalite(tab1, tab2, 5, 5, false);
		testCasEgalite(tab1, tab3, 5, 0, false);
		testCasEgalite(tab1, tab4, 5, 3, false);
		testCasEgalite(tab1, tab1, 5, 3, false);
	}

	void testCasEgalite(int[] tab1, int[] tab2, int nb1, int nb2, boolean result) {
		System.out.print("testCasEgalite(" + Arrays.toString(tab1) + ", " + Arrays.toString(tab2) + ", " + nb1 + ", " + nb2 + ") : ");
		boolean temp = egalite(tab1, tab2, nb1, nb2);
		if (temp == result) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}

	/**
	 * Renvoie la copie exacte (clone) du tableau passé en paramètre
	 * @param tabToCopy le tableau à copier
	 * @param nb le nombre d'entiers présents dans le tableau
	 * @return le nouveau tableau qui est la copie du tableau passé en paramètre
	 **/
	int[] copier(int[] tabToCopy, int nb) {
		int[] copie = new int[0];
		boolean verif = verifTab(tabToCopy, nb);
		if (!verif) {
			System.err.println("Impossible de copier le tableau");
		} else {
			copie = new int[tabToCopy.length];
			for (int i = 0; i < nb; i++) {
				copie[i] = tabToCopy[i];
			}
		}
		return copie;
	}

	void testCopier() {
		System.out.println("");
		System.out.println("*** testCopier()");
		// Cas normaux
		int[] tab1 = {1, 2, 3, 4};
		testCasCopier(tab1, 4, false);
		// Cas d'erreur
		testCasCopier(tab1, 0, true);
	}

	void testCasCopier(int[] tabToCopy, int nb, boolean err) {
		System.out.print("testCasCopier(" + Arrays.toString(tabToCopy) + ", " + nb + ") : ");
		int[] temp = copier(tabToCopy, nb);
		if (Arrays.equals(tabToCopy, temp) || (err && !Arrays.equals(tabToCopy, temp))) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}

	/**
	 * Renvoie le maximum parmi les éléments du tableau
	 * @param tab le tableau
	 * @param nb le nombre d'entiers présents dans le tableau
	 * @return le maximum des éléments du tableau
	 */
	int max(int[] tab, int nb) {
		int max = 0;
		boolean verif = verifTab(tab, nb);
		if (!verif) {
			System.err.println("Impossible de trouver le max du tableau");
		} else {
			max = tab[0];
			int i = 1;
			while (i < nb) {
				if (tab[i] > max) {
					max = tab[i];
				}
				i++;
			}
		}
		return max;
	}

	void testMax() {
		System.out.println("");
		System.out.println("*** testMax()");
		// Cas normaux
		int[] tab1 = {1, 4, 3, 0};
		int[] tab2 = {-5, 28, 168, 784898561};
		testCasMax(tab1, 4, 4, false);
		testCasMax(tab2, 4, 784898561, false);
		// Cas d'erreur
		testCasMax(tab2, 0, 0, true);
	}

	void testCasMax(int[] tab, int nb, int max, boolean err) {
		System.out.print("testCasMax(" + Arrays.toString(tab) + ", " + nb + ", " + max + ") : ");
		int temp = max(tab, nb);
		System.out.println(temp);
		if (temp == max || (temp != max && err)) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}

	/**
	 * Renvoie un nouveau tableau qui est l'inverse de celui passé en paramètre. Son j-ème élément est égal au (nb + 1 - j) élément du tableau initial (dans l'explication, j = 1 signifie premier élément du tableau)
	 * @param tab le tableau
	 * @param nb le nombre d'entiers présents dans le tableau
	 * @return le nouveau tableau qui est l'inverse de tab sur la plage (0 ... nb - 1)
	 */
	int[] inverse(int[] tab, int nb) {
		int[] inverse = copier(tab, nb);
		boolean verif = verifTab(tab, nb);
		if (!verif) {
			System.err.println("Impossible d'inverser le tableau");
		} else {
			int i = 0;
			int j = nb - 1;
			while (i < nb / 2) {
				if ((j - i) != 0) {
					int temp = inverse[j];
					inverse[j] = inverse[i];
					inverse[i] = temp;
				}
				i++;
				j--;
			}
		}
		return inverse;
	}

	void testInverse() {
		System.out.println("");
		System.out.println("*** testInverse()");
		// Cas normaux
		int[] tab1 = {1, 2, 3, 4};
		int[] result1 = {4, 3, 2, 1};
		int[] tab2 = {8, 6, 4, 0, 2};
		int[] result2 = {2, 0, 4, 6, 8};
		testCasInverse(tab1, 4, result1, false);
		testCasInverse(tab2, 5, result2, false);
		// Cas d'erreur
		testCasInverse(tab2, 0, result2, true);
	}

	void testCasInverse(int[] tab, int nb, int[] result, boolean err) {
		System.out.print("testCasInverse(" + Arrays.toString(tab) + ", " + nb + ") : ");
		int[] temp = inverse(tab, nb);
		System.out.println(Arrays.toString(temp));
		if (Arrays.equals(result, temp) || (err && !Arrays.equals(result, temp))) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}

	/**
	 * Echange les contenus des cases du tableau passé en paramètre, cases identifiées par les indices ind1 et ind2. Vérifier que les indices ind1 et ind2 sont bien compris entre zéro et (nb - 1), sinon afficher un message d'erreur
	 * @param tab le tableau
	 * @param nb le nombre d'entiers présents dans le tableau
	 * @param ind1 numéro de la première case à échanger
	 * @param ind2 numéro de la deuxième case à échanger
	 */
	void echange(int[] tab, int nb, int ind1, int ind2) {
		boolean verif = verifTab(tab, nb);
		if (!verif) {
			System.err.println("Impossible d'échanger les valeurs du tableau");
		} else {
			boolean err1 = false;
			boolean err2 = false;
			if (ind1 > nb - 1 || ind1 < 0) {
				System.err.println("ind1 pas dans les bornes");
				err1 = true;
			} if (ind2 > nb - 1 || ind2 < 0) {
				System.err.println("ind2 pas dans les bornes");
				err2 = true;
			} if (!err1 && !err2) {
				int temp = tab[ind1];
				tab[ind1] = tab[ind2];
				tab[ind2] = temp;
			}
		}
	}

	void testEchange() {
		System.out.println("");
		System.out.println("*** testEchange()");
		// Cas normaux
		int[] tab1 = {10, 20, 88, 69};
		int[] result1 = {69, 20, 88, 10};
		testCasEchange(tab1, 4, 3, 0, result1, false);
		// Cas limite
		testCasEchange(tab1, 4, 3, 3, tab1, false);
		// Cas d'erreur
		testCasEchange(tab1, 0, 0, 0, result1, true);
		testCasEchange(tab1, 4, -5, 13, result1, true);
	}

	void testCasEchange(int[] tab, int nb, int ind1, int ind2, int[] result, boolean err) {
		System.out.print("testCasEchange(" + Arrays.toString(tab) + ", " + nb + ", " + ind1 + ", " + ind2 + ") : ");
		echange(tab, nb, ind1, ind2);
		System.out.println(Arrays.toString(tab));
		if (Arrays.equals(result, tab) || (err && !Arrays.equals(result, tab))) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}

	/**
	 * Retourne un nouveau tableau qui a la même taille et les mêmes occurrences d'élements que le tableau passé en paramètre mais ces éléments sont répartis selon des indices aléatoires (0 &lt;= indice &lt;= nbElem - 1). Une technique simple consiste à utiliser les méthodes "echange" et "tirerAleatoire" pour effectuer le mélange
	 * @param tab le tableau
	 * @param nb le nombre d'entiers présents dans le tableau
	 * @return le nouveau tableau qui a le même contenu que le tableau initial mais mélangé
	 */
	int[] melange(int[] tab, int nb) {
		int[] melange = copier(tab, nb);
		boolean verif = verifTab(tab, nb);
		if (!verif) {
			System.err.println("Impossible de mélanger le tableau");
		} else {
			int i = 0;
			while (i < nb) {
				int random1 = tirerAleatoire(0, nb - 1);
				int random2 = tirerAleatoire(0, nb - 1);
				while (random2 == random1) {
					random2 = tirerAleatoire(0, nb - 1);
				}
				echange(melange, nb, random1, random2);
				i++;
			}
			if (Arrays.equals(tab, melange)) {
				melange = melange(tab, nb);
			}
		}
		return melange;
	}

	void testMelange() {
		System.out.println("");
		System.out.println("*** testMelange()");
		// Cas normaux
		int[] tab1 = {10, 20, 88, 69};
		testCasMelange(tab1, 4, false);
		// Cas d'erreur
		//int[] tab2 = {8};
		//testCasMelange(tab2, 1, true); (c'est tellement un cas d'erreur que Java aime pas exécuter ça)
		testCasMelange(tab1, 0, true);
	}

	void testCasMelange(int[] tab, int nb, boolean err) {
		System.out.print("testCasMelange(" + Arrays.toString(tab) + ", " + nb + ") : ");
		int[] temp = melange(tab, nb);
		System.out.println(Arrays.toString(temp));
		if ((!Arrays.equals(temp, tab) && !err) || err) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}

	/**
	 * Décale de une case de la droite vers la gauche toutes les cases d'un tableau à partir d'un indice "ind" et jusque nb - 1 ([ind] &lt;- [ind + 1] &lt;- [ind + 2] &lt;- ... &lt;- [nb - 2] &lt;- [nb - 1]). Vérifier que ind est compris entre 0 et (nb - 2) sinon afficher une erreur
	 * @param tab le tableau
	 * @param nb le nombre d'entiers présents dans le tableau
	 * @param ind l'indice à partir duquel commence le décalage à gauche
	 */
	void decalerGauche(int[] tab, int nb, int ind) {
		boolean verif = verifTab(tab, nb);
		if (!verif) {
			System.err.println("Impossible de décaler le tableau");
		} else {
			if (ind < 0 || ind > nb - 2) {
				System.err.println("L'indice n'est pas bon");
			} else {
				int i = ind + 1;
				while (i < nb) {
					tab[ind] = tab[i];
					i++;
					ind++;
				}
			}
		}
	}

	void testDecalerGauche() {
		System.out.println("");
		System.out.println("*** testDecalerGauche()");
		// Cas normaux
		int[] tab1 = {10, 20, 30, 40};
		int[] tab2 = {10, 20, 30, 40};
		int[] tab3 = {10, 20, 30, 40};
		int[] tab4 = {10, 20, 30, 40};
		int[] result1 = {20, 30, 40, 40};
		int[] result2 = {10, 30, 40, 40};
		testCasDecalerGauche(tab1, 4, 0, result1, false);
		testCasDecalerGauche(tab2, 4, 1, result2, false);
		// Cas d'erreur
		testCasDecalerGauche(tab3, 0, 0, result1, true);
		testCasDecalerGauche(tab4, 4, 3, result2, true);

	}

	void testCasDecalerGauche(int[] tab, int nb, int ind, int[] result, boolean err) {
		System.out.print("testCasDecalerGauche(" + Arrays.toString(tab) + ", " + nb + ", " + ind + ") : ");
		decalerGauche(tab, nb, ind);
		System.out.println(Arrays.toString(tab));
		if ((Arrays.equals(tab, result) && !err) || (!Arrays.equals(tab, result) && err)) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}

	/**
	 * Supprime du tableau la première case rencontrée dont le contenu est égale à "valeur". La case du tableau est supprimée par décalage à gauche des cases du tableau. L'appel de la méthode "decalerGauche" est obligatoire. A l'issue de la suppression (si elle existe) le nombre d'éléments dans le tableau est décrémenté et retourné
	 * @param tab le tableau
	 * @param nb le nombre d'entiers présents dans le tableau
	 * @param valeur le contenu de la première case à supprimer
	 * @return le nombre d'éléments dans le tableau (éventuellement inchangé)
	 */
	int supprimerUneValeur(int[] tab, int nb, int valeur) {
		int elems = nb;
		boolean verif = verifTab(tab, nb);
		if (!verif) {
			System.err.println("Impossible de supprimer une valeur du tableau");
		} else {
			int i = 0;
			boolean found = false;
			while (!found && i < nb) {
				if (tab[i] == valeur) {
					decalerGauche(tab, nb, i);
					found = true;
					elems--;
				}
				i++;
			}
		}
		return elems;
	}

	void testSupprimerUneValeur() {
		System.out.println("");
		System.out.println("*** testSupprimerUneValeur()");
		// Cas normaux
		int[] tab1 = {5, 8, 16, 83};
		int[] tab2 = {11, 12, 13};
		testCasSupprimerUneValeur(tab1, 4, 5, 3, false);
		testCasSupprimerUneValeur(tab1, 3, 83, 2, false);
		testCasSupprimerUneValeur(tab1, 2, 16, 1, false);
		testCasSupprimerUneValeur(tab2, 3, 0, 3, false);
		// Cas d'erreur
		testCasSupprimerUneValeur(tab1, 1, 8, 0, true);
		testCasSupprimerUneValeur(tab2, 0, 0, 0, true);
	}

	void testCasSupprimerUneValeur(int[] tab, int nb, int valeur, int result, boolean err) {
		System.out.print("testCasSupprimerUneValeur(" + Arrays.toString(tab) + ", " + nb + ", " + valeur + ") : ");
		int temp = supprimerUneValeur(tab, nb, valeur);
		System.out.println(temp);
		if (temp == result || (temp != result && err)) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}

	/**
	 * Renvoie vrai ssi le tableau tab1 est inclus dans tab2. Autrement dit, si tous les éléments de tab1 se retrouvent intégralement dans tab2 (y compris les doublons) mais pas nécessairement dans le même ordre. L'utilisation de méthodes déjà écrites est autorisé
	 * @param tab1 le premier tableau
	 * @param tab2 le deuxième tableau
	 * @param nb1 le nombre d'entiers présents dans le tableau1
	 * @param nb2 le nombre d'entiers présents dans le tableau2
	 * @return vrai ssi tableau1 est inclus dans tableau2
	 */
	boolean inclusion(int[] tab1, int[] tab2, int nb1, int nb2) {
		boolean inclus = true;
		boolean verif1 = verifTab(tab1, nb1);
		if (!verif1) {
			System.err.println("Problème avec le premier tableau");
		}
		boolean verif2 = verifTab(tab2, nb2);
		if (!verif2) {
			System.err.println("Problème avec le second tableau");
		}
		if (verif1 && verif2) {
			if (nb1 != nb2) {
				System.err.println("nb1 != nb2");
				inclus = false;
			} else {
				int i = 0;
				while (inclus && i < nb1) {
					int j = 0;
					boolean inclus2 = false;
					while (!inclus2 && j < nb2) {
						if (tab1[i] == tab2[j]) {
							inclus2 = true;
						}
						j++;
					}
					if (!inclus2) {
						inclus = false;
					}
					i++;
				}
			}
		} else {
			inclus = false;
		}
		return inclus;
	}

	void testInclusion() {
		System.out.println("");
		System.out.println("*** testInclusion()");
		// Cas normaux
		int[] tab1 = {2, 4, 6, 8};
		int[] tab2 = {6, 4, 8, 2};
		testCasInclusion(tab1, tab1, 4, 4, true, false);
		testCasInclusion(tab1, tab2, 4, 4, true, false);
		// Cas d'erreur
		int[] tab3 = {3, 2, 8};
		testCasInclusion(tab1, tab2, 2, 8, false, true);
		testCasInclusion(tab1, tab3, 4, 3, false, false);
	}

	void testCasInclusion(int[] tab1, int[] tab2, int nb1, int nb2, boolean result, boolean err) {
		System.out.print("testCasInclusion(" + Arrays.toString(tab1) + Arrays.toString(tab2) + ", " + nb1 + ", " + nb2 + ") : ");
		boolean temp = inclusion(tab1, tab2, nb1, nb2);
		System.out.println(temp);
		if (temp == result || (temp != result && err)) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}

	/**
	 * Une méthode utilitaire pour remplirToutesDiff. Vérifie si une valeur existe dans un tableau sauf à un certain indice (où on compare avec preval)
	 * @param tab le tableau à vérifier
	 * @param valeur la valeur à chercher
	 * @param ind l'indice exclus
	 * @param preval la valeur précedente de ind
	 * @return vrai ssi la valeur est dans tab
	 */
	boolean estPresent(int[] tab, int valeur, int ind, int preval) {
		boolean present = false;
		int i = 0;
		while (!present && i < tab.length) {
			if (i != ind) {
				if (tab[i] == valeur) {
					present = true;
				}
			} else {
				if (valeur == preval) {
					present = true;
				}
			}
			i++;
		}
		return present;
	}

	/**
	 * A partir d'un tableau déjà créé, remplir le tableau de nb valeurs saisies par l'utilisateur. Au fur et à mesure de la saisie, si la nouvelle valeur saisie existe déjà dans le tableau alors ne pas l'insérer et demander de ressaisir. Tenir compte des cas d'erreurs concernant tab et nb (appel de verifTab)
	 * @param tab le tableau à remplir d'éléments tous différents
	 * @param nb le nombre d'entiers que contiendra le tableau
	 */
	void remplirToutesDiff(int[] tab, int nb) {
		boolean verif = verifTab(tab, nb);
		if (!verif) {
			System.err.println("Impossible de remplir le tableau");
		} else {
			int i = 0;
			System.out.println("Le tableau est : " + Arrays.toString(tab));
			while (i < nb) {
				int preval = tab[i];
				tab[i] = SimpleInput.getInt("Entrez une valeur à insérer : ");
				System.out.println(Arrays.toString(tab));
				while (estPresent(tab, tab[i], i, preval)) {
					tab[i] = SimpleInput.getInt("Entrez une valeur à insérer (elle ne doit pas déjà être dans le tableau) : ");
					System.out.println(Arrays.toString(tab));
				}
				i++;
			}
		}
	}

	void testRemplirToutesDiff() {
		System.out.println("");
		System.out.println("*** testRemplirToutesDiff()");
		// Cas normaux
		int[] tab1 = {4, 5, 6};
		int[] tab2 = {4, 5, 6};
		System.out.println("Remplir avec 1, 2, 3");
		testCasRemplirToutesDiff(tab1, 3, false);
		System.out.println("Remplir avec 1, 1, 5, 8, 6, 9");
		testCasRemplirToutesDiff(tab2, 3, false);
		// Cas d'erreur
		testCasRemplirToutesDiff(tab1, 0, true);
	}

	void testCasRemplirToutesDiff(int[] tab, int nb, boolean err) {
		int[] original = copier(tab, nb);
		System.out.print("testCasRemplirToutesDiff(" + Arrays.toString(tab) + ", " + nb + ") : ");
		remplirToutesDiff(tab, nb);
		System.out.println(Arrays.toString(tab));
		if (!Arrays.equals(tab, original) || (Arrays.equals(tab, original) && err)) {
			System.out.println("\tOK");
		} else {
			System.err.println("\tERREUR");
		}
	}
}