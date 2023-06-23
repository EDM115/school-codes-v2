package tri;

/**
 * Classe TriParSelectionG, trie un tableau d'éléments génériques par sélection
 * @param <T> le type des éléments du tableau
 */
public class TriParSelectionG<T extends Comparable<T>> implements ITri<T> {
	
	/**
	 * Le tableau à trier
	 */
	private T[] tab;

	/**
	 * Echange les éléments d'indice i et j du tableau tab
	 *
	 * @param i l'indice de l'élément à échanger
	 * @param j l'indice de l'élément à échanger
	 */
	private void swap(int i, int j) {
		if (i < 0 || i >= this.tab.length || j < 0 || j >= this.tab.length) {
			System.err.println("\u001B[31mswap() : Invalid arguments\u001B[0m");
		} else {
			T tmp = this.tab[i];
			this.tab[i] = this.tab[j];
			this.tab[j] = tmp;
		}
	}

	/**
	 * Retourne l'indice de l'élément minimum du tableau tab à partir de l'indice debut
	 *
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
	 *
	 * @param tab le tableau à trier
	 */
	@Override
	public void trier(T[] tab) {
		this.tab = tab;
		for (int i = 0; i < this.tab.length - 1; i++) {
			this.swap(i, this.minimumPosition(i));
		}
	}

	/**
	 * Constructeur TriParSelectionG
	 * @param tab le tableau à trier
	 */
	public TriParSelectionG(T[] tab) {
		if (tab == null) {
			System.err.println("\u001B[31mTriParSelectionG() : Invalid arguments\u001B[0m");
		} else {
			this.trier(tab);
		}
	}

	/**
	 * Retourne le tableau
	 *
	 * @return le tableau
	 */
	public T[] getTab() {
		return this.tab;
	}
}
