import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import datastruct.LinkedList;

/**
 * Tests every method from LinkedList in JUnit4
 */
public class LinkedListTest {
	/**
	 * The list used for the tests
	 */
	LinkedList list;

	/**
	 * Creates a new instance of LinkedList before each test
	 */
	@Before
	public void setUp() {
		this.list = new LinkedList();
	}

	/**
	 * Destroys the instance of LinkedList after each test
	 */
	@After
	public void tearDown() {
		this.list = null;
	}

	/**
	 * Tests the insert() method with a correct parameter
	 */
	@Test
	public void testInsert() {
		System.out.println("\u001B[33mTest de la méthode insert - cas normal\u001B[0m");
		this.list.insert("test");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test", this.list.getValue());
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 1, this.list.getSize());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the insert() method with a null parameter
	 */
	@Test
	public void testInsertNull() {
		System.out.println("\u001B[33mTest de la méthode insert - cas d'erreur\u001B[0m");
		this.list.insert(null);
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", null, this.list.getValue());
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, this.list.getSize());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the delete() method with a non-empty list
	 */
	@Test
	public void testDelete() {
		System.out.println("\u001B[33mTest de la méthode delete - cas normal\u001B[0m");
		this.list.insert("test");
		this.list.delete();
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", null, this.list.getValue());
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, this.list.getSize());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the delete() method with an empty list
	 */
	@Test
	public void testDeleteEmpty() {
		System.out.println("\u001B[33mTest de la méthode delete - cas d'erreur\u001B[0m");
		this.list.delete();
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", null, this.list.getValue());
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, this.list.getSize());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the contains() method with a non-empty list
	 */
	@Test
	public void testContains() {
		System.out.println("\u001B[33mTest de la méthode contains - cas normal\u001B[0m");
		this.list.insert("test");
		assertTrue("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.list.contains("test"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the contains() method with an empty list
	 */
	@Test
	public void testContainsEmpty() {
		System.out.println("\u001B[33mTest de la méthode contains - cas limite\u001B[0m");
		assertFalse("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.list.contains("test"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the contains() method with a null parameter
	 */
	@Test
	public void testContainsNull() {
		System.out.println("\u001B[33mTest de la méthode contains - cas d'erreur\u001B[0m");
		assertFalse("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.list.contains(null));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the add() method with a correct parameter
	 */
	@Test
	public void testAdd() {
		System.out.println("\u001B[33mTest de la méthode add - cas normal\u001B[0m");
		this.list.insert("test");
		this.list.add(0, "test2");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test2", this.list.getValue());
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 2, this.list.getSize());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the add() method with a null parameter
	 */
	@Test
	public void testAddNull() {
		System.out.println("\u001B[33mTest de la méthode add - cas d'erreur\u001B[0m");
		this.list.insert("test");
		this.list.add(0, null);
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test", this.list.getValue());
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 1, this.list.getSize());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the add() method with an index out of bounds
	 */
	@Test
	public void testAddOutOfBounds() {
		System.out.println("\u001B[33mTest de la méthode add - cas d'erreur\u001B[0m");
		this.list.insert("test");
		this.list.add(2, "test2");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test", this.list.getValue());
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 1, this.list.getSize());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the getValue() method with a non-empty list
	 */
	@Test
	public void testGetValue() {
		System.out.println("\u001B[33mTest de la méthode getValue - cas normal\u001B[0m");
		this.list.insert("test");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test", this.list.getValue());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the getValue() method with an empty list
	 */
	@Test
	public void testGetValueEmpty() {
		System.out.println("\u001B[33mTest de la méthode getValue - cas limite\u001B[0m");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", null, this.list.getValue());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the setValue() method with a non-empty list
	 */
	@Test
	public void testSetValue() {
		System.out.println("\u001B[33mTest de la méthode setValue - cas normal\u001B[0m");
		this.list.insert("test");
		this.list.setValue("test2");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test2", this.list.getValue());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the setValue() method with an empty list
	 */
	@Test
	public void testSetValueEmpty() {
		System.out.println("\u001B[33mTest de la méthode setValue - cas d'erreur\u001B[0m");
		this.list.setValue("test");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", null, this.list.getValue());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the setValue() method with a null parameter
	 */
	@Test
	public void testSetValueNull() {
		System.out.println("\u001B[33mTest de la méthode setValue - cas d'erreur\u001B[0m");
		this.list.insert("test");
		this.list.setValue(null);
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test", this.list.getValue());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the isEmpty() method with a non-empty list
	 */
	@Test
	public void testIsEmpty() {
		System.out.println("\u001B[33mTest de la méthode isEmpty - cas normal\u001B[0m");
		this.list.insert("test");
		assertFalse("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.list.isEmpty());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the isEmpty() method with an empty list
	 */
	@Test
	public void testIsEmptyEmpty() {
		System.out.println("\u001B[33mTest de la méthode isEmpty - cas normal\u001B[0m");
		assertTrue("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.list.isEmpty());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the getSize() method with a non-empty list
	 */
	@Test
	public void testGetSize() {
		System.out.println("\u001B[33mTest de la méthode getSize - cas normal\u001B[0m");
		this.list.insert("test");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", 1, this.list.getSize());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the getSize() method with an empty list
	 */
	@Test
	public void testGetSizeEmpty() {
		System.out.println("\u001B[33mTest de la méthode getSize - cas normal\u001B[0m");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", 0, this.list.getSize());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the goToHead() method with a non-empty list
	 */
	@Test
	public void testGoToHead() {
		System.out.println("\u001B[33mTest de la méthode goToHead - cas normal\u001B[0m");
		this.list.insert("test");
		this.list.goToHead();
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test", this.list.getValue());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the goToHead() method with an empty list
	 */
	@Test
	public void testGoToHeadEmpty() {
		System.out.println("\u001B[33mTest de la méthode goToHead - cas d'erreur\u001B[0m");
		this.list.goToHead();
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", null, this.list.getValue());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the goToEnd() method with a non-empty list
	 */
	@Test
	public void testGoToEnd() {
		System.out.println("\u001B[33mTest de la méthode goToEnd - cas normal\u001B[0m");
		this.list.insert("test");
		this.list.goToEnd();
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test", this.list.getValue());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the goToEnd() method with an empty list
	 */
	@Test
	public void testGoToEndEmpty() {
		System.out.println("\u001B[33mTest de la méthode goToEnd - cas d'erreur\u001B[0m");
		this.list.goToEnd();
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", null, this.list.getValue());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the next() method with a non-empty list
	 */
	@Test
	public void testNext() {
		System.out.println("\u001B[33mTest de la méthode next - cas normal\u001B[0m");
		this.list.insert("test");
		this.list.insert("test2");
		this.list.next();
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test", this.list.getValue());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the next() method with an empty list
	 */
	@Test
	public void testNextEmpty() {
		System.out.println("\u001B[33mTest de la méthode next - cas d'erreur\u001B[0m");
		this.list.next();
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", null, this.list.getValue());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the toString() method with a non-empty list
	 */
	@Test
	public void testToString() {
		System.out.println("\u001B[33mTest de la méthode toString - cas normal\u001B[0m");
		this.list.insert("test");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test ", this.list.toString());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the toString() method with an empty list
	 */
	@Test
	public void testToStringEmpty() {
		System.out.println("\u001B[33mTest de la méthode toString - cas limite\u001B[0m");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "", this.list.toString());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the getValueAt() method with a non-empty list
	 */
	@Test
	public void testGetValueAt() {
		System.out.println("\u001B[33mTest de la méthode getValueAt - cas normal\u001B[0m");
		this.list.insert("test");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test", this.list.getValueAt(0));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the getValueAt() method with an empty list
	 */
	@Test
	public void testGetValueAtEmpty() {
		System.out.println("\u001B[33mTest de la méthode getValueAt - cas d'erreur\u001B[0m");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", null, this.list.getValueAt(0));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Runs the tests
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		JUnitCore.main("LinkedListTest");
	}

	/**
	 * Default constructor, useless
	 */
	public LinkedListTest() {}
}
