/**
 * Ce programme teste la divisibilité de deux entiers
 *
 * @author EDM115
 */
class Divisibilite {
  void principal() {
    testEstDiviseur();
    testEstParfait();
    quatreNbParfaits();
  }

  /**
   * teste la divisibilité de deux entiers
   *
   * @param p entier positif à tester pour la divisibilité
   * @param q diviseur strictement positif
   * @return vrai ssi q divise p
   */
  boolean estDiviseur(int p, int q) {
    if (q == 0) {
      return false;
    }
    boolean div = (p % q) == 0;
    return div;
  }

  /** Teste la méthode estDiviseur() */
  void testEstDiviseur() {
    System.out.println();
    System.out.println("*** testEstDiviseur()");
    testCasEstDiviseur(1, 0, false);
    testCasEstDiviseur(1, 1, true);
    testCasEstDiviseur(3, 2, false);
    testCasEstDiviseur(-12, 4, true);
  }

  /**
   * teste un appel de estDiviseur
   *
   * @param p nombre divisé
   * @param q diviseur
   * @param result resultat attendu
   */
  void testCasEstDiviseur(int p, int q, boolean result) {
    // Arrange
    System.out.print("estDiviseur(" + p + ", " + q + ") \t= " + result + "\t : ");
    // Act
    boolean resExec = estDiviseur(p, q);
    // Assert
    if (resExec == result) {
      System.out.println("OK");
    } else {
      System.err.println("ERREUR");
    }
  }

  /**
   * teste si un nombre est parfait
   *
   * @param a entier positif
   * @return vrai ssi a est un nombre parfait
   */
  boolean estParfait(int a) {
    boolean parfait = false;
    if (a > 0) {
      int somme = 0;
      int i = 1;
      while (i <= a) {
        if (estDiviseur(a, i)) {
          somme = somme + i;
        }
        i = i + 1;
      }
      if (somme == 2 * a) {
        parfait = true;
      }
    }
    return parfait;
  }

  /** Teste la méthode estParfait() */
  void testEstParfait() {
    System.out.println();
    System.out.println("*** testEstParfait()");
    testCasEstParfait(6, true);
    testCasEstParfait(28, true);
    testCasEstParfait(128, false);
    testCasEstParfait(8128, true);
  }

  /**
   * teste un appel de estParfait
   *
   * @param a nombre à vérifier
   * @param result resultat attendu
   */
  void testCasEstParfait(int a, boolean result) {
    // Arrange
    System.out.print("estParfait(" + a + ") \t= " + result + "\t : ");
    // Act
    boolean resExec = estParfait(a);
    // Assert
    if (resExec == result) {
      System.out.println("OK");
    } else {
      System.err.println("ERREUR");
    }
  }

  /** Affiche les quatre premiers nombres parfaits */
  void quatreNbParfaits() {
    int i = 0;
    int parfaits = 0;
    boolean numEstParfait;
    while (parfaits < 5) {
      numEstParfait = estParfait(i);
      if (numEstParfait) {
        parfaits++;
        System.out.println(i + " est un nombre parfait");
      }
      i++;
    }
  }
}
