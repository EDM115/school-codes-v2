import java.util.ArrayList;

public class MoneyBag {
	private ArrayList<Money> monies;

	public MoneyBag() {
		this.monies = new ArrayList<Money>();
	}

	public MoneyBag(Money m) throws IllegalArgumentException {
		this();
		if (m == null) {
			throw new IllegalArgumentException("MoneyBag() : invalid parameters");
		}
		appendMoney(m);
	}

	public void appendMoney(Money m) throws IllegalArgumentException {
		if (m == null) {
			throw new IllegalArgumentException("appendMoney() : invalid parameters");
		}
		for (Money money : this.monies) {
			if (money.getCurrency().equals(m.getCurrency())) {
				try {
					money.add(m);
				}
				catch (BadCurrencyException e) {
					System.out.println("\u001B[31mECHEC de l'ajout : " + e.getMessage() + "\u001B[0m");
				}
				if (money.getAmount() == 0) {
					this.monies.remove(money);
				}
				return;
			}
		}
	}

	public void theSame(MoneyBag mb) throws NotTheSameException {
		if (mb == null) {
			throw new NotTheSameException("theSame() : invalid parameters");
		}
		for (Money money : this.monies) {
			for (Money money2 : mb.monies) {
				if (money.getCurrency().equals(money2.getCurrency())) {
					if (!money.equals(money2)) {
						throw new NotTheSameException("theSame() : invalid parameters");
					}
				}
			}
		}
	}

	public String toString() {
		String s = "MoneyBag(";
		for (Money money : this.monies) {
			s += money.toString() + ", ";
		}
		return s.substring(0, s.length() - 2) + ")";
	}

	public ArrayList<Money> getMonies() {
		return this.monies;
	}
}
