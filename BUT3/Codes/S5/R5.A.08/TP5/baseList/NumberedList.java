/**
 * Subclass of BaseList that represents a numbered list.
 * 
 * @param listImpl the implementation of the list
 */
public class NumberedList extends BaseList {
  public NumberedList(ListImpl listImpl) {
    super(listImpl);
  }

  /**
   * Prints the items in the numbered list along with their corresponding indices.
   * Iterates through the list and prints each item with its index starting from 1.
   */
  public void printList() {
    for (int i = 0; i < getNumberOfItems(); i++) {
      System.out.println((i + 1) + ". " + getItem(i));
    }
  }
}
