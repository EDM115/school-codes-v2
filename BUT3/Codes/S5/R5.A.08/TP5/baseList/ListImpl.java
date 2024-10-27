/**
 * Interface for a list implementation.
 * Defines methods to add and remove items, get the number of items, and retrieve an item by index.
 */
public interface ListImpl {
  void addItem(String item);

  void removeItem(String item);

  int getNumberOfItems();

  String getItem(int index);
}
