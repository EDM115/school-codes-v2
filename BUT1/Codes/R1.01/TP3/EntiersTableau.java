/**
* L'utilisateur saisit maximum 10 entiers dans un tableau, interrompu par -1, l'affiche et recherche un element
* @author EDM115
*/
class EntiersTableau {
	void principal() {
		int nbVal = 0;
		int[] tab = new int[10];
		int nb = SimpleInput.getInt("Saisissez un entier : ");
		
		while (nb != -1 && nbVal < tab.length) {
			tab[nbVal] = nb;
			if (nbVal != tab.length - 1) {
				nb = SimpleInput.getInt("Saisissez un entier : ");
			}
			nbVal++;
		}
		System.out.println("Le tableau contient " + nbVal + " valeurs");
		
		boolean nullTab = false;
		if (nbVal == 0) {
			nullTab = true;
		}
		int i = 0;
		int value;
		String tableau = "[";
		if (!nullTab) {
			while (i < nbVal) {
				value = tab[i];
				tableau = tableau + value;
				if (i != nbVal - 1) {
					tableau = tableau + ",";
				}
				i++;
			}
		}
		tableau = tableau + "]";
		System.out.println(tableau);
		
		if (!nullTab) {
			boolean inTab = false;
			int j = 0;
			int tabVal = 0;
			int val = SimpleInput.getInt("Saisissez un entier pour savoir si il est dans le tableau : ");
			while (j < nbVal && !inTab) {
				tabVal = tab[j];
				if (tabVal == val) {
					inTab = true;
					j--;
				}
				j++;
			}
			if (inTab) {
				System.out.println(val + " est dans le tableau en indice " + j);
			} else {
				System.out.println(val + " n'est pas dans le tableau");
			}
		}
	}
}
