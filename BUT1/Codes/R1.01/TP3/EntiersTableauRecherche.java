/**
* Cherche si un nombre est présent au moins 2 fois dans un tableau d'entiers
* @author EDM115
*/
class EntiersTableauRecherche {
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
		
		boolean nullTab = false;
		if (nbVal == 0) {
			nullTab = true;
		}
		
		if (!nullTab) {
			boolean inTab = false;
			int i = 0;
			int tabVal = 0;
			String indice = "";
			int present = 0;
			int val = SimpleInput.getInt("Saisissez un entier pour savoir si il est dans le tableau : ");
			while (i < nbVal) {
				tabVal = tab[i];
				if (tabVal == val) {
					inTab = true;
					if (indice != "") {
						indice = indice + ", ";
					}
					indice = indice + i;
					present++;
				}
				i++;
			}
			if (present >= 2) {
				System.out.println(val + " est présent dans le tableau plus de 2 fois");
			}
			if (inTab) {
				System.out.println(val + " est dans le tableau en indice " + indice);
			} else {
				System.out.println(val + " n'est pas dans le tableau");
			}
		}
	}
}
