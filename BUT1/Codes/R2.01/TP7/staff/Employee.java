package staff;

/**
 * Employee class, used to represent an employee
 */
public class Employee extends StaffMember {

	/**
	 * The pay rate of the employee
	 */
	protected double payRate;

	/**
	 * The social security number of the employee
	 */
	protected String socialSecurityNumber;

	/**
	 * Constructor of the employee
	 * @param eName The name of the employee
	 * @param eAddress The address of the employee
	 * @param ePhone The phone number of the employee
	 * @param socSecNumber The social security number of the employee
	 * @param rate The pay rate of the employee
	 */
	public Employee(String eName, String eAddress, String ePhone, String socSecNumber, double rate) {
		super(eName, eAddress, ePhone);
		if (socSecNumber == null) {
			System.err.println("\u001B[31mEmployee() : le numéro de sécurité sociale ne peut pas être null\u001B[0m");
			socialSecurityNumber = "";
		} else {
			socialSecurityNumber = socSecNumber;
		}
		if (rate < 0) {
			System.err.println("\u001B[31mEmployee() : le taux de paye ne peut pas être négatif\u001B[0m");
			payRate = 0;
		} else {
			payRate = rate;
		}
	}

	/**
	 * "Pays" the employee
	 * @return The pay rate of the employee
	 */
	public double pay() {
		return payRate;
	}

	/**
	 * Gets a string representation of the employee
	 * @return A string representation of the employee
	 */
	public String toString() {
		String result = super.toString() + "\n" + "\u001B[36mSocial security number : \u001B[0m" + socialSecurityNumber + "\n\u001B[36mPay rate : \u001B[0m" + payRate;

		return result;
	}
}