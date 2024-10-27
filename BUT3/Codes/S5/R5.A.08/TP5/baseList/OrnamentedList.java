/**
 * Represents an Ornamented List that extends the functionality of the BaseList.
 * Allows setting a specific item type character for ornamentation and prints the list with the ornamented items.
 * 
 * @param listImpl the ListImpl object to be used for the OrnamentedList
 */
public class OrnamentedList extends BaseList {
  private char itemType;

  public OrnamentedList(ListImpl listImpl) {
    super(listImpl);
    this.itemType = '+';
  }

  /**
   * Sets the item type character for ornamentation if the new item type is greater than a space character.
   *
   * @param newItemType the new item type character to be set
   */
  public void setItemType(char newItemType) {
    if (newItemType > ' ') {
      itemType = newItemType;
    }
  }

  /**
   * Prints the list items with each item prefixed by the item type character.
   */
  public void printList() {
    for (int i = 0; i < getNumberOfItems(); i++) {
      System.out.println(itemType + " " + getItem(i));
    }
  }
}
