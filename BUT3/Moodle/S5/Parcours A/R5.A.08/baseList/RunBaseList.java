public class RunBaseList{
    public static void main(String [] arguments){
        
        System.out.println("Creating a BaseList object.");
        BaseList listOne = new BaseList();
       
        
        System.out.println("Adding elements to the list.");
        listOne.addItem("One");
        listOne.addItem("Two");
        listOne.addItem("Three");
        listOne.addItem("Four");
        System.out.println();
        
        System.out.println("Creating an OrnamentedList object.");
        OrnamentedList listTwo = new OrnamentedList();
    
        listTwo.setItemType('+');
        System.out.println();
        
        System.out.println("Creating an NumberedList object.");
        NumberedList listThree = new NumberedList();
        System.out.println();
        
        System.out.println("Printing out first list (BaseList)");
        for (int i = 0; i < listOne.getNumberOfItems(); i++){
            System.out.println("\t" + listOne.getItem(i));
        }
        System.out.println();
        
        System.out.println("Printing out second list (OrnamentedList)");
        for (int i = 0; i < listTwo.getNumberOfItems(); i++){
            System.out.println("\t" + listTwo.getItem(i));
        }
        System.out.println();
        
        System.out.println("Printing our third list (NumberedList)");
        for (int i = 0; i < listThree.getNumberOfItems(); i++){
            System.out.println("\t" + listThree.getItem(i));
        }
    }
}