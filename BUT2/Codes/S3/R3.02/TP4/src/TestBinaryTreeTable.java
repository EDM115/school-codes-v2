import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import datastruct.BinaryTreeTable;

public class TestBinaryTreeTable {
	BinaryTreeTable<Integer, String> table;

	@Before
	public void setUp() throws Exception {
		table = new BinaryTreeTable<Integer, String>();
	}

	@After
	public void tearDown() throws Exception {
		table = null;
	}

	@Test
	public final void testBinaryTreeTable() {
		System.out.println("\u001B[33mTest du constructeur BinaryTreeTable()\u001B[0m");
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, table.computeH(table.getTheRoot()));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testInsert() {
		System.out.println("\u001B[33mTest de la méthode insert() - Cas normal\u001B[0m");
		table.insert(1, "test");
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 1, table.computeH(table.getTheRoot()));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
		System.out.println("\u001B[33mTest de la méthode insert() - Cas d'erreur\u001B[0m");
		table.insert(1, "test");
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 1, table.computeH(table.getTheRoot()));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testInsertNull() {
		System.out.println("\u001B[33mTest de la méthode insert() - Cas d'erreur : clé nulle\u001B[0m");
		table.insert(null, "test");
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, table.computeH(table.getTheRoot()));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
		System.out.println("\u001B[33mTest de la méthode insert() - Cas d'erreur : donnée nulle\u001B[0m");
		table.insert(1, null);
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, table.computeH(table.getTheRoot()));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testDelete() {
		System.out.println("\u001B[33mTest de la méthode delete() - Cas normal\u001B[0m");
		table.insert(1, "test");
		table.delete(1);
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, table.computeH(table.getTheRoot()));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
		System.out.println("\u001B[33mTest de la méthode delete() - Cas d'erreur\u001B[0m");
		table.delete(1);
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, table.computeH(table.getTheRoot()));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testDeleteNull() {
		System.out.println("\u001B[33mTest de la méthode delete() - Cas d'erreur : clé nulle\u001B[0m");
		table.delete(null);
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, table.computeH(table.getTheRoot()));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testDeleteEmpty() {
		System.out.println("\u001B[33mTest de la méthode delete() - Cas limite : arbre vide\u001B[0m");
		table.delete(1);
		assertEquals("\u001B[31mECHEC du test : taille incorrecte\u001B[0m", 0, table.computeH(table.getTheRoot()));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testGetTheRoot() {
		System.out.println("\u001B[33mTest de la méthode getTheRoot() - Cas normal\u001B[0m");
		table.insert(1, "test");
		assertNotNull("\u001B[31mECHEC du test : racine null\u001B[0m", table.getTheRoot());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testGetTheRootEmpty() {
		System.out.println("\u001B[33mTest de la méthode getTheRoot() - Cas limite : arbre vide\u001B[0m");
		assertNull("\u001B[31mECHEC du test : racine non null\u001B[0m", table.getTheRoot());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testToString() {
		System.out.println("\u001B[33mTest de la méthode toString()\u001B[0m");
		table.insert(1, "test");
		table.insert(2, "test2");
		table.insert(3, "test3");
		String expected = "\nclé=1	data=test\nclé=2	data=test2\nclé=3	data=test3";
		assertEquals("\u001B[31mECHEC du test : toString() incorrecte\u001B[0m", expected, table.toString());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testClone() {
		System.out.println("\u001B[33mTest de la méthode clone() - Cas normal\u001B[0m");
		table.insert(1, "test");
		table.insert(2, "test2");
		table.insert(3, "test3");
		String expected = "\nclé=1	data=test\nclé=2	data=test2\nclé=3	data=test3";
		BinaryTreeTable<Integer, String> clone = table.clone();
		assertEquals("\u001B[31mECHEC du test : clone() incorrecte\u001B[0m", expected, clone.toString());
		assertEquals("\u001B[31mECHEC du test : clone() incorrecte\u001B[0m", table.computeH(table.getTheRoot()), clone.computeH(clone.getTheRoot()));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testCloneEmpty() {
		System.out.println("\u001B[33mTest de la méthode clone() - Cas limite : arbre vide\u001B[0m");
		BinaryTreeTable<Integer, String> clone = table.clone();
		assertEquals("\u001B[31mECHEC du test : clone() incorrecte\u001B[0m", "", clone.toString());
		assertEquals("\u001B[31mECHEC du test : clone() incorrecte\u001B[0m", table.computeH(table.getTheRoot()), clone.computeH(clone.getTheRoot()));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testShowTree() {
		System.out.println("\u001B[33mTest de la méthode showTree()\u001B[0m");
		table.insert(1, "test");
		table.insert(2, "test2");
		table.insert(3, "test3");
		table.showTree();
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testSelect() {
		System.out.println("\u001B[33mTest de la méthode select() - Cas normal\u001B[0m");
		table.insert(1, "test");
		table.insert(2, "test2");
		table.insert(3, "test3");
		assertEquals("\u001B[31mECHEC du test : select() incorrecte\u001B[0m", "test", table.select(1));
		assertEquals("\u001B[31mECHEC du test : select() incorrecte\u001B[0m", "test2", table.select(2));
		assertEquals("\u001B[31mECHEC du test : select() incorrecte\u001B[0m", "test3", table.select(3));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testSelectEmpty() {
		System.out.println("\u001B[33mTest de la méthode select() - Cas limite : arbre vide\u001B[0m");
		assertNull("\u001B[31mECHEC du test : select() incorrecte\u001B[0m", table.select(1));
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testIsAVL() {
		System.out.println("\u001B[33mTest de la méthode isAVL() - Cas normal\u001B[0m");
		table.insert(2, "test2");
		table.insert(1, "test1");
		table.insert(3, "test3");
		assertTrue("\u001B[31mECHEC du test : isAVL() incorrecte\u001B[0m", table.isAVL());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testIsAVLEmpty() {
		System.out.println("\u001B[33mTest de la méthode isAVL() - Cas limite : arbre vide\u001B[0m");
		assertTrue("\u001B[31mECHEC du test : isAVL() incorrecte\u001B[0m", table.isAVL());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public final void testSetBalance() {
		System.out.println("\u001B[33mTest de la méthode setBalance() - Cas normal\u001B[0m");
		table.setBalance(true);
		assertTrue("\u001B[31mECHEC du test : setBalance() incorrecte\u001B[0m", table.getBalance());
		table.setBalance(false);
		assertFalse("\u001B[31mECHEC du test : setBalance() incorrecte\u001B[0m", table.getBalance());
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}
}
