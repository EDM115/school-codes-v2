package order;
import java.util.ArrayList;

public class Pack implements Product, IPack {

    private ArrayList<Product> products;
 
   public Pack() { 
       products = new ArrayList<Product>();
   }

   public void add(Product product) {
    products.add(product);
   }
   
   public void remove(Product product) {
        products.remove(product);
   }

   public double getPrice() {
      double price = 0;

      for (Product product : products)
         price += product.getPrice();
      return price;
   }

   public String toString()   {
      String description = "Pack: ";
     for (Product p :  products) {
         description = description + p.toString() + " - ";
      }
      return description;
   }
}
