import org.junit.*; // accès aux classes JUnit4
import org.junit.runner.*; // permet l'exécution de la classe de test
import static org.junit.Assert.*; // accès aux assertions

public class TestOperations {
	private Operations op;

	@Before
	public void instancier() {
		this.op = new Operations();
	}

	@Test
	public void testAdditionne() {
		System.out.println("\u001B[33mTest de la méthode additionne - cas normal\u001B[0m");
		int res = this.op.additionne(1, 2);
		// automatisation : « res » doit contenir « 3 » sinon le test est en erreur
		// if/else s'écrit en 1 seule ligne avec assertEquals
		assertEquals("\u001B[31mECHEC du test\u001B[0m", 3, res);

		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public void testCalculeRacineCarree() {
		double test = 4;
		System.out.println("\u001B[33mTest de la méthode calculeRacineCarree - cas normal\u001B[0m");
		try {
			double res = this.op.calculeRacineCarree(test);
			assertEquals("\u001B[31mECHEC du test : racine carrée incorrecte\u001B[0m", 2d, res, 0d);
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
		catch (ArithmeticException e) {
			System.out.println("\u001B[31mECHEC du test : " + e.getMessage() + "\u001B[0m");
		}
	}

	@Test
	public void testCalculeRacineCarreeNegatif() {
		double test = -4;
		System.out.println("\u001B[33mTest de la méthode calculeRacineCarree - cas d'erreur\u001B[0m");
		try {
			double res = this.op.calculeRacineCarree(test);
			fail("\u001B[31mECHEC du test : exception non levée\u001B[0m");
		}
		catch (ArithmeticException e) {
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
	}

	@Test
	public void testCalculeRacineCarreeLimite() {
		double test = 0;
		System.out.println("\u001B[33mTest de la méthode calculeRacineCarree - cas limite\u001B[0m");
		try {
			double res = this.op.calculeRacineCarree(test);
			assertEquals("\u001B[31mECHEC du test : racine carrée incorrecte\u001B[0m", 0d, res, 0d);
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
		catch (ArithmeticException e) {
			System.out.println("\u001B[31mECHEC du test : " + e.getMessage() + "\u001B[0m");
		}
	}
	
	public static void main(String args[]) {
		JUnitCore.main("TestOperations");
	}
}