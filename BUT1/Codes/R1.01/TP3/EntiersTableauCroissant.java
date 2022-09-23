/**
* L'utilisateur saisit maximum 10 entiers dans un tableau, et vérifie si le tableau est croissant
* @author EDM115
*/
class EntiersTableauCroissant {
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
		System.out.println("Le tableau contient " + nbVal + " valeurs\n");
		
		boolean nullTab = false;
		if (nbVal == 0) {
			nullTab = true;
		}
		
		if (!nullTab) {
			boolean croissant = true;
			if (nbVal != 1) {
				int i = 0;
				int value;
				while (i < nbVal - 1 && croissant) {
					value = tab[i];
					if (tab[i+1] < value) {
						croissant = false;
					}
					i++;
				}
				if (tab[i] < tab[i - 1]) {
					croissant = false;
				}
			}
			if (croissant) {
				System.out.println("Le tableau est croissant");
			} else {
				System.out.println("Le tableau n'est pas croissant");
			}
		} else {
			System.out.println("Tableau vide. Je peux pas dire si il est croissant");
		}
	}
}

