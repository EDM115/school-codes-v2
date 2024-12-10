import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

/**
 * A bag is a collection that allows duplicates. It is implemented as a linked list.
 * @param <E> The type of the elements in the bag
 */
class Bag<E> extends AbstractCollection<E> {
    /**
	 * Private class representing an element of the list
	 */
    private class Element {
		/**
		 * Connection to the next element in the list
		 */
        Element next;
		/**
		 * Stored data
		 */
        E data;

        /**
		 * Constructor for an element of the list
		 * @param data The data to store
		 * @param next The next element in the list
		 */
        Element(E data, Element next) {
            this.data = data;
            this.next = next;
        }
    }
    
    /**
	 * Private class representing an iterator on the list
	 */
    private class Itr implements Iterator<E> {
		/**
		 * Current element
		 */
        Element current;

		/**
		 * Previous element
		 */
        Element pastCurrent;

		/**
		 * Expected modification count
		 */
        int expectedModCount = modCount;
        
		/**
		 * Constructor for an iterator on the list
		 */
        Itr() {
            current = pastCurrent = sentinel;
        }
        
		/**
		 * Checks if there is a next element
		 * @return True if there is a next element, false otherwise
		 */
        public boolean hasNext() {
            checkForModification();
            return current.next != sentinel;
        }

		/**
		 * Returns the next element
		 * @return The next element
		 */
        public E next() {
            checkForModification();
            pastCurrent = current;
            current = current.next;
            return current.data;
        }

		/**
		 * Removes the current element
		 */
        @Override
        public void remove() {
            if (pastCurrent == current) {
                throw new IllegalStateException("Cannot call remove() twice consecutively");
            }
            checkForModification();
            
            // Point the previous element to the next of current
            pastCurrent.next = current.next;
            current = pastCurrent;
            size--;
            modCount++;
            expectedModCount++;
        }
        
		/**
		 * Checks if the list has been modified
		 * @throws ConcurrentModificationException If the list has been modified
		 */
        final void checkForModification() throws ConcurrentModificationException {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

	/**
	 * The sentinel element
	 */
    private Element sentinel;

	/**
	 * The size of the list
	 */
    private int size;

	/**
	 * The modification count
	 */
    private int modCount = 0;

    /**
	 * Checks if the invariant is respected
	 * @return True if the invariant is respected, false otherwise
	 */
    private boolean invariant() {
        return size >= 0 && (size == 0 ? sentinel.next == sentinel : true);
    }

	/**
	 * Constructor for a bag
	 */
    public Bag() {
        this.sentinel = new Element(null, null);
        this.sentinel.next = this.sentinel;
        this.size = 0;
        assert invariant(); 
    }
    
	/**
	 * Constructor for a bag from a collection
	 * @param c The collection to use
	 * @throws IllegalArgumentException If the collection is null
	 */
    public Bag(Collection<E> c) throws IllegalArgumentException {
        this();
        if (c == null) {
			throw new IllegalArgumentException("Bag() : Collection cannot be null");
		}
        for (E item : c) {
            add(item);
        }
        assert invariant();
    }
    
	/**
	 * Returns an iterator on the list
	 * @return An iterator on the list
	 */
    public Iterator<E> iterator() {
        assert invariant();
        return new Itr();
    }

	/**
	 * Returns the size of the list
	 * @return The size of the list
	 */
    public int size() {
        assert invariant();
        return size;
    }

	/**
	 * Adds an element to the list
	 * @param o The element to add
	 * @throws IllegalArgumentException If the element is null
	 * @return True if the element has been added, false otherwise
	 */
    @Override
    public boolean add(E o) throws IllegalArgumentException {
        if (o == null) {
			throw new IllegalArgumentException("Bag.add() : Element cannot be null");
		}
        assert invariant();
        if (size == Integer.MAX_VALUE) {
            return false;
        }

        int index = new Random().nextInt(size + 1);
        Element prev = sentinel;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        
        Element newElem = new Element(o, prev.next);
        prev.next = newElem;
        size++;
        modCount++;
        
        assert invariant();
        return true;
    }
}
