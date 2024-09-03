import order.*;
/**
   A program that tests the order classes.
*/
public class OrderTester {

   public static void main(String[] args) {

      Order order = new Order ();
   
   //   OrderFormatterBis formatter = new OrderFormatter<Product>();

      // create some items

      Article hammer = new Article("Hammer", 19.95);
      Article nails = new Article("Assorted nails", 9.95);
      DiscountedArticle pen = new DiscountedArticle("discounted pen", 0.80, 0.20);
      DiscountedArticle ruler = new DiscountedArticle("discounted ruler", 1.80, 0.10);
      

      Pack pack = new Pack();
      pack.add(hammer);
      pack.add(nails);

      order.addItem(pack);
      order.addItem(pen);
      order.addItem(ruler);

      System.out.println(order.format());
      
   }

   /*
        O R D E R


Pack: Hammer - Assorted nails - : $29,90
discounted pen: $0,60
discounted ruler: $1,70


TOTAL DUE: $32,20
*/
      
   

      
}
