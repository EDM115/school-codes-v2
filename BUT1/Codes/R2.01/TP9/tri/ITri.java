package tri;

import pays.*;

/**
 * Interface ITri
 * @param <Pays> le type de données à trier
 */
public interface ITri<Pays> {
	/**
	 * Trie le tableau tab
	 * @param tab le tableau à trier
	 */
	public void trier(Pays[] tab);
}