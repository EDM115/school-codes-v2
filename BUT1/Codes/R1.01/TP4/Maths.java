/**
 * Ce programme calcule la factorielle et la combinaison
 *
 * @author EDM115
 */
class Maths {
  void principal() {
    testFactoriel();
    testCombinaison();
  }

  /**
   * calcul de la factorielle du paramètre
   *
   * @param n valeur de la factorielle à calculer
   * @return factoriel de n
   */
  double factoriel(int n) {
    if (n < 0) {
      System.out.println("Erreur, factoriel de nombre négatif");
      return -1;
    } else if (n < 2) {
      return 1;
    }
    int i = 0;
    double f = 1;
    while (i < n) {
      f = f * (n - i);
      i++;
    }
    return f;
  }

  /** Teste la méthode factoriel() */
  void testFactoriel() {
    System.out.println();
    System.out.println("*** testFactoriel()");
    testCasFactoriel(5, 120);
    testCasFactoriel(0, 1);
    testCasFactoriel(1, 1);
    testCasFactoriel(2, 2);
  }

  /**
   * teste un appel de factoriel
   *
   * @param n valeur de la factorielle à calculer
   * @param result resultat attendu
   */
  void testCasFactoriel(int n, double result) {
    // Arrange
    System.out.print("factoriel(" + n + ") \t= " + result + "\t : ");
    // Act
    double resExec = factoriel(n);
    // Assert
    if (resExec == result) {
      System.out.println("OK");
    } else {
      System.err.println("ERREUR");
    }
  }

  /**
   * calcul de la combinaison k parmi n
   *
   * @param n cardinalité de l'ensemble
   * @param k nombre d'éléments dans n avec k<=n
   * @return nombre de combinaisons de k parmi n
   */
  double combinaison(int n, int k) {
    double c, num, deno;
    num = factoriel(n);
    deno = factoriel(k) * factoriel(n - k);
    c = num / deno;
    return c;
  }

  /** Teste la méthode combinaison() */
  void testCombinaison() {
    System.out.println();
    System.out.println("*** testCombinaison()");
    testCasCombinaison(5, 3, 10);
    testCasCombinaison(4, 2, 6);
    testCasCombinaison(5, 0, 1);
    testCasCombinaison(7, 6, 7);
    testCasCombinaison(25, 24, 24.999999999999996);
  }

  /**
   * teste un appel de combinaison
   *
   * @param n nombre d'élements dans l'ensemble
   * @param k nombre d'élements dans un sous-ensemble de n
   * @param result resultat attendu
   */
  void testCasCombinaison(int n, int k, double result) {
    // Arrange
    System.out.print("combinaison(" + n + ", " + k + ") \t= " + result + "\t : ");
    // Act
    double resExec = combinaison(n, k);
    // Assert
    if (resExec == result) {
      System.out.println("OK");
    } else {
      System.err.println("ERREUR");
    }
  }
}
