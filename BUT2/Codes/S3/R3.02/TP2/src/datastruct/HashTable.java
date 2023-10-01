package datastruct;

/**
 * Builds the class HashTable.java (package datastruct) under Eclipse which implements a very simplified hash table (in particular, we will consider that the table has a capacity of TAILLE tuples maximum, TAILLE being a constant). We will first create an interface Table.java and then HashTable.java which will implement this interface.
 */
public class HashTable implements Table {
	/**
	 * The table that will contain all the tuples
	 */
	Tuple[] table;

	/**
	 * The number of tuples that the table contains (necessarily &gt;= TAILLE)
	 */
	int nbTuples;

	/**
	 * Constant that defines the size of the table
	 */
	int TAILLE = 10;

	/**
	 * This method returns the data of the Tuple corresponding to the search key key passed as a parameter. Returns null if no tuple matches the key.
	 * @param key The identification key of the Tuple
	 * @return The data of the Tuple corresponding to the search key key passed as a parameter
	 * @throws IllegalArgumentException If the key is null
	 */
	@Override
	public Object select(String key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("select() : key is null");
		}
		int indice = this.computeIndex(key);
		if (this.table[indice] != null && this.table[indice].sameKey(key)) {
			return this.table[indice].data;
		}
		indice = this.circularSearch(key, indice);
		if (indice != -1) {
			return this.table[indice].data;
		}
		return null;
	}

	/**
	 * If no Tuple in the table matches the key passed as a parameter, this method inserts a new Tuple whose key (key) and data (data) are passed as parameters. Returns false if the insertion is not possible (either the key already exists, or the table is full).
	 * Important note: in case of collision (i.e. when the index "ind" calculated with computeIndex (...), see below, corresponds to an already occupied box), we advance circularly box by box on the table starting with the box "ind + 1" and stopping as soon as a box has a content "null". The index of the first box encountered with a content "null" is the location of the new Tuple inserted in the table.
	 * @param key The identification key of the Tuple
	 * @param data The data of the Tuple
	 * @return True if the insertion has been performed (false otherwise)
	 * @throws IllegalArgumentException If the key or the data is null
	 */
	@Override
	public boolean insert(String key, Object data) throws IllegalArgumentException {
		if (key == null || data == null) {
			throw new IllegalArgumentException("insert() : key or data is null");
		}
		if (this.nbTuples == this.TAILLE) {
			return false;
		}
		int indice = this.computeIndex(key);
		if (this.table[indice] == null) {
			this.table[indice] = new Tuple(key, data);
			this.nbTuples++;
			return true;
		}
		indice = this.circularSearch(key, indice);
		if (indice != -1) {
			this.table[indice] = new Tuple(key, data);
			this.nbTuples++;
			return true;
		}
		return false;
	}

	/**
	 * Removes from the table the Tuple corresponding to the key passed as a parameter. Returns false if the deletion is not possible (i.e. no Tuple matches this key in the table).
	 * @param key The identification key of the Tuple
	 * @return True if the deletion has been performed (false otherwise)
	 * @throws IllegalArgumentException If the key is null
	 */
	@Override
	public boolean delete(String key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("delete() : key is null");
		}
		int indice = this.computeIndex(key);
		if (this.table[indice] != null && this.table[indice].sameKey(key)) {
			this.table[indice] = null;
			this.nbTuples--;
			return true;
		}
		indice = this.circularSearch(key, indice);
		if (indice != -1) {
			this.table[indice] = null;
			this.nbTuples--;
			return true;
		}
		return false;
	}

	/**
	 * The constructor that must initialize the attributes
	 */
	public HashTable() {
		this.table = new Tuple[TAILLE];
		this.nbTuples = 0;
	}

	/**
	 * Returns the number of tuples that the table contains
	 * @return The number of tuples that the table contains
	 */
	public int getNbTuples() {
		return this.nbTuples;
	}

	/**
	 * Computes, based on the ASCII codes of the characters that make up the key, the index of the table where the Tuple will be stored (0 &gt;= index &gt; TAILLE). Method to use in the code of the insert and select methods.
	 * @param key The identification key of the Tuple
	 * @return The index of the table where the Tuple will be stored (0 &gt;= index &gt; TAILLE)
	 * @throws IllegalArgumentException If the key is null
	 */
	private int computeIndex(String key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("computeIndex() : key is null");
		}
		int indice = 0;
		for (int i = 0; i < key.length(); i++) {
			indice += key.charAt(i);
		}
		return indice % this.TAILLE;
	}

	/**
	 * Returns the index of the table where the searched tuple is located (or -1 if it is not there). The search on the table is circular and starts at (index + 1). This method is called by select or delete when the tuple searched is not at the index calculated by computeIndex but at another location found by circular traversal of the table (see "Important note" above).
	 * @param key The identification key of the Tuple
	 * @param indice The index of the table where the searched tuple is located (or -1 if it is not there)
	 * @return The index of the table where the searched tuple is located (or -1 if it is not there)
	 * @throws IllegalArgumentException If the key is null or if the index is negative
	 */
	private int circularSearch(String key, int indice) throws IllegalArgumentException {
		if (key == null || indice < 0) {
			throw new IllegalArgumentException("circularSearch() : key is null or indice < 0");
		}
		int i = indice + 1;
		while (i != indice) {
			if (i == this.TAILLE) {
				i = 0;
			}
			if (this.table[i] != null && this.table[i].sameKey(key)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	/**
	 * Returns the entirety of the table as a string using a StringBuilder.
	 * @return The entirety of the table as a string
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.TAILLE; i++) {
			if (this.table[i] != null) {
				sb.append("[" + i + "] " + this.table[i].toString() + "\n");
			} else {
				sb.append("[" + i + "] null\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Inner class that represents a Tuple (key + data)
	 */
	private class Tuple {
		/**
		 * The identification key of the Tuple
		 */
		String key;

		/**
		 * The data of the Tuple
		 */
		Object data;

		/**
		 * Constructor of a Tuple, initialization of attributes
		 * @param key The identification key of the Tuple
		 * @param data The data of the Tuple
		 * @throws IllegalArgumentException If the key or the data is null
		 */
		Tuple(String key, Object data) throws IllegalArgumentException {
			if (key == null || data == null) {
				throw new IllegalArgumentException("Tuple() : key or data is null");
			}
			this.key = key;
			this.data = data;
		}
		
		/**
		 * Method that compares the key of the Tuple with another key (otherKey). Method used to search the Tuple in the table (the table). Returns true if the two keys are identical (false otherwise).
		 * @param otherKey The key to compare with the key of the Tuple
		 * @return True if the two keys are identical (false otherwise)
		 */
		boolean sameKey(String otherKey) {
			if (otherKey == null) {
				return false;
			}
			return this.key.equals(otherKey);
		}
		
		/**
		 * Returns the Tuple as a string that will contain its key and data
		 * @return The Tuple as a string that will contain its key and data
		 */
		public String toString() {
			return "Tuple [key=" + key + ", data=" + data + "]";
		}
	}
}
