/**
 * Calcule x^n, x un réel et n un entier
 *
 * @author EDM115
 */
class Exposant {
  void principal() {
    System.out.println("Calcule x^n, x un réel et n un entier");
    float x = SimpleInput.getFloat("Entrez x : ");
    int n = SimpleInput.getInt("Entrez n : ");
    float resultat = 0;
    boolean negatif = false;
    int nn = n;

    if (n == 0) {
      System.out.println(x + "^" + n + " = 1");
      return;
    }
    if (n < 0) {
      n = n * -1;
      negatif = true;
    }
    resultat = x;
    n--;
    while (n > 0) {
      resultat = resultat * x;
      n--;
    }
    if (negatif) {
      resultat = 1 / resultat;
    }
    System.out.println(x + "^" + nn + " = " + resultat);
  }
}
