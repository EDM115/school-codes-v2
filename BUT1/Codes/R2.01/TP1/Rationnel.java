/**
 * A class that creates an object to replicate the behavior of rational numbers
 *
 * @author EDM115
 */
public class Rationnel {

  /** The numerator of the rational number */
  private int numerateur;

  /** The denominator of the rational number */
  private int denominateur;

  /**
   * The constructor of the class
   *
   * @param numerateur The numerator of the rational number
   * @param denominateur The denominator of the rational number
   */
  public Rationnel(int numerateur, int denominateur) {
    if (denominateur == 0) {
      this.numerateur = numerateur;
      this.denominateur = 1;
    } else if (denominateur < 0) {
      this.numerateur = -numerateur;
      this.denominateur = -denominateur;
    } else {
      this.numerateur = numerateur;
      this.denominateur = denominateur;
    }
  }

  /**
   * The getter of the numerator
   *
   * @return The numerator of the rational number
   */
  public int getNumerateur() {
    return this.numerateur;
  }

  /**
   * The setter of the numerator
   *
   * @param numerateur The numerator of the rational number
   */
  public void setNumerateur(int numerateur) {
    this.numerateur = numerateur;
  }

  /**
   * The getter of the denominator
   *
   * @return The denominator of the rational number
   */
  public int getDenominateur() {
    return this.denominateur;
  }

  /**
   * The setter of the denominator
   *
   * @param denominateur The denominator of the rational number
   */
  public void setDenominateur(int denominateur) {
    if (denominateur == 0) {
      this.denominateur = 1;
    } else if (denominateur < 0) {
      this.numerateur = -this.numerateur;
      this.denominateur = -denominateur;
    } else {
      this.denominateur = denominateur;
    }
  }

  /**
   * Returns the inverse of the rational number
   *
   * @return The inverse of the rational number
   */
  public Rationnel inverse() {
    Rationnel r = new Rationnel(this.denominateur, this.numerateur);

    return r;
  }

  /**
   * Adds a rational number to the current one
   *
   * @param r The rational number to add
   * @return The sum of the two rational numbers
   */
  public Rationnel ajoute(Rationnel r) {
    Rationnel r2 = null;

    if (r != null) {
      int newNumerateur =
          this.numerateur * r.getDenominateur() + this.denominateur * r.getNumerateur();
      int newDenominateur = this.denominateur * r.getDenominateur();
      r2 = new Rationnel(newNumerateur, newDenominateur);
      r2.reduit();
    }

    return r2;
  }

  /**
   * Subtracts a rational number from the current one
   *
   * @param r The rational number to subtract
   * @return The difference of the two rational numbers
   */
  public Rationnel soustrait(Rationnel r) {
    Rationnel r2 = null;

    if (r != null) {
      int newNumerateur =
          this.numerateur * r.getDenominateur() - this.denominateur * r.getNumerateur();
      int newDenominateur = this.denominateur * r.getDenominateur();
      r2 = new Rationnel(newNumerateur, newDenominateur);
      r2.reduit();
    }

    return r2;
  }

  /**
   * Multiplies a rational number by the current one
   *
   * @param r The rational number to multiply
   * @return The product of the two rational numbers
   */
  public Rationnel multiplie(Rationnel r) {
    Rationnel r2 = null;

    if (r != null) {
      int newNumerateur = this.numerateur * r.getNumerateur();
      int newDenominateur = this.denominateur * r.getDenominateur();
      r2 = new Rationnel(newNumerateur, newDenominateur);
      r2.reduit();
    }

    return r2;
  }

  /**
   * Checks if the current rational number is equal to another one
   *
   * @param r The rational number to compare
   * @return True if the two rational numbers are equal, false otherwise
   */
  public Boolean egale(Rationnel r) {
    Boolean equal = null;

    if (r != null) {
      equal =
          ((this.numerateur == r.getNumerateur()) && (this.denominateur == r.getDenominateur()));
    }

    return equal;
  }

  /** Turns a rational number into a readable string */
  public String toString() {
    String fraction;

    try {
      fraction = this.numerateur + "/" + this.denominateur;
    } catch (NullPointerException e) {
      fraction = "Un des deux paramètres est null";
    }

    return fraction;
  }

  /**
   * Calculates the greatest common divisor of two integers
   *
   * @param numerateur The numerator of the rational number
   * @param denominateur The denominator of the rational number
   * @return The greatest common divisor of the two integers
   */
  private int pgcd(int numerateur, int denominateur) {
    int pgcd = 0;

    if (denominateur == 0) {
      pgcd = numerateur;
    } else {
      pgcd = pgcd(denominateur, numerateur % denominateur);
    }

    return pgcd;
  }

  /** Reduces the rational number to its simplest form */
  private void reduit() {
    int pgcd = pgcd(this.numerateur, this.denominateur);

    this.numerateur /= pgcd;
    this.denominateur /= pgcd;
  }
}
