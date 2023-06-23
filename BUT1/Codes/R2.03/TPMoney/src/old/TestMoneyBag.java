import org.junit.*;
import org.junit.runner.*;
import static org.junit.Assert.*;

public class TestMoneyBag {
	private MoneyBag mb;

	@Before
	public void instance() {
		try {
			this.mb = new MoneyBag();
		}
		catch (IllegalArgumentException e) {
			System.out.println("\u001B[31mECHEC de l'instanciation : " + e.getMessage() + "\u001B[0m");
		}
	}

	@Test
	public void testConstructeur() {
		System.out.println("\u001B[33mTest du constructeur - cas normal\u001B[0m");
		try {
			MoneyBag res = new MoneyBag(new Money(10, "EUR"));
			assertEquals("\u001B[31mECHEC du test : montant incorrect", 10, res.getMonies().get(0).getAmount());
			assertEquals("\u001B[31mECHEC du test : devise incorrecte", "EUR", res.getMonies().get(0).getCurrency());
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		} catch (IllegalArgumentException e) {
			System.out.println("\u001B[31mECHEC du test : " + e.getMessage() + "\u001B[0m");
		}
	}

	@Test
	public void testConstructeurNull() {
		System.out.println("\u001B[33mTest du constructeur - cas null\u001B[0m");
		try {
			MoneyBag res = new MoneyBag(null);
			fail("\u001B[31mECHEC du test : exception non levée\u001B[0m");
		} catch (IllegalArgumentException e) {
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
	}

	@Test
	public void testAppendMoney() {
		System.out.println("\u001B[33mTest de la méthode append - cas normal\u001B[0m");
		try {
			MoneyBag res1 = new MoneyBag(new Money(10, "EUR"));
			MoneyBag res2 = new MoneyBag();

			res2.appendMoney(new Money(10, "EUR"));
			assertEquals("\u001B[31mECHEC du test : montant incorrect", 10, res2.getMonies().get(0).getAmount());
			assertEquals("\u001B[31mECHEC du test : devise incorrecte", "EUR", res2.getMonies().get(0).getCurrency());
			assertTrue("\u001B[31mECHEC du test : montant incorrect", res1.getMonies().get(0).equals(res2.getMonies().get(0)));
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		} catch (IllegalArgumentException e) {
			System.out.println("\u001B[31mECHEC du test : " + e.getMessage() + "\u001B[0m");
		}
	}

	@Test
	public void testAppendMoneyNull() {
		System.out.println("\u001B[33mTest de la méthode append - cas null\u001B[0m");
		try {
			this.mb.appendMoney(null);
			fail("\u001B[31mECHEC du test : exception non levée\u001B[0m");
		} catch (IllegalArgumentException e) {
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
	}

	@Test
	public void testTheSame() {
		System.out.println("\u001B[33mTest de la méthode theSame - cas normal\u001B[0m");
		try {
			MoneyBag res1 = new MoneyBag(new Money(10, "EUR"));
			MoneyBag res2 = new MoneyBag(new Money(10, "EUR"));
			res1.theSame(res2);
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		} catch (NotTheSameException e) {
			System.out.println("\u001B[31mECHEC du test : " + e.getMessage() + "\u001B[0m");
		}
	}

	@Test
	public void testTheSameException() {
		System.out.println("\u001B[33mTest de la méthode theSame - cas exception\u001B[0m");
		try {
			MoneyBag res1 = new MoneyBag(new Money(10, "EUR"));
			res1.theSame(new MoneyBag(new Money(10, "USD")));
			fail("\u001B[31mECHEC du test : exception non levée\u001B[0m");
		} catch (NotTheSameException e) {
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
	}

	@Test
	public void testTheSameNull() {
		System.out.println("\u001B[33mTest de la méthode theSame - cas null\u001B[0m");
		try {
			this.mb.theSame(null);
			fail("\u001B[31mECHEC du test : exception non levée\u001B[0m");
		} catch (NotTheSameException e) {
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		}
	}

	@Test
	public void testToString() {
		System.out.println("\u001B[33mTest de la méthode toString - cas normal\u001B[0m");
		try {
			MoneyBag res = new MoneyBag(new Money(10, "EUR"));
			assertEquals("\u001B[31mECHEC du test : toString incorrect", "MoneyBag(10 EUR)", res.toString());
			System.out.println("\u001B[32mTest réussi\u001B[0m");
		} catch (IllegalArgumentException e) {
			System.out.println("\u001B[31mECHEC du test : " + e.getMessage() + "\u001B[0m");
		}
	}

	public static void main(String[] args) {
		JUnitCore.main("TestMoneyBag");
	}
}
