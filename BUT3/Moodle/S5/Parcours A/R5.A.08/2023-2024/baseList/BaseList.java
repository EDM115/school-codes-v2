import java.util.ArrayList;
public class BaseList{
  private ArrayList<String> items = new ArrayList<String>();
    
    public void addItem(String item){
        if (!items.contains(item)){
            items.add(item);
        }
    }
    public void addItem(String item, int position){
        if (!items.contains(item)){
            items.add(position, item);
        }
    }
    
   public void removeItem(String item){
        if (items.contains(item)){
            items.remove(items.indexOf(item));
        }
    }
    
       public int getNumberOfItems(){
        return items.size();
    }
    
    public String getItem(int index){
        if (index < items.size()){
            return items.get(index);
        }
        return null;
    }
}