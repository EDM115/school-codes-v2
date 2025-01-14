import java.util.Date;

public class Loan {
  private double notional;
  private double outstanding;
  private int rating;
  private String type;
  private double unusedPercentage;
  private Date start;
  private Date expiry;
  private Date maturity;
  private static final int MILLIS_PER_DAY = 86400000;
  private static String TERM_LOAN = "TL"; //
  private static String REVOLVER = "RC";
  private static String RCTL = "RCTL";
  
  public Loan(String type, double notional, double outstanding, int rating, Date expiry, Date maturity) {
    this.type = type;
    this.notional = notional;
    this.outstanding = outstanding;
    this.rating = rating;
    this.expiry= expiry;
    this.maturity=maturity;
    if (RCTL.equals(type))
      this.maturity = maturity;
  } 
  
  public double calcCapital() {
    return riskAmount() * duration() ; //* RiskFactor.forRiskRating(rating);
  }
  
  private double calcUnusedRiskAmount() {
    return (notional - outstanding) * unusedPercentage;
  }
  
  private double duration() { 
    if (expiry == null)
      return ((maturity.getTime() - start.getTime()) / MILLIS_PER_DAY) / 365;
    else if (maturity == null)
      return ((expiry.getTime() - start.getTime()) / MILLIS_PER_DAY) / 365;
    else {
      long millisToExpiry = expiry.getTime() - start.getTime();
      long millisFromExpiryToMaturity = maturity.getTime() - expiry.getTime();
      double revolverDuration = (millisToExpiry / MILLIS_PER_DAY) / 365;
      double termDuration = (millisFromExpiryToMaturity / MILLIS_PER_DAY) / 365;
      return revolverDuration + termDuration;
    }
  }
  
  private double riskAmount() {
    if (unusedPercentage != 1.00)
      return outstanding + calcUnusedRiskAmount();
    else
      return outstanding;
  }
  
  public void setOutstanding(double newOutstanding) {
    outstanding = newOutstanding;
  }
  
  private void setUnusedPercentage() {
    if (expiry != null && maturity != null) {
      if (rating > 4) unusedPercentage = 0.95;
      else  unusedPercentage = 0.50;
    }
    else if (maturity != null) {unusedPercentage = 1.00;
    } else if (expiry != null) {
      if (rating > 4) unusedPercentage = 0.75;
      else unusedPercentage = 0.25;
    }
  }
}