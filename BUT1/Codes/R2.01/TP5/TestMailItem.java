import mail.MailItem;

/**
 * TestMailItem
 * Tests the class MailItem
 */
public class TestMailItem {
	/**
	 * Main method
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("\t\u001B[33mTests MailItem :\u001B[0m");

		System.out.println("");
		System.out.println("\u001B[36m*** MailItem()\u001B[0m");
		MailItem item1 = new MailItem("Nathan", "Allan", "Eh vraiment là");
		MailItem item2 = new MailItem("Allan", "Nathan", "je suis fatigué là");
		System.out.println("\"Nathan\", \"Allan\", \"Eh vraiment là\" -> MailItem() = \n" + item1);
		System.out.println("\"Allan\", \"Nathan\", \"je suis fatigué là\" -> MailItem() = \n" + item2);

		System.out.println("");
		System.out.println("\u001B[36m*** getFrom()\u001B[0m");
		System.out.println("\"Nathan\" -> getFrom() = " + item1.getFrom());
		System.out.println("\"Allan\" -> getFrom() = " + item2.getFrom());

		System.out.println("");
		System.out.println("\u001B[36m*** getTo()\u001B[0m");
		System.out.println("\"Allan\" -> getTo() = " + item1.getTo());
		System.out.println("\"Nathan\" -> getTo() = " + item2.getTo());

		System.out.println("");
		System.out.println("\u001B[36m*** getMessage()\u001B[0m");
		System.out.println("\"Eh vraiment là\" -> getMessage() = " + item1.getMessage());
		System.out.println("\"je suis fatigué là\" -> getMessage() = " + item2.getMessage());

		System.out.println("");
		System.out.println("\u001B[36m*** print()\u001B[0m");
		System.out.println("\"Nathan\", \"Allan\", \"Eh vraiment là\" -> print() =");
		item1.print();
		System.out.println("\"Allan\", \"Nathan\", \"je suis fatigué là\" -> print() =");
		item2.print();
	}

	/**
	 * Constructor, useless
	 */
	public TestMailItem() {
	}
}