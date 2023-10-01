import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Bag
 */
public class BagTest {
	/**
	 * Bag used for tests
	 */
	Bag<String> bag;

	/**
	 * No setup needed here as there is different constructors
	 */
	@Before
	public void setUp() {
	}

	/**
	 * Destroys the bag after each test
	 */
	@After
	public void tearDown() {
		this.bag = null;
	}

	/**
	 * Test method for Bag()
	 */
	@Test
	public final void testBag() {
		System.out.println("\u001B[33mTest du constructeur Bag()\u001B[0m");
		this.bag = new Bag<String>();
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for Bag(Collection&gt;E&lt; c)
	 */
	@Test
	public final void testBagCollectionOfE() {
		System.out.println("\u001B[33mTest du constructeur Bag(Collection<E>) - Cas normal\u001B[0m");
		ArrayList<String> collection = new ArrayList<String>();
		collection.add("test");
		collection.add("test2");
		collection.add("test3");
		this.bag = new Bag<String>(collection);
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 3, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for Bag(Collection&gt;E&lt; c) in an error case
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testBagCollectionOfEWithNull() {
		System.out.println("\u001B[33mTest du constructeur Bag(Collection<E>) - Cas d'erreur : collection nulle\u001B[0m");
		this.bag = new Bag<String>(null);
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for add() with a normal case
	 */
	@Test
	public final void testAdd() {
		System.out.println("\u001B[33mTest de la méthode add() - Cas normal\u001B[0m");
		this.bag = new Bag<String>();
		this.bag.add("test");
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 1, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for add() with an error case
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testAddWithNull() {
		System.out.println("\u001B[33mTest de la méthode add() - Cas d'erreur : élément null\u001B[0m");
		this.bag = new Bag<String>();
		this.bag.add(null);
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for iterator()
	 */
	@Test
	public final void testIterator() {
		System.out.println("\u001B[33mTest de la méthode iterator()\u001B[0m");
		this.bag = new Bag<String>();
		Object it = this.bag.iterator();
		assertNotNull("\u001B[31mECHEC du test : itérateur null\u001B[0m", it);
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for size() with a normal case
	 */
	@Test
	public final void testSize() {
		System.out.println("\u001B[33mTest de la méthode size() - Cas normal\u001B[0m");
		this.bag = new Bag<String>();
		this.bag.add("test");
		this.bag.add("test2");
		this.bag.add("test3");
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 3, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for size() with an empty bag
	 */
	@Test
	public final void testSizeEmpty() {
		System.out.println("\u001B[33mTest de la méthode size() - Cas limte : sac vide\u001B[0m");
		this.bag = new Bag<String>();
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for toString()
	 */
	@Test
	public final void testToString() {
		System.out.println("\u001B[33mTest de la méthode toString()\u001B[0m");
		this.bag = new Bag<String>();
		this.bag.add("test");
		this.bag.add("test2");
		this.bag.add("test3");
		// assert : as the insertion of the element is random, checks instead if the String contains the 3 inserted elements
		assertTrue("\u001B[31mECHEC du test : toString incorrecte\u001B[0m", this.bag.toString().contains("test") && this.bag.toString().contains("test2") && this.bag.toString().contains("test3"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for clear()
	 */
	@Test
	public final void testClear() {
		System.out.println("\u001B[33mTest de la méthode clear()\u001B[0m");
		this.bag = new Bag<String>();
		this.bag.add("test");
		this.bag.add("test2");
		this.bag.add("test3");
		this.bag.clear();
		assertEquals("\u001B[31mECHEC du test : clear incorrecte\u001B[0m", 0, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for addAll()
	 */
	@Test
	public final void testAddAll() {
		System.out.println("\u001B[33mTest de la méthode addAll()\u001B[0m");
		this.bag = new Bag<String>();
		ArrayList<String> collection = new ArrayList<String>();
		collection.add("test");
		collection.add("test2");
		collection.add("test3");
		this.bag.addAll(collection);
		assertEquals("\u001B[31mECHEC du test : addAll incorrecte\u001B[0m", 3, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for contains()
	 */
	@Test
	public final void testContains() {
		System.out.println("\u001B[33mTest de la méthode contains()\u001B[0m");
		this.bag = new Bag<String>();
		this.bag.add("test");
		this.bag.add("test2");
		this.bag.add("test3");
		assertTrue("\u001B[31mECHEC du test : contains incorrecte\u001B[0m", this.bag.contains("test"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for containsAll()
	 */
	@Test
	public final void testContainsAll() {
		System.out.println("\u001B[33mTest de la méthode containsAll()\u001B[0m");
		this.bag = new Bag<String>();
		ArrayList<String> collection = new ArrayList<String>();
		collection.add("test");
		collection.add("test2");
		collection.add("test3");
		this.bag.addAll(collection);
		assertTrue("\u001B[31mECHEC du test : containsAll incorrecte\u001B[0m", this.bag.containsAll(collection));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for isEmpty()
	 */
	@Test
	public final void testIsEmpty() {
		System.out.println("\u001B[33mTest de la méthode isEmpty()\u001B[0m");
		this.bag = new Bag<String>();
		assertTrue("\u001B[31mECHEC du test : isEmpty incorrecte\u001B[0m", this.bag.isEmpty());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for remove()
	 */
	@Test
	public final void testRemove() {
		System.out.println("\u001B[33mTest de la méthode remove()\u001B[0m");
		this.bag = new Bag<String>();
		this.bag.add("test");
		this.bag.add("test2");
		this.bag.add("test3");
		this.bag.remove("test");
		assertEquals("\u001B[31mECHEC du test : remove incorrecte\u001B[0m", 2, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for removeAll()
	 */
	@Test
	public final void testRemoveAll() {
		System.out.println("\u001B[33mTest de la méthode removeAll()\u001B[0m");
		this.bag = new Bag<String>();
		ArrayList<String> collection = new ArrayList<String>();
		collection.add("test");
		collection.add("test2");
		collection.add("test3");
		this.bag.addAll(collection);
		this.bag.removeAll(collection);
		assertEquals("\u001B[31mECHEC du test : removeAll incorrecte\u001B[0m", 0, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for retainAll()
	 */
	@Test
	public final void testRetainAll() {
		System.out.println("\u001B[33mTest de la méthode retainAll()\u001B[0m");
		this.bag = new Bag<String>();
		ArrayList<String> collection = new ArrayList<String>();
		collection.add("test");
		collection.add("test2");
		collection.add("test3");
		this.bag.addAll(collection);
		ArrayList<String> collection2 = new ArrayList<String>();
		collection2.add("test");
		collection2.add("test2");
		this.bag.retainAll(collection2);
		assertEquals("\u001B[31mECHEC du test : retainAll incorrecte\u001B[0m", 2, this.bag.size());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Test method for toArray()
	 */
	@Test
	public final void testToArray() {
		System.out.println("\u001B[33mTest de la méthode toArray()\u001B[0m");
		this.bag = new Bag<String>();
		this.bag.add("test");
		this.bag.add("test2");
		this.bag.add("test3");
		Object[] array = this.bag.toArray();
		assertEquals("\u001B[31mECHEC du test : toArray incorrecte\u001B[0m", 3, array.length);
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Default constructors, useless
	 */
	public BagTest() {
	}
}
