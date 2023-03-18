package mail;

/** MailItem Represents a mail item */
public class MailItem {
  /** The sender of the message */
  private String from;

  /** The recipient of the message */
  private String to;

  /** The message */
  private String message;

  /**
   * Constructor for objects of class MailItem
   *
   * @param from the sender of the message
   * @param to the recipient of the message
   * @param message the message
   */
  public MailItem(String from, String to, String message) {
    if (from == null || to == null || message == null) {
      System.err.println("\u001B[31mMailItem : Les paramètres ne peuvent pas être null\u001B[0m");
      this.from = "";
      this.to = "";
      this.message = "";
    } else {
      this.from = from;
      this.to = to;
      this.message = message;
    }
  }

  /**
   * Getter for the sender of the message
   *
   * @return the sender of the message
   */
  public String getFrom() {
    return this.from;
  }

  /**
   * Getter for the recipient of the message
   *
   * @return the recipient of the message
   */
  public String getTo() {
    return this.to;
  }

  /**
   * Getter for the message
   *
   * @return the message
   */
  public String getMessage() {
    return this.message;
  }

  /** Prints the mail item in a readable format in the console */
  public void print() {
    System.out.println(
        "From : " + this.from + "\nTo : " + this.to + "\nMessage : " + this.message + "\n");
  }

  /**
   * Get the string representation of the mail item (tests purpose only)
   *
   * @return the string representation of the mail item
   */
  public String toString() {
    String str = "";

    if (this.from == null || this.to == null || this.message == null) {
      System.err.println("\u001B[31mMailItem.toString : Une des variables est null\u001B[0m");
    } else {
      str = "From : " + this.from + " To : " + this.to + " Message : " + this.message + " ";
    }

    return str;
  }
}
