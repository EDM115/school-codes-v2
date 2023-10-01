package datastruct;

/**
 * Interface of a list
 */
public interface List {
	/**
	 * Insertion of a new element in the list. It must precede the current element (on its left). The new element becomes the current element. Also increment the number of elements in the list.
	 * @param data The data to insert
	 */
	public void insert(Object data);
	
	/**
	 * Delete the current element. Reposition the current element on its previous (on its left). Special case: if the current element is the first element of the list (the previous is the sentinel), reposition the current element on its next (on its right). Also decrement the number of elements in the list. Return false if the list is empty.
	 * @return true if the element has been deleted, false otherwise
	 */
	public boolean delete();
	
	/**
	 * Return true if an element of the list contains the value data, false otherwise.
	 * @param data The data to search
	 * @return true if the list contains the data, false otherwise
	 */
	public boolean contains(Object data);
	
	/**
	 * Insertion of a new element in the list at position index (&lt;= 0 and &gt;= size). The new element becomes the current element. Also increment the number of elements in the list.
	 * @param index The index where to insert the data
	 * @param data The data to insert
	 */
	public void add(int index, Object data);
	
	/**
	 * Return the value contained in the current element.
	 * @return the value contained in the current element
	 */
	public Object getValue();
	
	/**
	 * Edit the content of the current element with the new data passed as a parameter.
	 * @param newData The new data to set
	 */
	public void setValue(Object newData);
	
	/**
	 * Return true if the list is empty, false otherwise.
	 * @return true if the list is empty, false otherwise
	 */
	public boolean isEmpty();
	
	/**
	 * Return the number of elements in the list.
	 * @return the number of elements in the list
	 */
	public int getSize();
}
