package order;

public final class DiscountedArticle extends Article {
  
  private final double discount;  //un montant a deduire du prix
  
  public DiscountedArticle(String description, double price, double discount){
    super(description, price);
    this.discount = discount;
  }
  
  public double getPrice(){
    return super.getPrice()-discount;
  }
  
}
