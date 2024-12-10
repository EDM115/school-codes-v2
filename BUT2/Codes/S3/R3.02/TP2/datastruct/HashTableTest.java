package datastruct;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * Tests every method from HashTable in JUnit4
 */
public class HashTableTest {
	/**
	 * The table that will contain all the tuples
	 */
	private HashTable ht;

	/**
	 * Creates a new instance of HashTable for each test
	 */
	@Before
	public void setUp() {
		this.ht = new HashTable();
	}

	/**
	 * Destroys the instance of HashTable after each test
	 */
	@After
	public void tearDown() {
		this.ht = null;
	}

	/**
	 * Tests the select() method with a correct key
	 */
	@Test
	public final void testSelect() {
		System.out.println("\u001B[33mTest de la méthode select - cas normal\u001B[0m");
		this.ht.insert("test", "test");
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", "test", this.ht.select("test"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the select() method with a null key
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSelectNull() {
		System.out.println("\u001B[33mTest de la méthode select - cas d'erreur : clé nulle\u001B[0m");
		this.ht.select(null);
		System.out.println("\u001B[31mECHEC du test : aucune exception levée\u001B[0m");
	}

	/**
	 * Tests the select() method with a key that doesn't exist
	 */
	@Test
	public final void testSelectNotFound() {
		System.out.println("\u001B[33mTest de la méthode select - cas d'erreur : clé inexistante\u001B[0m");
		assertNull("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.ht.select("test"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the insert() method with a correct key and data
	 */
	@Test
	public final void testInsert() {
		System.out.println("\u001B[33mTest de la méthode insert - cas normal\u001B[0m");
		assertTrue("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.ht.insert("test", "test"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the insert() method with a null key
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testInsertNullKey() {
		System.out.println("\u001B[33mTest de la méthode insert - cas d'erreur : clé nulle\u001B[0m");
		this.ht.insert(null, "test");
		System.out.println("\u001B[31mECHEC du test : aucune exception levée\u001B[0m");
	}

	/**
	 * Tests the insert() method with a null data
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testInsertNullData() {
		System.out.println("\u001B[33mTest de la méthode insert - cas d'erreur : donnée nulle\u001B[0m");
		this.ht.insert("test", null);
		System.out.println("\u001B[31mECHEC du test : aucune exception levée\u001B[0m");
	}

	/**
	 * Tests the insert() method with a key that already exists
	 */
	@Test
	public final void testInsertAlreadyExists() {
		System.out.println("\u001B[33mTest de la méthode insert - cas d'erreur : clé déjà existante\u001B[0m");
		this.ht.insert("test", "test");
		assertFalse("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.ht.insert("test", "test"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the insert() method with a full table
	 */
	@Test
	public final void testInsertFullTable() {
		System.out.println("\u001B[33mTest de la méthode insert - cas d'erreur : table pleine\u001B[0m");
		for (int i = 0; i < this.ht.TAILLE; i++) {
			this.ht.insert("test" + i, "test" + i);
		}
		assertFalse("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.ht.insert("test", "test"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the delete() method with a correct key
	 */
	@Test
	public final void testDelete() {
		System.out.println("\u001B[33mTest de la méthode delete - cas normal\u001B[0m");
		this.ht.insert("test", "test");
		assertTrue("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.ht.delete("test"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the delete() method with a null key
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testDeleteNull() {
		System.out.println("\u001B[33mTest de la méthode delete - cas d'erreur : clé nulle\u001B[0m");
		this.ht.delete(null);
		System.out.println("\u001B[31mECHEC du test : aucune exception levée\u001B[0m");
	}

	/**
	 * Tests the delete() method with a key that doesn't exist
	 */
	@Test
	public final void testDeleteNotFound() {
		System.out.println("\u001B[33mTest de la méthode delete - cas d'erreur : clé inexistante\u001B[0m");
		assertFalse("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.ht.delete("test"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the delete() method with a full table
	 */
	@Test
	public final void testDeleteFullTable() {
		System.out.println("\u001B[33mTest de la méthode delete - cas d'erreur : table pleine\u001B[0m");
		for (int i = 0; i < this.ht.TAILLE; i++) {
			this.ht.insert("test" + i, "test" + i);
		}
		assertTrue("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.ht.delete("test0"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the delete() method with a key that already exists
	 */
	@Test
	public final void testDeleteAlreadyExists() {
		System.out.println("\u001B[33mTest de la méthode delete - cas d'erreur : clé déjà existante\u001B[0m");
		this.ht.insert("test", "test");
		this.ht.insert("test2", "test2");
		assertTrue("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", this.ht.delete("test"));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the toString() method with a full table
	 */
	@Test
	public final void testToString() {
		System.out.println("\u001B[33mTest de la méthode toString - cas normal\u001B[0m");
		for (int i = 0; i < this.ht.TAILLE; i++) {
			this.ht.insert("test" + i, "test" + i);
		}
		String expected = "";
		for (int i = 0; i < this.ht.TAILLE; i++) {
			expected += "[" + i + "] Tuple [key=test" + ((i + 4) % 10) + ", data=test" + ((i + 4) % 10) + "]\n";
		}
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", expected, this.ht.toString());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Tests the toString() method with an empty table
	 */
	@Test
	public final void testToStringEmpty() {
		System.out.println("\u001B[33mTest de la méthode toString - cas d'erreur : table vide\u001B[0m");
		String expected = "";
		for (int i = 0; i < this.ht.TAILLE; i++) {
			expected += "[" + i + "] null\n";
		}
		assertEquals("\u001B[31mECHEC du test : valeur incorrecte\u001B[0m", expected, this.ht.toString());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	/**
	 * Runs the tests
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		JUnitCore.main("HashTableTest");
	}

	/**
	 * Default constructor, useless
	 */
	public HashTableTest() {}
}
