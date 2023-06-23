import java.util.ArrayList;
import java.util.List;

public class MoneyBag implements IMoney {
    private List<Money> monies;

    public MoneyBag() {
        monies = new ArrayList<>();
    }

    public List<Money> getMonies() {
        return monies;
    }

    @Override
    public int getAmount() {
        assert invariant() : "Invariant violated!";

        int amount = 0;
        for (Money money : this.monies) {
            amount += money.getAmount();
        }
        return amount;
    }

    @Override
    public String getCurrency() {
        assert invariant() : "Invariant violated!";

        String currency = "";
        for (Money money : this.monies) {
            if (!money.getCurrency().isEmpty()) {
                currency += money.getCurrency();
            }
        }
        return currency;
    }

    public MoneyBag(Money m) throws IllegalArgumentException {
        this();
        assert m != null : "MoneyBag() : invalid parameters";

        appendMoney(m);
    }

    @Override
    public IMoney add(Money m) {
        assert m != null : "add() : invalid parameters";
        assert invariant() : "Invariant violated!";

        MoneyBag bag = new MoneyBag();
        for (Money money : monies) {
            bag.appendMoney(money);
        }
        bag.appendMoney(m);
        assert bag.invariant() : "Post-condition violated!";
        return bag;
    }

    public IMoney add(MoneyBag mb) {
        assert mb != null : "add() : invalid parameters";
        assert invariant() : "Invariant violated!";

        MoneyBag bag = new MoneyBag();
        for (Money money : monies) {
            bag.appendMoney(money);
        }
        for (Money money : mb.getMonies()) {
            bag.appendMoney(money);
        }
        assert bag.invariant() : "Post-condition violated!";
        return bag;
    }

    public void appendMoney(Money m) throws IllegalArgumentException {
        assert m != null : "appendMoney() : invalid parameters";
        assert invariant() : "Invariant violated!";

        for (Money money : this.monies) {
            if (money.getCurrency().equals(m.getCurrency())) {
                money.add(m);
                if (money.getAmount() == 0) {
                    this.monies.remove(money);
                }
                assert invariant() : "Post-condition violated!";
                return;
            }
        }

        // If no matching currency found, add the money to the list
        this.monies.add(m);
        assert invariant() : "Post-condition violated!";
    }

    public void theSame(MoneyBag mb) throws NotTheSameException {
        assert mb != null : "theSame() : invalid parameters";
        assert invariant() : "Invariant violated!";

        for (Money money : this.monies) {
            for (Money money2 : mb.monies) {
                if (money.getCurrency().equals(money2.getCurrency())) {
                    if (!money.equals(money2)) {
                        throw new NotTheSameException("theSame() : invalid parameters");
                    }
                }
            }
        }
        assert invariant() : "Post-condition violated!";
    }

    private boolean invariant() {
        // Check the invariant for the MoneyBag
        // The invariant ensures that the list of monies is valid and consistent
        for (Money money : monies) {
            if (money.getAmount() < 0 || money.getCurrency().isEmpty()) {
                return false;
            }
        }
		return true;
	}

	public String toString() {
		assert invariant() : "Invariant violated!";

		String s = "MoneyBag(";
		for (Money money : this.monies) {
			s += money.toString() + ", ";
		}
		return s.substring(0, s.length() - 2) + ")";
	}
}