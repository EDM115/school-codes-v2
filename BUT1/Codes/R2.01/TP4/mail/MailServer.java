package mail;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * MailServer
 * Represents a mail server
 */
public class MailServer {
	/**
	 * The list of mail items
	 */
	private ArrayList<MailItem> items;

	/**
	 * Constructor for objects of class MailServer
	 */
	public MailServer() {
		this.items = new ArrayList<MailItem>();
	}

	/**
	 * Add a mail item to the server
	 * @param item the mail item to add
	 */
	public void post(MailItem item) {
		this.items.add(item);
	}

	/**
	 * Get the number of mail items in the server for a given user
	 * @param who the user
	 * @return the number of mail items in the server for the user
	 */
	public int howManyMailItems(String who) {
		int count = 0;

		for (MailItem item : this.items) {
			if (item.getTo().equals(who)) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Get the next mail item for a given user, and remove it from the server
	 * @param who the user
	 * @return the next mail item for the user
	 */
	public MailItem getNextMailItem(String who) {
		MailItem item = null;
		boolean found = false;
		Iterator<MailItem> it = this.items.iterator();

		while (!found && it.hasNext() && item == null) {
			item = it.next();
			if (item.getTo().equals(who)) {
				it.remove();
				found = true;
			} else {
				item = null;
			}
		}

		return item;
	}

	/**
	 * Get the string representation of the server, used only for tests
	 * @return the string representation of the server
	 */
	public String toString() {
		String str = "MailServer(";

		for (MailItem item : this.items) {
			str += item.toString() + ", \n";
		}
		str += ")";

		return str;
	}
}