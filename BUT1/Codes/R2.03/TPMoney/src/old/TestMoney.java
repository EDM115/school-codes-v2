import org.junit.*;
import org.junit.runner.*;
import static org.junit.Assert.*;

public class TestMoney {
	private Money m;

	@Before
	public void instance() {
		try {
			this.m = new Money(10, "EUR");
		}
		catch (IllegalArgumentException e) {
			System.out.println("\u001B[31mECHEC de l'instanciation : " + e.getMessage() + "\u001B[0m");
		}
	}

	@Test
	public void testGetAmount() {
		System.out.println("\u001B[33mTest de la méthode getAmount - cas normal\u001B[0m");
		int res = this.m.getAmount();
		assertEquals("\u001B[31mECHEC du test : montant incorrect\u001B[0m", 10, res);
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public void testGetCurrency() {
		System.out.println("\u001B[33mTest de la méthode getCurrency - cas normal\u001B[0m");
		String res = this.m.getCurrency();
		assertEquals("\u001B[31mECHEC du test : devise incorrecte\u001B[0m", "EUR", res);
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public void testAdd() {
		System.out.println("\u001B[33mTest de la méthode add - cas normal\u001B[0m");
		try {
			Money res = this.m.add(new Money(5, "EUR"));
			assertEquals("\u001B[31mECHEC du test : montant incorrect", 15, res.getAmount());
			assertEquals("\u001B[31mECHEC du test : devise incorrecte", "EUR", res.getCurrency());
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
		catch (BadCurrencyException e) {
			System.out.println("\u001B[31mECHEC du test : " + e.getMessage() + "\u001B[0m");
		}
	}

	@Test
	public void testAddDevise() {
		System.out.println("\u001B[33mTest de la méthode add - cas devise différente\u001B[0m");
		try {
			Money res = this.m.add(new Money(5, "USD"));
			fail("\u001B[31mECHEC du test : exception non levée\u001B[0m");
		}
		catch (BadCurrencyException e) {
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
	}

	@Test
	public void testAddNull() {
		System.out.println("\u001B[33mTest de la méthode add - cas null\u001B[0m");
		try {
			Money res = this.m.add(null);
			fail("\u001B[31mECHEC du test : exception non levée\u001B[0m");
		}
		catch (BadCurrencyException e) {
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
	}

	@Test
	public void testEquals() {
		System.out.println("\u001B[33mTest de la méthode equals - cas normal\u001B[0m");
		boolean res = this.m.equals(new Money(10, "EUR"));
		assertTrue("\u001B[31mECHEC du test : montant incorrect\u001B[0m", res);
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public void testEqualsDevise() {
		System.out.println("\u001B[33mTest de la méthode equals - cas devise différente\u001B[0m");
		boolean res = this.m.equals(new Money(10, "USD"));
		assertFalse("\u001B[31mECHEC du test : montant incorrect\u001B[0m", res);
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public void testEqualsNull() {
		System.out.println("\u001B[33mTest de la méthode equals - cas null\u001B[0m");
		try {
			boolean res = this.m.equals(null);
			fail("\u001B[31mECHEC du test : exception non levée\u001B[0m");
		}
		catch (NullPointerException e) {
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
	}

	@Test
	public void testToString() {
		System.out.println("\u001B[33mTest de la méthode toString - cas normal\u001B[0m");
		String res = this.m.toString();
		assertEquals("\u001B[31mECHEC du test : chaîne incorrecte\u001B[0m", "Money(10, EUR)", res);
		System.out.println("\u001B[32mTest réussi\u001B[0m");
	}

	@Test
	public void testToStringNull() {
		System.out.println("\u001B[33mTest de la méthode toString - cas null\u001B[0m");
		try {
			Money m = new Money(10, null);
			fail("\u001B[31mECHEC du test : exception non levée\u001B[0m");
		}
		catch (IllegalArgumentException e) {
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
	}

	@Test
	public void testToStringEmpty() {
		System.out.println("\u001B[33mTest de la méthode toString - cas chaîne vide\u001B[0m");
		try {
			Money m = new Money(10, "");
			fail("\u001B[31mECHEC du test : exception non levée\u001B[0m");
		}
		catch (IllegalArgumentException e) {
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
	}

	public static void main(String[] args) {
		JUnitCore.main("TestMoney");
	}
}
