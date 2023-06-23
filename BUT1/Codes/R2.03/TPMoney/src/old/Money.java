public class Money {
	private int amount;
	private String currency;

	public Money(int amount, String currency) throws IllegalArgumentException {
		if (amount < 0 || currency == null || currency.isEmpty()) {
			throw new IllegalArgumentException("Money() : invalid parameters");
		}
		this.amount = amount;
		this.currency = currency;
	}

	public int getAmount() {
		return this.amount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public Money add(Money m) throws BadCurrencyException {
		if (m == null || !this.currency.equals(m.currency)) {
			throw new BadCurrencyException("Money.add() : invalid parameters");
		}
		return new Money(this.amount + m.amount, this.currency);
	}
	
	public boolean equals(Money m) {
		return this.amount == m.amount && this.currency.equals(m.currency);
	}

	public String toString() {
		return "Money(" + this.amount + ", " + this.currency + ")";
	}
}
