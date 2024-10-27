/**
 * BaseList class represents a base abstract class for list implementations.
 * It provides methods to add, remove, and retrieve items from the list using a ListImpl object.
 * Subclasses must implement the printList method to display the list contents.
 */
public abstract class BaseList {
  protected ListImpl listImpl;

  public BaseList(ListImpl listImpl) {
    this.listImpl = listImpl;
  }

  public void addItem(String item) {
    listImpl.addItem(item);
  }

  public void removeItem(String item) {
    listImpl.removeItem(item);
  }

  public int getNumberOfItems() {
    return listImpl.getNumberOfItems();
  }

  public String getItem(int index) {
    return listImpl.getItem(index);
  }

  public abstract void printList();
}
