/**
 * Vérifie si une chaîne de caractère est incluse dans une autre
 *
 * @author EDM115
 */
class SousChaine {
  void principal() {
    testEstSousChaine();
  }

  /**
   * teste si une chaîne est une sous-chaîne d'une autre
   *
   * @param mot chaîne de caractères
   * @param phrase chaîne de carectères
   * @return vrai ssi la première chaîne est présente dans la seconde
   */
  boolean estSousChaine(String mot, String phrase) {
    int len1 = mot.length();
    int len2 = phrase.length();
    boolean sousChaine = false;
    int i = 0;
    while (i < (len2 - len1 + 1) && !sousChaine) {
      int j = 0;
      while (j < len1 && mot.charAt(j) == phrase.charAt(i + j)) {
        j++;
      }
      if (j == len1) {
        sousChaine = true;
      }
      i++;
    }
    return sousChaine;
  }

  /** Teste la méthode estSousChaine() */
  void testEstSousChaine() {
    System.out.println();
    System.out.println("*** testEstSousChaine()");
    String mot1 = "ses";
    String phrase1 = "abcdsesdef";
    boolean result1 = true;
    String phrase2 = "abcdef";
    boolean result2 = false;
    String phrase3 = "abcdefse";
    boolean result3 = false;
    String phrase4 = "asbecsd";
    boolean result4 = false;
    testCasEstSousChaine(mot1, phrase1, result1);
    testCasEstSousChaine(mot1, phrase2, result2);
    testCasEstSousChaine(mot1, phrase3, result3);
    testCasEstSousChaine(mot1, phrase4, result4);
  }

  /**
   * teste un appel de estSousChaine
   *
   * @param mot la sous-chaîne
   * @param phrase la chaîne
   * @param result dit si mot est dans phrase
   */
  void testCasEstSousChaine(String mot, String phrase, boolean result) {
    // Arrange
    System.out.print("testCasEstSousChaine(" + mot + ", " + phrase + ") \t= " + result + "\t : ");
    // Act
    boolean resExec = estSousChaine(mot, phrase);
    // Assert
    if (resExec == result) {
      System.out.println("OK");
    } else {
      System.err.println("ERREUR");
    }
  }
}
