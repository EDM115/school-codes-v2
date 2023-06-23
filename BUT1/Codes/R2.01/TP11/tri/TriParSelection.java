package tri;

import pays.*;

/**
 * Classe TriParSelection, implémentant l'interface ITri
 */
public class TriParSelection implements ITri<Pays> {

	/**
	 * Le tableau de pays à trier
	 */
	private Pays[] tab;

	/**
	 * Echange les éléments d'indice i et j du tableau tab
	 * @param i l'indice de l'élément à échanger
	 * @param j l'indice de l'élément à échanger
	 */
	private void swap(int i, int j) {
		if (i < 0 || i >= this.tab.length || j < 0 || j >= this.tab.length) {
			System.err.println("\u001B[31mswap() : Invalid arguments\u001B[0m");
		} else {
			Pays tmp = this.tab[i];
			this.tab[i] = this.tab[j];
			this.tab[j] = tmp;
		}
	}

	/**
	 * Retourne l'indice de l'élément minimum du tableau tab à partir de l'indice debut
	 * @param debut l'indice de départ
	 * @return l'indice de l'élément minimum du tableau tab à partir de l'indice debut
	 */
	private int minimumPosition(int debut) {
		if (debut < 0 || debut >= this.tab.length) {
			System.err.println("\u001B[31mminimumPosition() : Invalid arguments\u001B[0m");
			return -1;
		} else {
			int minPos = debut;
			for (int i = debut + 1; i < this.tab.length; i++) {
				if (this.tab[i].compareTo(this.tab[minPos]) < 0) {
					minPos = i;
				}
			}
			return minPos;
		}
	}

	/**
	 * Trie le tableau tab
	 * @param tab le tableau à trier
	 */
	@Override
	public void trier(Pays[] tab) {
		this.tab = tab;
		for (int i = 0; i < this.tab.length - 1; i++) {
			this.swap(i, this.minimumPosition(i));
		}
	}

	/**
	 * Constructeur de la classe TriParSelection
	 * @param tab le tableau à trier
	 */
	public TriParSelection(Pays[] tab) {
		if (tab == null) {
			System.err.println("\u001B[31mTriParSelection() : Invalid arguments\u001B[0m");
		} else {
			this.trier(tab);
		}
	}

	/**
	 * Retourne le tableau
	 * @return le tableau
	 */
	public Pays[] getTab() {
		return this.tab;
	}
}