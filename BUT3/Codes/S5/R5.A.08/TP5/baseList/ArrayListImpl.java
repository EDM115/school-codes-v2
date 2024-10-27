import java.util.ArrayList;

/**
 * Implementation of the ListImpl interface using an ArrayList to store items.
 * Provides methods to add an item if it does not already exist, remove an item if it exists,
 * get the total number of items, and retrieve an item by its index.
 */
public class ArrayListImpl implements ListImpl {
  private ArrayList<String> items = new ArrayList<>();

  /**
   * Adds a new item to the list if it is not already present.
   *
   * @param item the item to be added to the list
   */
  public void addItem(String item) {
    if (!items.contains(item)) {
      items.add(item);
    }
  }

  /**
   * Removes the specified item from the list if it exists.
   *
   * @param item the item to be removed from the list
   */
  public void removeItem(String item) {
    if (items.contains(item)) {
      items.remove(item);
    }
  }

  /**
   * Returns the total number of items in the list.
   *
   * @return the total number of items in the list
   */
  public int getNumberOfItems() {
    return items.size();
  }

  /**
   * Retrieves the item at the specified index from the list.
   *
   * @param index the index of the item to retrieve
   * @return the item at the specified index, or null if the index is out of bounds
   */
  public String getItem(int index) {
    if (index < items.size()) {
      return items.get(index);
    }

    return null;
  }
}
