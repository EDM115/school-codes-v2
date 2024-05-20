package order;
/**
 An article with a price and description.
 */
public class Article implements Product {
  
  private String description;
  private double price;
  
  public Article(String description, double price)   {
    this.description = description;
    this.price = price;
  }
  
  public double getPrice() {
    return price;
  }
  
  public String toString() {
    return description;
  }  
}
