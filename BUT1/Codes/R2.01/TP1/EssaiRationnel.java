/**
 * Tests the methods of the Rationnel class
 *
 * @author EDM115
 */
public class EssaiRationnel {

  /**
   * Main method
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    System.out.println("\t\u001B[33mTests Rationnel :\u001B[0m");

    System.out.println("");
    System.out.println("\u001B[36m*** Rationnel()\u001B[0m");
    int numerateur = 5;
    int denominateur = 2;
    Rationnel r = new Rationnel(numerateur, denominateur);
    System.out.println("Rationnel(" + numerateur + ", " + denominateur + ") = " + r.toString());
    int numerateur2 = 15;
    int denominateur2 = 3;
    Rationnel r2 = new Rationnel(numerateur2, denominateur2);
    System.out.println("Rationnel(" + numerateur2 + ", " + denominateur2 + ") = " + r2.toString());
    int numerateur3 = 0;
    int denominateur3 = 0;
    Rationnel r3 = new Rationnel(numerateur3, denominateur3);
    System.out.println("Rationnel(" + numerateur3 + ", " + denominateur3 + ") = " + r3.toString());
    int numerateur4 = -8;
    int denominateur4 = -4;
    Rationnel r4 = new Rationnel(numerateur4, denominateur4);
    System.out.println("Rationnel(" + numerateur4 + ", " + denominateur4 + ") = " + r4.toString());

    System.out.println("");
    System.out.println("\u001B[36m*** getNumerateur()\u001B[0m");
    System.out.println("5/2 -> getNumerateur() = " + r.getNumerateur());
    System.out.println("15/3 -> getNumerateur() = " + r2.getNumerateur());
    System.out.println("0/1 -> getNumerateur() = " + r3.getNumerateur());

    System.out.println("");
    System.out.println("\u001B[36m*** setNumerateur()\u001B[0m");
    r.setNumerateur(10);
    System.out.println("5/2 -> setNumerateur(10) = " + r.toString());
    r2.setNumerateur(0);
    System.out.println("15/3 -> setNumerateur(0) = " + r2.toString());

    System.out.println("");
    System.out.println("\u001B[36m*** getDenominateur()\u001B[0m");
    System.out.println("10/2 -> getDenominateur() = " + r.getDenominateur());
    System.out.println("0/3 -> getDenominateur() = " + r2.getDenominateur());
    System.out.println("0/1 -> getDenominateur() = " + r3.getDenominateur());

    r3.setNumerateur(6);
    r3.setDenominateur(7);

    System.out.println("");
    System.out.println("\u001B[36m*** setDenominateur()\u001B[0m");
    r.setDenominateur(16);
    System.out.println("10/2 -> setDenominateur(16) = " + r.toString());
    r2.setDenominateur(9);
    System.out.println("0/9 -> setDenominateur(0) = " + r2.toString());

    System.out.println("");
    System.out.println("\u001B[36m*** inverse()\u001B[0m");
    System.out.println("10/16 -> inverse() = " + r.inverse().toString());
    System.out.println("0/9 -> inverse() = " + r2.inverse().toString());
    System.out.println("6/7 -> inverse() = " + r3.inverse().toString());

    System.out.println("");
    System.out.println("\u001B[36m*** ajoute()\u001B[0m");
    System.out.println("10/16 + 8/4 = " + r.ajoute(r4).toString());
    System.out.println("0/9 + 6/7 = " + r2.ajoute(r3).toString());
    System.out.println("10/16 + null = " + r.ajoute(null));

    System.out.println("");
    System.out.println("\u001B[36m*** soustrait()\u001B[0m");
    System.out.println("10/16 - 8/4 = " + r.soustrait(r4).toString());
    System.out.println("0/9 - 6/7 = " + r2.soustrait(r3).toString());
    System.out.println("10/16 - null = " + r.soustrait(null));

    System.out.println("");
    System.out.println("\u001B[36m*** multiplie()\u001B[0m");
    System.out.println("10/16 * 8/4 = " + r.multiplie(r4).toString());
    System.out.println("0/9 * 6/7 = " + r2.multiplie(r3).toString());
    System.out.println("10/16 * null = " + r.multiplie(null));

    System.out.println("");
    System.out.println("\u001B[36m*** egale()\u001B[0m");
    System.out.println("10/16 == 8/4 = " + r.egale(r4));
    System.out.println("6/7 == 6/7 = " + r3.egale(r3));
    System.out.println("10/16 == null = " + r.egale(null));

    System.out.println("");
    System.out.println("\u001B[36m*** toString()\u001B[0m");
    System.out.println("10/16 -> toString() = " + r.toString());
    System.out.println("0/9 -> toString() = " + r2.toString());
    System.out.println("6/7 -> toString() = " + r3.toString());
    System.out.println("8/4 -> toString() = " + r4.toString());
  }
}
