package staff;

/**
 * Volunteer class, used to represent a volunteer
 */
public class Volunteer extends StaffMember {

	/**
	 * Constructor of the volunteer
	 * @param eName The name of the volunteer
	 * @param eAddress The address of the volunteer
	 * @param ePhone The phone number of the volunteer
	 */
	public Volunteer(String eName, String eAddress, String ePhone) {
		super(eName, eAddress, ePhone);
	}

	/**
	 * "Pays" the volunteer
	 * @return 0.0
	 */
	public double pay() {
		return 0.0;
	}

	/**
	 * Gets a string representation of the volunteer
	 * @return A string representation of the volunteer
	 */
	public String toString() {
		String result = super.toString();

		return result;
	}
}