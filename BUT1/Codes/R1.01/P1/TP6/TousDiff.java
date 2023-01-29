/**
 * Vérifie si toutes les valeurs sont différentes (l'inverse d'une intersection)
 * @author EDM115
 **/

import java.util.Arrays;

class TousDiff {
	void principal() {
		testSontTousDiff();
	}

	/**
	 * vérifie si deux tableaux n'ont aucune valeur commune
	 * @param tab1 premier tableau
	 * @param tab2 deuxième tableau
	 * @return vrai si les tableaux tab1 et tab2 n'ont aucune valeur commune, faux sinon
	 **/
	boolean sontTousDiff(int[] tab1, int[] tab2) {
		boolean tousDiff = true;
		int i = 0;
		int l1 = tab1.length;
		int l2 = tab2.length;
		
		while (i < l1 && tousDiff) {
			int j = 0;
			while (j < l2 && tousDiff) {
				if (tab1[i] == tab2[j]) {
					tousDiff = false;
				}
				j++;
			}
			i++;
		}
		return tousDiff;
	}

	/**
	 * Teste la méthode sontTousDiff()
	 **/
	void testSontTousDiff() {
		System.out.println();
		System.out.println("*** testSontTousDiff()");
		int[] test1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] test2 = {2, 8, 3, -1, -9, 56, -127, 0, 1, 8};
		int[] test3 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
		int[] test4 = {};
		int[] test5 = {10, 11, 15};
		testCasSontTousDiff(test1, test2, false);
		testCasSontTousDiff(test4, test1, true);
		testCasSontTousDiff(test2, test3, false);
		testCasSontTousDiff(test3, test5, true);
	}

	/**
	 * teste un appel de sontTousDiff
	 * @param tab1 le premier tableau
	 * @param tab2 le deuxième tableau
	 * @param diff booléen qui dit si ils sont différents
	 **/
	void testCasSontTousDiff(int[] tab1, int[] tab2, boolean diff) {
		// Arrange
		System.out.println();
		System.out.print("testCasSontTousDiff(" + Arrays.toString(tab1) + ", " + Arrays.toString(tab2) + ") \t= " + diff + "\t : ");
		// Act
		boolean resExec = sontTousDiff(tab1, tab2);
		// Assert
		if (resExec == diff){
			System.out.print("OK");
		} else {
			System.err.print("ERREUR");
		}
	}
}