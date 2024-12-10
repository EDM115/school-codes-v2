package datastruct;

/**
 * Interface for a table of key/data pairs
 * @param <E> the type of the keys
 * @param <T> the type of the data
 */
public interface Table <E extends Comparable<E>, T> {
	/**
	 * This method returns the data contained in the node corresponding to the search key key passed as a parameter. Returns null if the key does not exist
	 * @param key the key to search for
	 * @return the data associated with the key, or null if the key does not exist
	 */
	public T select(E key);

	/**
	 * If the key does not already exist in the table, this method inserts a new node with the key and data passed as parameters. Returns false if the insertion was not possible.
	 * @param key the key to insert
	 * @param data the data to insert
	 * @return true if the insertion was successful, false otherwise
	 */
	public boolean insert(E key, T data);

	/**
	 * Destroys the node corresponding to the key passed as a parameter. Returns false if the deletion was not possible (i.e. the key does not exist).
	 * @param key the key to delete
	 * @return true if the deletion was successful, false otherwise
	 */
	public boolean delete(E key);
}
