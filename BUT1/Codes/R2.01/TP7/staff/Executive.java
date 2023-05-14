package staff;

/**
 * Executive class, used to represent an executive
 */
public class Executive extends Employee {

	/**
	 * Bonus of the executive
	 */
	private double bonus;

	/**
	 * Constructor of the executive
	 * @param eName The name of the executive
	 * @param eAddress The address of the executive
	 * @param ePhone The phone number of the executive
	 * @param socSecNumber The social security number of the executive
	 * @param rate The pay rate of the executive
	 */
	public Executive(String eName, String eAddress, String ePhone, String socSecNumber, double rate) {
		super(eName, eAddress, ePhone, socSecNumber, rate);
		this.bonus = 0;
	}

	/**
	 * Awards a bonus to the executive
	 * @param execBonus The bonus to award
	 */
	public void awardBonus(double execBonus) {
		if (execBonus < 0) {
			System.err.println("\u001B[31mawardBonus() : le bonus ne peut pas être négatif\u001B[0m");
		} else {
			this.bonus = execBonus;
		}
	}

	/**
	 * Pays the executive
	 * @return The payment of the executive
	 */
	public double pay() {
		double payment = super.pay() + this.bonus;
		this.bonus = 0;
		return payment;
	}

	/**
	 * Gets a string representation of the executive
	 * @return A string representation of the executive
	 */
	public String toString() {
		String result = super.toString() + "\n" + "\u001B[36mBonus : \u001B[0m" + this.bonus;

		return result;
	}
}