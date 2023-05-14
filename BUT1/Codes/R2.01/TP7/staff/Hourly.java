package staff;

/**
 * Hourly class, used to represent an hourly employee
 */
public class Hourly extends Employee {

	/**
	 * The hours worked by the hourly employee
	 */
	private int hoursWorked;

	/**
	 * Constructor of the hourly employee
	 * @param eName The name of the hourly employee
	 * @param eAddress The address of the hourly employee
	 * @param ePhone The phone number of the hourly employee
	 * @param socSecNumber The social security number of the hourly employee
	 * @param rate The pay rate of the hourly employee
	 */
	public Hourly(String eName, String eAddress, String ePhone, String socSecNumber, double rate) {
		super(eName, eAddress, ePhone, socSecNumber, rate);
		this.hoursWorked = 0;
	}

	/**
	 * Adds hours to the hourly employee
	 * @param moreHours The hours to add
	 */
	public void addHours(int moreHours) {
		if (moreHours < 0) {
			System.err.println("\u001B[31maddHours() : les heures ne peuvent pas être négatives\u001B[0m");
		} else {
			this.hoursWorked += moreHours;
		}
	}

	/**
	 * Pays the hourly employee
	 * @return The payment of the hourly employee
	 */
	public double pay() {
		double payment = super.pay() * this.hoursWorked;
		this.hoursWorked = 0;
		return payment;
	}

	/**
	 * Gets a string representation of the hourly employee
	 * @return A string representation of the hourly employee
	 */
	public String toString() {
		String result = super.toString() + "\n" + "\u001B[36mHeures travaillées : \u001B[0m" + this.hoursWorked;

		return result;
	}
}