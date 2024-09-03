public class OrnamentedList extends BaseList{
    private char itemType;
    
    public char getItemType(){
      return itemType;
    }
    
    public void setItemType(char newItemType){
        if (newItemType > ' '){
            itemType = newItemType;
        }
    }
    
    public String get(int index){
        return itemType + " " + super.getItem(index);
    }
}