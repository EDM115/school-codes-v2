public class RunBaseList {
  public static void main(String[] arguments) {
    System.out.println("Creating a BaseList object.");
    ListImpl arrayListImpl = new ArrayListImpl();

    BaseList listOne = new BaseList(arrayListImpl) {
      @Override
      public void printList() {
        for (int i = 0; i < getNumberOfItems(); i++) {
          System.out.println(getItem(i));
        }
      }
    };

    System.out.println("Adding elements to the list.");
    listOne.addItem("One");
    listOne.addItem("Two");
    listOne.addItem("Three");
    listOne.addItem("Four");
    System.out.println();

    System.out.println("Creating an OrnamentedList object.");
    OrnamentedList listTwo = new OrnamentedList(arrayListImpl);
    listTwo.setItemType('+');
    System.out.println();

    System.out.println("Creating a NumberedList object.");
    NumberedList listThree = new NumberedList(arrayListImpl);
    System.out.println();

    System.out.println("Printing out first list (BaseList)");
    listOne.printList();
    System.out.println();

    System.out.println("Printing out second list (OrnamentedList)");
    listTwo.printList();
    System.out.println();

    System.out.println("Printing our third list (NumberedList)");
    listThree.printList();
  }
}
