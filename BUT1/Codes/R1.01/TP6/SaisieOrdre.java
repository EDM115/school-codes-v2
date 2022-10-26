/**
 * Saisie de 10 valeurs dans un tableau
 * @author EDM115
 **/

import java.util.Arrays;

class SaisieOrdre {
	final int LG_TAB = 10;

	void principal() {
		testSaisirEtTrier();
	}

	/**
	 * Crée et saisit un tableau trié de LG_TAB entiers
	 * @return tableau trié de LG_TAB entiers
	 **/
	int[] saisirEtTrier() {
		int[] t = new int[LG_TAB];
		int i = 0;
		int j = 0;
		int temp;
		int len = t.length;

		while (i < len) {
			t[i] = SimpleInput.getInt("Entrez un entier : ");
			while (j > 0 && t[j] < t[j - 1]) {
				temp = t[j - 1];
				t[j - 1] = t[j];
				t[j] = temp;
				j--;
			}
			System.out.println(Arrays.toString(t));
			i++;
			j = i;
		}
		return t;
	}

	/**
	 * Teste la méthode saisirEtTrier()
	 **/
	void testSaisirEtTrier() {
		System.out.println();
		System.out.println("*** testSaisirEtTrier()");
		int[] test1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] test2 = {2, 8, 3, -1, -9, 56, -127, 0, 1, 8};
		int[] test3 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
		int[] result2 = {-127, -9, -1, 0, 1, 2, 3, 8, 8, 56};
		int[] result3 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		testCasSaisirEtTrier(test1, test1);
		testCasSaisirEtTrier(test2, result2);
		testCasSaisirEtTrier(test3, result3);
	}

	/**
	 * teste un appel de saisirEtTrier
	 * @param tab le tableau non trié
	 * @param result le tableau attendu en sortie
	 **/
	void testCasSaisirEtTrier(int[] tab, int[] result) {
		// Arrange
		System.out.println("testCasSaisirEtTrier(" + Arrays.toString(tab) + ") \t= " + Arrays.toString(result) + "\t : ");
		// Act
		int i = 0;
		System.out.println("Valeurs à mettre : " + Arrays.toString(tab));
		int[] tabTri = new int[LG_TAB];
		tabTri = saisirEtTrier();
		boolean resExec = true;
		while (resExec && i < tabTri.length) {
			if (tabTri[i] != result[i]) {
				resExec = false;
			}
			i++;
		}
		// Assert
		if (resExec){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
}