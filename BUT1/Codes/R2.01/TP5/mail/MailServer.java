package mail;

import java.util.ArrayList;
import java.util.Iterator;

/** MailServer Represents a mail server */
public class MailServer {
  /** The list of mail items */
  private ArrayList<MailItem> items;

  /** The anti-spam filter */
  private AntiSpam antiSpam;

  /** The list of spams */
  private ArrayList<MailItem> spams;

  /** The list of spammers */
  private ArrayList<String> spammers;

  /**
   * Constructor for objects of class MailServer
   *
   * @param filters the list of filters
   */
  public MailServer(ArrayList<String> filters) {
    this.items = new ArrayList<MailItem>();
    this.antiSpam = new AntiSpam(filters);
    this.spams = new ArrayList<MailItem>();
    this.spammers = new ArrayList<String>();
  }

  /**
   * Add a mail item to the server
   *
   * @param item the mail item to add
   */
  public void post(MailItem item) {
    if (item == null) {
      System.err.println("\u001B[31mpost : Le paramètre ne peut pas être null\u001B[0m");
    } else if (this.antiSpam.scan(item.getMessage())) {
      System.out.println("post() : Message rejeté (spam)");
      String from = item.getFrom();
      String to = item.getTo();
      String message = item.getMessage();
      message = "[SPAM] " + message;
      MailItem spam = new MailItem(from, to, message);
      this.spams.add(spam);
      if (!isSpammer(from)) {
        this.spammers.add(from);
      }
    } else {
      if (isSpammer(item.getFrom())) {
        String from = item.getFrom();
        String to = item.getTo();
        String message = item.getMessage();
        message = "[ATTENTION SPAMMEUR] " + message;
        MailItem spam = new MailItem(from, to, message);
        this.items.add(spam);
      } else {
        this.items.add(item);
      }
    }
  }

  /**
   * Check if a user is a spammer
   *
   * @param who the user
   * @return true if the user is a spammer, false otherwise
   */
  private boolean isSpammer(String who) {
    boolean isSpammer = false;

    if (who == null) {
      System.err.println("\u001B[31misScammer : Le paramètre ne peut pas être null\u001B[0m");
    } else {
      for (String spammer : this.spammers) {
        if (spammer.equals(who)) {
          isSpammer = true;
        }
      }
    }

    return isSpammer;
  }

  /**
   * Get the number of mail items in the server for a given user
   *
   * @param who the user
   * @return the number of mail items in the server for the user
   */
  public int howManyMailItems(String who) {
    int count = 0;

    if (who == null) {
      System.err.println(
          "\u001B[31mhowManyMailItems : Le paramètre ne peut pas être null\u001B[0m");
    } else {
      for (MailItem item : this.items) {
        if (item.getTo().equals(who)) {
          count++;
        }
      }
    }

    return count;
  }

  /**
   * Get the next mail item for a given user, and remove it from the server
   *
   * @param who the user
   * @return the next mail item for the user
   */
  public MailItem getNextMailItem(String who) {
    MailItem item = null;

    if (who == null) {
      System.err.println("\u001B[31mgetNextMailItem : Le paramètre ne peut pas être null\u001B[0m");
    } else {
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
    }

    return item;
  }

  /**
   * Get the string representation of the server, used only for tests
   *
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
