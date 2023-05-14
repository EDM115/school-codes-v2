package staff;

/**
 * StaffMember class, used to represent a staff member
 */
public abstract class StaffMember {

	/**
	 * The name of the staff member
	 */
	protected String name;

	/**
	 * The address of the staff member
	 */
	protected String address;

	/**
	 * The phone number of the staff member
	 */
	protected String phone;

	/**
	 * Constructor of the staff member
	 * @param eName The name of the staff member
	 * @param eAddress The address of the staff member
	 * @param ePhone The phone number of the staff member
	 */
	public StaffMember(String eName, String eAddress, String ePhone) {
		if (eName == null || eAddress == null || ePhone == null) {
			System.err.println("\u001B[31mStaffMember() : les paramètres ne peuvent pas être null\u001B[0m");
			name = "";	
			address = "";
			phone = "";
		} else {
			name = eName;
			address = eAddress;
			phone = ePhone;
		}
	}

	/**
	 * Gets a string representation of the staff member
	 * @return A string representation of the staff member
	 */
	public String toString() {
		String result = "\u001B[36mName : \u001B[0m" + name + "\n" + "\u001B[36mAddress : \u001B[0m" + address + "\n" + "\u001B[36mPhone : \u001B[0m" + phone;

		return result;
	}

	/**
	 * Pays the staff member
	 * @return The payment of the StaffMember
	 */
	public abstract double pay();
}