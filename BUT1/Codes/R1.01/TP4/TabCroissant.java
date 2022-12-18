/**
 * Ce programme teste si un tableau est croissant
 *
 * @author EDM115
 */
class TabCroissant {
  void principal() {
    testEstCroissant();
  }

  /**
   * teste si les valeurs d'un tableau sont triées par ordre croissant
   *
   * @param t tableau d'entiers
   * @return vrai ssi les valeurs du tableau sont en ordre croissant
   */
  boolean estCroissant(int[] t) {
    int len = t.length;
    boolean croissant = true;
    int i = 1;
    int val = t[0];
    while (i < len && croissant) {
      if (t[i] < val) {
        croissant = false;
      }
      val = t[i];
      i++;
    }
    return croissant;
  }

  /** Teste la méthode estCroissant() */
  void testEstCroissant() {
    System.out.println();
    System.out.println("*** testEstCroissant()");
    int[] t1 = {1, 2, 3};
    int[] t2 = {2, 4, 3};
    int[] t3 = {-1, 0, -10};
    int[] t4 = {-5, -3, -1};
    testCasEstCroissant(t1, true);
    testCasEstCroissant(t2, false);
    testCasEstCroissant(t3, false);
    testCasEstCroissant(t4, true);
  }

  /**
   * teste un appel de estCroissant
   *
   * @param t tableau a vérifier
   * @param result resultat attendu
   */
  void testCasEstCroissant(int[] t, boolean result) {
    // Arrange
    System.out.print("estCroissant(" + affTab(t) + ") \t= " + result + "\t : ");
    // Act
    boolean resExec = estCroissant(t);
    // Assert
    if (resExec == result) {
      System.out.println("OK");
    } else {
      System.err.println("ERREUR");
    }
  }

  String affTab(int[] t) {
    boolean nullTab = false;
    int len = t.length;
    if (len == 0) {
      nullTab = true;
    }
    int i = 0;
    int value;
    String tableau = "[";
    if (!nullTab) {
      while (i < len) {
        value = t[i];
        tableau = tableau + value;
        if (i != len - 1) {
          tableau = tableau + ", ";
        }
        i++;
      }
    }
    tableau = tableau + "]";
    return tableau;
  }
}
