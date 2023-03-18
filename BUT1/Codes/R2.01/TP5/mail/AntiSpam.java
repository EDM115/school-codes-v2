package mail;

import java.util.ArrayList;

/** AntiSpam Represents an anti-spam filter */
public class AntiSpam {
  /** The list of filters */
  private ArrayList<String> filters;

  /**
   * Constructor for objects of class AntiSpam
   *
   * @param filters the list of filters
   */
  public AntiSpam(ArrayList<String> filters) {
    if (filters == null) {
      System.err.println("\u001B[31mAntiSpam : Le paramètre ne peut pas être null\u001B[0m");
      this.filters = new ArrayList<String>();
    } else {
      this.filters = filters;
    }
  }

  /**
   * Add a filter to the anti-spam ArrayList
   *
   * @param f the filter to add
   */
  public void add(String f) {
    if (f == null) {
      System.err.println("\u001B[31madd : Le paramètre ne peut pas être null\u001B[0m");
    } else {
      this.filters.add(f);
    }
  }

  /**
   * Scan a message for any spam in its content
   *
   * @param message the message to scan
   * @return true if the message contains spam, false otherwise
   */
  public boolean scan(String message) {
    boolean spam = false;

    if (message == null) {
      System.err.println("\u001B[31mscan : Le paramètre ne peut pas être null\u001B[0m");
    } else {
      for (String filter : this.filters) {
        if (message.contains(filter)) {
          spam = true;
        }
      }
    }

    return spam;
  }

  public String toString() {
    String str = "AntiSpam : [";

    for (String filter : this.filters) {
      str += filter + ", ";
    }
    str += "]";

    return str;
  }
}
