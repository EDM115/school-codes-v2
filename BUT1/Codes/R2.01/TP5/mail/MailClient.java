package mail;

import java.util.ArrayList;

/** MailClient Represents a mail client */
public class MailClient {
  /** The name of the client */
  private String user;

  /** The server */
  private MailServer server;

  /** The list of mail items */
  private ArrayList<MailItem> mails;

  /**
   * Constructor for objects of class MailClient
   *
   * @param server the server
   * @param user the name of the client
   */
  public MailClient(MailServer server, String user) {
    if (server == null || user == null) {
      System.err.println("\u001B[31mMailClient : Les paramètres ne peuvent pas être null\u001B[0m");
      this.user = "";
      this.server = null;
      this.mails = new ArrayList<MailItem>();
    } else {
      this.user = user;
      this.server = server;
      this.mails = new ArrayList<MailItem>();
    }
  }

  /**
   * Getter for the name of the client
   *
   * @return the name of the client
   */
  public MailItem getNextMailItem() {
    MailItem item = this.server.getNextMailItem(this.user);

    mails.add(item);

    return item;
  }

  /** Print the next mail item */
  public void printNextMailItem() {
    MailItem item = this.getNextMailItem();

    if (item != null) {
      item.print();
    } else {
      System.out.println("printNextMailItem() : Pas de nouveau message");
    }
  }

  /**
   * Send a mail item
   *
   * @param to the recipient of the message
   * @param message the message
   */
  public void sendMailItem(String to, String message) {
    if (to == null || message == null) {
      System.err.println(
          "\u001B[31msendMailItem : Les paramètres ne peuvent pas être null\u001B[0m");
    } else {
      MailItem item = new MailItem(this.user, to, message);
      this.server.post(item);
    }
  }
}
