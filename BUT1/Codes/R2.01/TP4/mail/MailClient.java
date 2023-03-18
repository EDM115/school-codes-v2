package mail;

import mail.MailServer;

/**
 * InnerMailClient
 */
public class MailClient {
	private String user;
	private MailServer server;

	public MailClient(MailServer server, String user) {
		if (server == null || user == null) {
			System.err.println("\u001B[31mMailClient : Les paramètres ne peuvent pas être null\u001B[0m");
			this.user = "";
			this.server = null;
		} else {
			this.user = user;
			this.server = server;
		}
	}

	public MailItem getNextMailItem() {
		return this.server.getNextMailItem(this.user);
	}

	public void printNextMailItem() {
		MailItem item = this.getNextMailItem();

		if (item != null) {
			item.print();
		} else {
			System.out.println("Pas de message");
		}
	}

	public void sendMailItem(String to, String message) {
		if (to == null || message == null) {
			System.err.println("\u001B[31msendMailItem : Les paramètres ne peuvent pas être null\u001B[0m");
		} else {
			MailItem item = new MailItem(this.user, to, message);
			this.server.post(item);
		}
	}
}
