package datastruct;

/**
 * Java implementation of a doubly linked list with a sentinel. The sentinel is a special element that is always present in the list. It is used to simplify the implementation of the list. It is not counted in the size of the list. The list is empty when it contains only the sentinel. The sentinel is always the first element of the list. The current element is the element on which the cursor is positioned. The cursor is always positioned between two elements.
 */
public class LinkedList implements List {
	/**
	 * Points to the sentinel element
	 */
	private Element sentinel;

	/**
	 * Points to the current element
	 */
	private Element current;

	/**
	 * Number of elements in the list
	 */
	private int size;
	
	/**
	 * Constructor of an empty list (a sentinel as the only element)
	 */
	public LinkedList() {
		this.sentinel = new Element(null, null, null);
		this.sentinel.prev = this.sentinel;
		this.sentinel.next = this.sentinel;
		this.current = sentinel;
		this.size = 0;

		assert this.sentinel.prev == this.sentinel;
		assert this.sentinel.next == this.sentinel;
		assert invariant();
	}

	/**
	 * Insertion of a new element in the list. It must precede the current element (on its left). The new element becomes the current element. Also increment the number of elements in the list.
	 * @param data The data to insert
	 */
	@Override
	public void insert(Object data) {
		if (data == null) {
			System.err.println("insert() : parameter is null");
			return;
		}
		Element newElement = new Element(this.current.prev, this.current, data);
		this.current.prev.next = newElement;
		this.current.prev = newElement;
		this.current = newElement;
		this.size++;

		assert this.current.theValue.equals(data);
		assert invariant();
	}

	/**
	 * Delete the current element. Reposition the current element on its previous (on its left). Special case: if the current element is the first element of the list (the previous is the sentinel), reposition the current element on its next (on its right). Also decrement the number of elements in the list. Return false if the list is empty.
	 * @return true if the element has been deleted, false otherwise
	 */
	@Override
	public boolean delete() {
		if (isEmpty()) {
			return false;
		}
		if (this.current.prev == this.sentinel) {
			this.current.next.prev = this.sentinel;
			this.sentinel.next = this.current.next;
			this.current = this.sentinel.next;
		} else {
			this.current.prev.next = this.current.next;
			this.current.next.prev = this.current.prev;
			this.current = this.current.prev;
		}
		this.size--;

		assert invariant();

		return true;
	}

	/**
	 * Return true if an element of the list contains the value data, false otherwise.
	 * @param data The data to search
	 * @return true if the list contains the data, false otherwise
	 */
	@Override
	public boolean contains(Object data) {
		if (data == null) {
			System.err.println("contains() : parameter is null");
			return false;
		}
		Element tmp = this.sentinel.next;
		while (tmp != this.sentinel) {
			if (tmp.theValue.equals(data)) {
				return true;
			}
			tmp = tmp.next;
		}

		assert invariant();

		return false;
	}

	/**
	 * Insertion of a new element in the list at position index (&lt;= 0 and &gt;= size). The new element becomes the current element. Also increment the number of elements in the list.
	 * @param index The index where to insert the data
	 * @param data The data to insert
	 */
	@Override
	public void add(int index, Object data) {
		if (data == null) {
			System.err.println("add() : parameter is null");
			return;
		}
		if (index < 0 || index > this.size) {
			System.err.println("add() : index out of bounds");
			return;
		}
		Element tmp = this.sentinel.next;
		for (int i = 0; i < index; i++) {
			tmp = tmp.next;
		}
		Element newElement = new Element(tmp.prev, tmp, data);
		tmp.prev.next = newElement;
		tmp.prev = newElement;
		this.current = newElement;
		this.size++;

		assert this.current.theValue.equals(data);
		assert invariant();
	}

	/**
	 * Return the value contained in the current element.
	 * @return the value contained in the current element
	 */
	@Override
	public Object getValue() {
		if (isEmpty()) {
			return null;
		}

		return this.current.theValue;
	}

	/**
	 * Edit the content of the current element with the new data passed as a parameter.
	 * @param newData The new data to set
	 */
	@Override
	public void setValue(Object newData) {
		if (isEmpty()) {
			return;
		}
		if (newData == null) {
			System.err.println("setValue() : parameter is null");
			return;
		}
		this.current.theValue = newData;

		assert this.current.theValue.equals(newData);
		assert invariant();
	}

	/**
	 * Return true if the list is empty, false otherwise.
	 * @return true if the list is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Return the number of elements in the list.
	 * @return the number of elements in the list
	 */
	@Override
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Place the cursor on the head of the list.
	 */
	public void goToHead() {
		this.current = this.sentinel.next;

		assert this.current.prev == this.sentinel;
		assert invariant();
	}
	
	/**
	 * Place the cursor at the end of the list.
	 */
	public void goToEnd() {
		this.current = this.sentinel.prev;

		assert this.current.next == this.sentinel;
		assert invariant();
	}

	/**
	 * Place the cursor on its next if it exists.
	 * @return true if the cursor has been moved, false otherwise
	 */
	public boolean next() {
		if (this.current.next == this.sentinel) {
			return false;
		}
		this.current = this.current.next;

		assert this.current.prev != this.sentinel;
		assert invariant();

		return true;
	}
	
	/**
	 * Place the cursor on its previous if it exists.
	 * @return true if the cursor has been moved, false otherwise
	 */
	public boolean previous() {
		if (this.current.prev == this.sentinel) {
			return false;
		}
		this.current = this.current.prev;

		assert this.current.next != this.sentinel;
		assert invariant();

		return true;
	}
	
	/**
	 * Return the content of the list as a String (i.e. all the data)
	 * @return the content of the list as a String
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		Element tmp = this.sentinel.next;
		while (tmp != this.sentinel) {
			result.append(tmp.theValue).append(" ");
			tmp = tmp.next;
		}

		assert result != null;
		assert invariant();

		return result.toString();
	}

	/**
	 * Return the data at position index in the list
	 * @param index The index of the data to get
	 * @return the data at position index in the list
	 */
	public Object getValueAt(int index) {
		if (index < 0 || index >= this.size) {
			System.err.println("getValueAt() : index out of bounds");
			return null;
		}
		Element tmp = this.sentinel.next;
		for (int i = 0; i < index; i++) {
			tmp = tmp.next;
		}

		assert tmp.theValue != null;
		assert invariant();

		return tmp.theValue;
	}

	/**
	 * Invariant : condition to check at any time to ensure that the object remains in a consistent state "throughout its life". Consists, in practice in Java, to test with a private method private boolean invariant() the validity of each of the attributes of the object. The call to the invariant() method must be done at the creation of the object and at the end of execution of almost each of the methods.
	 * @return true if the invariant is respected, false otherwise
	 */
	private boolean invariant() {
		return this.sentinel != null && this.sentinel.prev != null && this.sentinel.next != null && this.size >= 0;
	}
	
	/**
	 * Inner class representing an element of the list
	 */
	private class Element {
		/**
		 * Connexion to the previous element of the list
		 */
		Element prev;

		/**
		 * Connexion to the next element of the list
		 */
		Element next;

		/**
		 * Data stored
		 */
		Object theValue;
		
		/**
		 * Constructor of an element
		 * @param prev The previous element
		 * @param next The next element
		 * @param data The data to store
		 */
		Element(Element prev, Element next, Object data) {
			this.prev = prev;
			this.next = next;
			this.theValue = data;
		}
	}
}
