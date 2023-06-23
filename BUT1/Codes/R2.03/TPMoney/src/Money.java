public class Money implements IMoney {
    private int amount;
    private String currency;

    public Money(int amount, String currency) throws IllegalArgumentException {
        if (amount < 0 || currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("Money() : invalid parameters");
        }
        this.amount = amount;
        this.currency = currency;

        assert invariant() : "Money() : invariant violated";
    }

    @Override
    public int getAmount() {
        assert invariant() : "getAmount() : invariant violated";
        return this.amount;
    }

    @Override
    public String getCurrency() {
        assert invariant() : "getCurrency() : invariant violated";
        return this.currency;
    }

    @Override
    public IMoney add(Money m) {
        if (m == null) {
            throw new IllegalArgumentException("Money to add must not be null");
        }

        if (this.currency.equals(m.getCurrency())) {
            int newAmount = this.amount + m.getAmount();
            Money result = new Money(newAmount, this.currency);

            assert result.getAmount() == (this.amount + m.getAmount()) : "Addition post-condition violated: incorrect amount";
            assert result.getCurrency().equals(this.currency) : "Addition post-condition violated: incorrect currency";
            assert result.invariant() : "Addition post-condition violated: invariant";

            return result;
        } else {
            MoneyBag bag = new MoneyBag();
            bag.appendMoney(this);
            bag.appendMoney(m);
            return bag;
        }
    }

    public boolean equals(Money m) {
        return this.amount == m.amount && this.currency.equals(m.currency);
    }

    public String toString() {
        assert invariant() : "toString() : invariant violated";
        return "Money(" + this.amount + ", " + this.currency + ")";
    }

    private boolean invariant() {
        // Vérification de la validité des attributs
        boolean validAmount = this.amount >= 0;
        boolean validCurrency = this.currency != null && !this.currency.isEmpty();

        // Renvoie vrai si tous les attributs sont valides, sinon faux
        return validAmount && validCurrency;
    }
}
