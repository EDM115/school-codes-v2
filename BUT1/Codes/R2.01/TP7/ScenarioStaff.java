import staff.*;

/**
 * ScenarioStaff class, used to test classes of the staff package
 */
public class ScenarioStaff {
	/**
	 * Main method
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("");
		System.out.println("\t\u001B[33mScenarioStaff :\u001B[0m");

		System.out.println("");
		System.out.println("\t\u001B[33mVolunteer :\u001B[0m");

		System.out.println("");
		System.out.println("\u001B[36m*** Volunteer()\u001B[0m");

		Volunteer volunteer1 = new Volunteer("John", "1 rue de la paix", "0123456789");
		System.out.println("Volunteer(\"John\", \"1 rue de la paix\", \"0123456789\") -> \n" + volunteer1);
		Volunteer volunteer2 = new Volunteer("Jane", "2 rue de la paix", "0123456789");
		System.out.println("Volunteer(\"Jane\", \"2 rue de la paix\", \"0123456789\") -> \n" + volunteer2);
		Volunteer volunteer3 = new Volunteer("Jack", "3 rue de la paix", "0123456789");
		System.out.println("Volunteer(\"Jack\", \"3 rue de la paix\", \"0123456789\") -> \n" + volunteer3);

		System.out.println("");
		System.out.println("\u001B[36m*** toString()\u001B[0m");

		System.out.println("volunteer1.toString() -> \n" + volunteer1.toString());
		System.out.println("volunteer2.toString() -> \n" + volunteer2.toString());
		System.out.println("volunteer3.toString() -> \n" + volunteer3.toString());

		System.out.println("");
		System.out.println("\u001B[36m*** pay()\u001B[0m");

		System.out.println("volunteer1.pay() -> " + volunteer1.pay());

		System.out.println("");
		System.out.println("\t\u001B[33mEmployee :\u001B[0m");

		System.out.println("");
		System.out.println("\u001B[36m*** Employee()\u001B[0m");

		Employee employee1 = new Employee("Michel", "1 avenue de la Méduse", "0662369800", "123456789", 10.0);
		System.out.println("Employee(\"Michel\", \"1 avenue de la Méduse\", \"0662369800\", \"123456789\", 10.0) -> \n" + employee1);
		Employee employee2 = new Employee("Marie", "2 avenue de la Méduse", "0662370800", "568842255", 8.0);
		System.out.println("Employee(\"Marie\", \"2 avenue de la Méduse\", \"0662370800\", \"568842255\", 10.0) -> \n" + employee2);
		Employee employee3 = new Employee("Maurice", "3 avenue de la Méduse", "0662371800", "568657255", 9.7);
		System.out.println("Employee(\"Maurice\", \"3 avenue de la Méduse\", \"0662371800\", \"568657255\", 10.0) -> \n" + employee3);

		System.out.println("");
		System.out.println("\u001B[36m*** toString()\u001B[0m");

		System.out.println("employee1.toString() -> \n" + employee1.toString());
		System.out.println("employee2.toString() -> \n" + employee2.toString());
		System.out.println("employee3.toString() -> \n" + employee3.toString());

		System.out.println("");
		System.out.println("\u001B[36m*** pay()\u001B[0m");

		System.out.println("employee1.pay() -> " + employee1.pay());

		System.out.println("");
		System.out.println("\t\u001B[33mExecutive :\u001B[0m");

		System.out.println("");
		System.out.println("\u001B[36m*** Executive()\u001B[0m");

		Executive executive1 = new Executive("Jean", "1 avenue de la République", "0668956100", "9876543210", 15.0);
		System.out.println("Executive(\"Jean\", \"1 avenue de la République\", \"0668956100\", \"9876543210\", 15.0) -> \n" + executive1);
		Executive executive2 = new Executive("Jeanne", "2 avenue de la République", "0668956200", "9876543211", 18.0);
		System.out.println("Executive(\"Jeanne\", \"2 avenue de la République\", \"0668956200\", \"9876543211\", 18.0) -> \n" + executive2);
		Executive executive3 = new Executive("Jacques", "3 avenue de la République", "0668956300", "9876543212", 16.0);
		System.out.println("Executive(\"Jacques\", \"3 avenue de la République\", \"0668956300\", \"9876543212\", 16.0) -> \n" + executive3);

		System.out.println("");
		System.out.println("\u001B[36m*** toString()\u001B[0m");

		System.out.println("executive1.toString() -> \n" + executive1.toString());
		System.out.println("executive2.toString() -> \n" + executive2.toString());
		System.out.println("executive3.toString() -> \n" + executive3.toString());

		System.out.println("");
		System.out.println("\u001B[36m*** awardBonus()\u001B[0m");

		executive2.awardBonus(1000.0);
		System.out.println("executive2.awardBonus(1000.0) -> \n" + executive2.toString());

		System.out.println("");
		System.out.println("\u001B[36m*** pay()\u001B[0m");

		System.out.println("executive1.pay() -> " + executive1.pay());

		System.out.println("");
		System.out.println("\t\u001B[33mHourly :\u001B[0m");

		System.out.println("");
		System.out.println("\u001B[36m*** Hourly()\u001B[0m");

		Hourly hourly1 = new Hourly("Pierre", "1 avenue de la Liberté", "0668956100", "9876543210", 15.0);
		System.out.println("Hourly(\"Pierre\", \"1 avenue de la Liberté\", \"0668956100\", \"9876543210\", 15.0) -> \n" + hourly1);
		Hourly hourly2 = new Hourly("Pierrette", "2 avenue de la Liberté", "0668956200", "9876543211", 18.0);
		System.out.println("Hourly(\"Pierrette\", \"2 avenue de la Liberté\", \"0668956200\", \"9876543211\", 18.0) -> \n" + hourly2);
		Hourly hourly3 = new Hourly("Pierre-Louis", "3 avenue de la Liberté", "0668956300", "9876543212", 16.0);
		System.out.println("Hourly(\"Pierre-Louis\", \"3 avenue de la Liberté\", \"0668956300\", \"9876543212\", 16.0) -> \n" + hourly3);

		System.out.println("");
		System.out.println("\u001B[36m*** toString()\u001B[0m");

		System.out.println("hourly1.toString() -> \n" + hourly1.toString());
		System.out.println("hourly2.toString() -> \n" + hourly2.toString());
		System.out.println("hourly3.toString() -> \n" + hourly3.toString());
		
		System.out.println("");
		System.out.println("\u001B[36m*** addHours()\u001B[0m");

		hourly1.addHours(10);
		System.out.println("hourly1.addHours(100) -> \n" + hourly1.toString());
		hourly2.addHours(-8);
		System.out.println("hourly2.addHours(-8) -> \n" + hourly2.toString());
		hourly3.addHours(0);
		System.out.println("hourly3.addHours(80) -> \n" + hourly3.toString());

		System.out.println("");
		System.out.println("\u001B[36m*** pay()\u001B[0m");

		System.out.println("hourly1.pay() -> " + hourly1.pay());

		System.out.println("");
		System.out.println("\t\u001B[33mStaff :\u001B[0m");

		System.out.println("");
		System.out.println("\u001B[36m*** Staff()\u001B[0m");

		Staff staff = new Staff();
		System.out.println("Staff() -> " + staff);

		System.out.println("");
		System.out.println("\u001B[36m*** addNewMember()\u001B[0m");

		staff.addNewMember(volunteer1);
		staff.addNewMember(volunteer2);
		staff.addNewMember(volunteer3);
		staff.addNewMember(employee1);
		staff.addNewMember(employee2);
		staff.addNewMember(employee3);
		staff.addNewMember(executive1);
		staff.addNewMember(executive2);
		staff.addNewMember(executive3);
		staff.addNewMember(hourly1);
		staff.addNewMember(hourly2);
		staff.addNewMember(hourly3);

		System.out.println("");
		System.out.println("\u001B[36m*** getMember()\u001B[0m");

		System.out.println("staff.getMember(0) -> \n" + staff.getMember(0));
		System.out.println("staff.getMember(5) -> \n" + staff.getMember(5));
		System.out.println("staff.getMember(-1) -> \n" + staff.getMember(-1));

		System.out.println("");
		System.out.println("\u001B[36m*** payday()\u001B[0m");

		staff.payday();
	}

	/**
	* Useless constructor
	*/
	public ScenarioStaff() {}
}