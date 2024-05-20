package order;
import java.util.ArrayList;

/**
   An order for a purchase, consisting of products.
*/
public class Order {

  private ArrayList<Product> products;
  private int status;
 
  public Order()   {
      status = 0;  // pending
      products = new ArrayList<Product>();
   }

   public void addItem(Product product) {
     products.add(product);
   }

   public void removeItem(Product product) {
     products.remove(product);
   }
   
   public ArrayList<Product> getProducts() {
     return products;
   }
   
   public int getStatus() {
     return status;
   }
   
   // change th order status to the next one
   public void nextState() {
     switch(status) {
       case 0 :
         status = 1;
         break;
       case 1 :
         status = 2;
         break;
       case 2 :
         System.out.println("the order is delevered");
         break;
       default :
         System.out.println("undefined status");
     }
   }
          
   
   public String format() {
      double total = 0;
      String r = "     O R D E R\n\n\n" ;
     for( Product p : products) {
        total += p.getPrice();
        r= r+ String.format( "%s: $%.2f\n",p.toString(),p.getPrice());
      }
      r = r+   String.format("\n\nTOTAL DUE: $%.2f\n", total);
      return r;
   }

 }
