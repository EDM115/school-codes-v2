package staff;

import java.util.ArrayList;

/**
 * Staff class, used to represent a staff
 */
public class Staff {

	/**
	 * The list of staff members
	 */
	ArrayList<StaffMember> staffList;

	/**
	 * Constructor of the staff
	 */
	public Staff() {
		this.staffList = new ArrayList<StaffMember>();
	}

	/**
	 * Adds a new member to the staff
	 * @param member The member to add
	 */
	public void addNewMember(StaffMember member) {
		this.staffList.add(member);
	}

	/**
	 * Gets a member of the staff
	 * @param index The index of the member
	 * @return The member
	 */
	public StaffMember getMember(int index) {
		if (index < 0 || index >= this.staffList.size()) {
			System.err.println("\u001B[31mgetMember() : Index out of bounds\u001B[0m");
			return null;
		}
		return this.staffList.get(index);
	}

	/**
	 * Pays the staff
	 */
	public void payday() {
		for (StaffMember member : this.staffList) {
			System.out.println(member.toString());
			double payment = member.pay();
			if (payment == 0.0) {
				System.out.println("Thanks !");
			} else {
				System.out.println("\u001B[36mPaid : \u001B[0m" + payment);
			}
			System.out.println("-----------------------------------");
		}
	}
}