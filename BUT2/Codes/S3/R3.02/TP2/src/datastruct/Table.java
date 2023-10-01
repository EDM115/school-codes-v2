package datastruct;

/**
 * Interface Table, which defines the methods that a table must implement
 */
public interface Table {
	/**
	 * This method returns the data of the Tuple corresponding to the search key key passed as a parameter. Returns null if no tuple matches the key.
	 * @param key The identification key of the Tuple
	 * @return The data of the Tuple corresponding to the search key key passed as a parameter
	 */
	public Object select(String key);
	
	/**
	 * If no Tuple in the table matches the key passed as a parameter, this method inserts a new Tuple whose key (key) and data (data) are passed as parameters. Returns false if the insertion is not possible (either the key already exists, or the table is full).
	 * Important note: in case of collision (i.e. when the index "ind" calculated with computeIndex (...), see below, corresponds to an already occupied box), we advance circularly box by box on the table starting with the box "ind + 1" and stopping as soon as a box has a content "null". The index of the first box encountered with a content "null" is the location of the new Tuple inserted in the table.
	 * @param key The identification key of the Tuple
	 * @param data The data of the Tuple
	 * @return True if the insertion has been performed (false otherwise)
	 */
	public boolean insert(String key, Object data);
	
	/**
	 * Removes from the table the Tuple corresponding to the key passed as a parameter. Returns false if the deletion is not possible (i.e. no Tuple matches this key in the table).
	 * @param key The identification key of the Tuple
	 * @return True if the deletion has been performed (false otherwise)
	 */
	public boolean delete(String key);
}
