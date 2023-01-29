import java.util.ArrayList;
import java.util.Arrays;

/**
 * Grundy2 Game This class adds a Game vs AI function and efficiency calculation
 *
 * @author EDM115, nathan-basol
 */
class Grundy2RecBruteEff {

  /** Global variable used to mesure the efficiency */
  long cpt;

  /** Principal function of the class */
  void principal() {
    /*
    testJouerGagnant();
    testPremier();
    testSuivant();
    */
    tests();
    System.out.println("");
    testEstGagnanteEfficacite();
    System.out.println("");
    partieJoueurContreOrdinateur();
  }

  /**
   * Plays the winning move if it exists
   *
   * @param jeu board game
   * @return true if there's a winning move, false otherwise
   */
  boolean jouerGagnant(ArrayList<Integer> jeu) {
    boolean gagnant = false;

    if (jeu == null) {
      System.err.println("jouerGagnant(): le paramètre jeu est null");
    } else {
      ArrayList<Integer> essai = new ArrayList<Integer>();
      int ligne = premier(jeu, essai);
      while (ligne != -1 && !gagnant) {
        if (estPerdante(essai)) {
          jeu.clear();
          gagnant = true;
          for (int i = 0; i < essai.size(); i++) {
            jeu.add(essai.get(i));
          }
        } else {
          ligne = suivant(jeu, essai, ligne);
        }
      }
    }

    return gagnant;
  }

  /**
   * RECURSIVE method that indicates if the configuration (of the current game or test game) is
   * losing
   *
   * @param jeu current game board (the state of the game at some point during the game)
   * @return true if the configuration (of the game) is losing, false otherwise
   */
  boolean estPerdante(ArrayList<Integer> jeu) {
    boolean ret = true;

    if (jeu == null) {
      System.err.println("estPerdante(): le paramètre jeu est null");
    } else {
      if (!estPossible(jeu)) {
        ret = true;
      } else {
        ArrayList<Integer> essai = new ArrayList<Integer>();
        int ligne = premier(jeu, essai);
        while ((ligne != -1) && ret) {
          cpt++;
          if (estPerdante(essai) == true) {
            ret = false;
          } else {
            ligne = suivant(jeu, essai, ligne);
          }
        }
      }
    }

    return ret;
  }

  /**
   * Indicates if the configuration is winning
   *
   * @param jeu game board
   * @return true if the configuration is winning, false otherwise
   */
  boolean estGagnante(ArrayList<Integer> jeu) {
    boolean ret = false;

    if (jeu == null) {
      System.err.println("estGagnante(): le paramètre jeu est null");
    } else {
      ret = !estPerdante(jeu);
    }

    return ret;
  }

  /** Short tests of the jouerGagnant() method */
  void testJouerGagnant() {
    System.out.println();
    System.out.println("*** testJouerGagnant() ***");
    System.out.println("Test des cas normaux");
    ArrayList<Integer> jeu1 = new ArrayList<Integer>();
    jeu1.add(6);
    ArrayList<Integer> resJeu1 = new ArrayList<Integer>();
    resJeu1.add(4);
    resJeu1.add(2);
    testCasJouerGagnant(jeu1, resJeu1, true);
  }

  /**
   * Testing a case of the jouerGagnant() method
   *
   * @param jeu game board
   * @param resJeu the game board after playing gagnant
   * @param res the result awaited by jouerGagnant
   */
  void testCasJouerGagnant(ArrayList<Integer> jeu, ArrayList<Integer> resJeu, boolean res) {
    System.out.print("jouerGagnant (" + jeu.toString() + ") : ");
    boolean resExec = jouerGagnant(jeu);
    System.out.print(jeu.toString() + " " + resExec + " : ");
    if (jeu.equals(resJeu) && res == resExec) {
      System.out.println("OK\n");
    } else {
      System.err.println("ERREUR\n");
    }
  }

  /**
   * Divide the matches of a line of play into two heaps (1 line = 1 heap)
   *
   * @param jeu table of matches by line
   * @param ligne line (heap) on which matches should be separated
   * @param nb number of matches REMOVED from line after separation
   */
  void enlever(ArrayList<Integer> jeu, int ligne, int nb) {
    if (jeu == null) {
      System.err.println("enlever() : le paramètre jeu est null");
    } else if (ligne >= jeu.size()) {
      System.err.println("enlever() : le numéro de ligne est trop grand");
    } else if (nb >= jeu.get(ligne)) {
      System.err.println("enlever() : le nb d'allumettes à retirer est trop grand");
    } else if (nb <= 0) {
      System.err.println("enlever() : le nb d'allumettes à retirer est trop petit");
    } else if (2 * nb == jeu.get(ligne)) {
      System.err.println("enlever() : le nb d'allumettes à retirer est la moitié");
    } else {
      jeu.add(nb);
      jeu.set(ligne, jeu.get(ligne) - nb);
    }
  }

  /**
   * Tests if it's possible to separate a heap
   *
   * @param jeu game board
   * @return true if there is at least a heap of 3 or more matches, false otherwise
   */
  boolean estPossible(ArrayList<Integer> jeu) {
    boolean ret = false;

    if (jeu == null) {
      System.err.println("estPossible(): le paramètre jeu est null");
    } else {
      int i = 0;
      while (i < jeu.size() && !ret) {
        if (jeu.get(i) > 2) {
          ret = true;
        }
        i = i + 1;
      }
    }

    return ret;
  }

  /**
   * Create a very first trial configuration from the game
   *
   * @param jeu game board
   * @param jeuEssai new game configuration
   * @return the number of the heap divided in half or -1 if there is no heap of at least 3 matches
   */
  int premier(ArrayList<Integer> jeu, ArrayList<Integer> jeuEssai) {
    int numTas = -1;
    int i;

    if (jeu == null) {
      System.err.println("premier(): le paramètre jeu est null");
    } else if (!estPossible((jeu))) {
      System.err.println("premier(): aucun tas n'est divisible");
    } else if (jeuEssai == null) {
      System.err.println("estPossible(): le paramètre jeuEssai est null");
    } else {
      jeuEssai.clear();
      i = 0;
      while (i < jeu.size()) {
        jeuEssai.add(jeu.get(i));
        i = i + 1;
      }
      i = 0;
      boolean trouve = false;
      while ((i < jeu.size()) && !trouve) {
        if (jeuEssai.get(i) >= 3) {
          trouve = true;
          numTas = i;
        }
        i = i + 1;
      }
      if (numTas != -1) {
        enlever(jeuEssai, numTas, 1);
      }
    }

    return numTas;
  }

  /** Short tests of the premier() method */
  void testPremier() {
    System.out.println();
    System.out.println("*** testPremier()");
    ArrayList<Integer> jeu1 = new ArrayList<Integer>();
    jeu1.add(10);
    jeu1.add(11);
    int ligne1 = 0;
    ArrayList<Integer> res1 = new ArrayList<Integer>();
    res1.add(9);
    res1.add(11);
    res1.add(1);
    testCasPremier(jeu1, ligne1, res1);
  }

  /**
   * Test a case of the testPremier method
   *
   * @param jeu game board
   * @param ligne the number of the heap separated first
   * @param res game board after the first separation
   */
  void testCasPremier(ArrayList<Integer> jeu, int ligne, ArrayList<Integer> res) {
    System.out.print("premier (" + jeu.toString() + ") : ");
    ArrayList<Integer> jeuEssai = new ArrayList<Integer>();
    int noLigne = premier(jeu, jeuEssai);
    System.out.println("\nnoLigne = " + noLigne + " jeuEssai = " + jeuEssai.toString());
    if (jeuEssai.equals(res) && noLigne == ligne) {
      System.out.println("OK\n");
    } else {
      System.err.println("ERREUR\n");
    }
  }

  /**
   * Generates the following test setup (i.e. ONE possible decomposition)
   *
   * @param jeu game board
   * @param jeuEssai play test configuration after separation
   * @param ligne the number of the heap which is the last to have been separated
   * @return the number of the heap divided in two for the new configuration, -1 if no more
   *     decomposition is possible
   */
  int suivant(ArrayList<Integer> jeu, ArrayList<Integer> jeuEssai, int ligne) {
    int numTas = -1;
    int i = 0;

    if (jeu == null) {
      System.err.println("suivant(): le paramètre jeu est null");
    } else if (jeuEssai == null) {
      System.err.println("suivant() : le paramètre jeuEssai est null");
    } else if (ligne >= jeu.size()) {
      System.err.println("estPossible(): le paramètre ligne est trop grand");
    } else {
      int nbAllumEnLigne = jeuEssai.get(ligne);
      int nbAllDernCase = jeuEssai.get(jeuEssai.size() - 1);
      if ((nbAllumEnLigne - nbAllDernCase) > 2) {
        jeuEssai.set(ligne, (nbAllumEnLigne - 1));
        jeuEssai.set(jeuEssai.size() - 1, (nbAllDernCase + 1));
        numTas = ligne;
      } else {
        jeuEssai.clear();
        for (i = 0; i < jeu.size(); i++) {
          jeuEssai.add(jeu.get(i));
        }
        boolean separation = false;
        i = ligne + 1;
        while (i < jeuEssai.size() && !separation) {
          if (jeu.get(i) > 2) {
            separation = true;
            enlever(jeuEssai, i, 1);
            numTas = i;
          } else {
            i = i + 1;
          }
        }
      }
    }

    return numTas;
  }

  /** Short tests of the suivant() method */
  void testSuivant() {
    System.out.println();
    System.out.println("*** testSuivant() ****");
    int ligne1 = 0;
    int resLigne1 = 0;
    ArrayList<Integer> jeu1 = new ArrayList<Integer>();
    jeu1.add(10);
    ArrayList<Integer> jeuEssai1 = new ArrayList<Integer>();
    jeuEssai1.add(9);
    jeuEssai1.add(1);
    ArrayList<Integer> res1 = new ArrayList<Integer>();
    res1.add(8);
    res1.add(2);
    testCasSuivant(jeu1, jeuEssai1, ligne1, res1, resLigne1);
    int ligne2 = 0;
    int resLigne2 = -1;
    ArrayList<Integer> jeu2 = new ArrayList<Integer>();
    jeu2.add(10);
    ArrayList<Integer> jeuEssai2 = new ArrayList<Integer>();
    jeuEssai2.add(6);
    jeuEssai2.add(4);
    ArrayList<Integer> res2 = new ArrayList<Integer>();
    res2.add(10);
    testCasSuivant(jeu2, jeuEssai2, ligne2, res2, resLigne2);
    int ligne3 = 1;
    int resLigne3 = 1;
    ArrayList<Integer> jeu3 = new ArrayList<Integer>();
    jeu3.add(4);
    jeu3.add(6);
    jeu3.add(3);
    ArrayList<Integer> jeuEssai3 = new ArrayList<Integer>();
    jeuEssai3.add(4);
    jeuEssai3.add(5);
    jeuEssai3.add(3);
    jeuEssai3.add(1);
    ArrayList<Integer> res3 = new ArrayList<Integer>();
    res3.add(4);
    res3.add(4);
    res3.add(3);
    res3.add(2);
    testCasSuivant(jeu3, jeuEssai3, ligne3, res3, resLigne3);
  }

  /**
   * Test a case of the suivant method
   *
   * @param jeu game board
   * @param jeuEssai the game board obtained after separating a heap
   * @param ligne the number of the heap which is the last to have been separated
   * @param resJeu is the jeuEssai expected after separation
   * @param resLigne is the expected number of the heap that is separated
   */
  void testCasSuivant(
      ArrayList<Integer> jeu,
      ArrayList<Integer> jeuEssai,
      int ligne,
      ArrayList<Integer> resJeu,
      int resLigne) {
    System.out.print(
        "suivant (" + jeu.toString() + ", " + jeuEssai.toString() + ", " + ligne + ") : ");
    int noLigne = suivant(jeu, jeuEssai, ligne);
    System.out.println("\nnoLigne = " + noLigne + " jeuEssai = " + jeuEssai.toString());
    if (jeuEssai.equals(resJeu) && noLigne == resLigne) {
      System.out.println("OK\n");
    } else {
      System.err.println("ERREUR\n");
    }
  }

  ///////////////////////////////////////////////////////////////////////////

  /** Simply calls all tests written for our methods */
  void tests() {
    testCreerTableauAllumettes();
    testCreerEtAfficherLesTableaux();
    testBonChoixLigne();
    testBonneSeparation();
    testPremierZero();
    testSeparation();
    testQuiVaJouer();
    testDemandeNombreAllumettes();
    testUnSeulTasSeparable();
    testIndiceDuSeulTasSeparable();
    testDemandeLigne();
    testDemandeSeparer();
    testFinPartie();
    testLigneDeSeparation();
    testChoixPremierJoueur();
    testDemandeLigneOrdinateur();
    testDemandeSeparerOrdinateur();
  }

  /** Testing the efficiency of the method estGagnante */
  void testEstGagnanteEfficacite() {
    System.out.println();
    System.out.println("*** Efficacité de la méthode estGagnante");
    int n = 3;
    long t1, t2, diffT;
    boolean coupGagnant = false;

    for (int i = 1; i <= 48; i++) {
      ArrayList<Integer> jeu = new ArrayList<Integer>();
      jeu.add(n);
      cpt = 0;
      t1 = System.nanoTime();
      boolean indice = estGagnante(jeu);
      t2 = System.nanoTime();
      diffT = (t2 - t1);
      System.out.println("Pour n = " + n + ", le temps est : " + diffT + " nanosecondes");
      System.out.println("Pour n = " + n + ", le cpt est : " + cpt);
      coupGagnant = jouerGagnant(jeu);
      System.out.println("l'IA joue le coup gagnant ? : " + coupGagnant);
      System.out.println();
      n++;
      jeu.clear();
    }
  }

  /**
   * Creates an array with matches
   *
   * @param nb number of matches
   * @return the final array
   */
  char[] creerTableauAllumettes(int nb) {
    char[] ret = new char[nb * 2];
    int i = 0;

    while (i < ret.length) {
      ret[i] = ' ';
      ret[i + 1] = '|';
      i += 2;
    }

    return ret;
  }

  /** Tests creerTableauAllumettes */
  void testCreerTableauAllumettes() {
    System.out.println();
    System.out.println("*** testCreerTableauAllumettes()");

    char[] a = {' ', '|', ' ', '|'};
    testCasCreerTableauAllumettes(2, a);
    char[] b = {};
    testCasCreerTableauAllumettes(0, b);
    char[] c = {' ', '|'};
    testCasCreerTableauAllumettes(1, c);
  }

  /**
   * Tests a call of creerTableauAllumettes
   *
   * @param lg length of the array
   * @param result expected result
   */
  void testCasCreerTableauAllumettes(int lg, char[] result) {
    System.out.print(
        "creerTableauAllumettes (" + lg + ") \t= " + Arrays.toString(result) + "\t : ");
    char[] resExec = creerTableauAllumettes(lg);
    boolean pareil = Arrays.equals(resExec, result);
    if (pareil) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * Prints the different matches arrays
   *
   * @param tab game board
   */
  void creerEtAfficherLesTableaux(ArrayList<Integer> tab) {
    int i = 0;
    int ligne = 0;

    while (i < tab.size()) {
      if (tab.get(i) >= 1) {
        char[] creerTab = creerTableauAllumettes(tab.get(i));
        System.out.print(ligne + " :");
        System.out.println(creerTab);
        ligne++;
      }
      i++;
    }
  }

  /** Tests creerEtAfficherLesTableaux */
  void testCreerEtAfficherLesTableaux() {
    System.out.println();
    System.out.println("*** testCreerEtAfficherLesTableaux()");

    int[] a = {11, 3, 4, 2, 1, 1, 0, 0, 0, 0, 0};
    ArrayList<Integer> jeu1 = new ArrayList<Integer>();
    for (int i = 0; i < a.length; i++) {
      jeu1.add(a[i]);
    }
    System.out.println("creerEtAfficherLesTableaux (" + Arrays.toString(a) + ")");
    creerEtAfficherLesTableaux(jeu1);
    System.out.println();
    int[] b = {7, 3, 5, 1, 0, 0, 4, 0, 0, 0, 0};
    ArrayList<Integer> jeu2 = new ArrayList<Integer>();
    for (int i = 0; i < b.length; i++) {
      jeu2.add(b[i]);
    }
    System.out.println("creerEtAfficherLesTableaux (" + Arrays.toString(b) + ")");
    creerEtAfficherLesTableaux(jeu2);
  }

  /**
   * Tests if the player gave the right line number
   *
   * @param tab game board
   * @param ligne the line selected by the player
   * @return true only if the line is correct
   */
  boolean bonChoixLigne(ArrayList<Integer> tab, int ligne) {
    boolean ret = true;

    if (ligne < 0 || ligne >= tab.size() || tab.get(ligne) <= 2) {
      ret = false;
    }

    return ret;
  }

  /** Tests bonChoixLigne */
  void testBonChoixLigne() {
    System.out.println();
    System.out.println("*** testBonChoixLigne()");

    int[] a = {2, 3, 5, 1, 0, 0, 0, 0, 0, 0, 0};
    ArrayList<Integer> jeu1 = new ArrayList<Integer>();
    for (int i = 0; i < a.length; i++) {
      jeu1.add(a[i]);
    }
    testCasBonChoixLigne(jeu1, -1, false);
    testCasBonChoixLigne(jeu1, 0, false);
    testCasBonChoixLigne(jeu1, 11, false);
    testCasBonChoixLigne(jeu1, 12, false);
    testCasBonChoixLigne(jeu1, 3, false);
    testCasBonChoixLigne(jeu1, 1, true);
    testCasBonChoixLigne(jeu1, 2, true);
  }

  /**
   * Tests a call of bonChoixLigne
   *
   * @param tab game board
   * @param ligne the selected line
   * @param result expected result
   */
  void testCasBonChoixLigne(ArrayList<Integer> tab, int ligne, boolean result) {
    System.out.print("BonChoixLigne (" + tab + ", " + ligne + ") \t= " + result + "\t : ");
    boolean resExec = bonChoixLigne(tab, ligne);
    if (resExec == result) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * Tests if the array can be separated by the number the player entered
   *
   * @param tab game board
   * @param ligne the line selected by the player
   * @param separer the number the player entered
   * @return true if the array can be separated by the number the player entered
   */
  boolean bonneSeparation(ArrayList<Integer> tab, int ligne, int separer) {
    boolean ret = false;

    if (bonChoixLigne(tab, ligne)) {
      if (tab.get(ligne) % 2 == 0) {
        if ((separer > 0) && (separer < tab.get(ligne)) && (separer != (tab.get(ligne) / 2))) {
          ret = true;
        }
      } else {
        if ((separer > 0) && (separer < tab.get(ligne))) {
          ret = true;
        }
      }
    }

    return ret;
  }

  /** Tests bonneSeparation */
  void testBonneSeparation() {
    System.out.println();
    System.out.println("*** testBonneSeparation()");

    int[] a = {2, 3, 5, 1, 4, 0, 0, 0, 0, 0, 0};
    ArrayList<Integer> jeu1 = new ArrayList<Integer>();
    for (int i = 0; i < a.length; i++) {
      jeu1.add(a[i]);
    }
    testCasBonneSeparation(jeu1, 1, 1, true);
    testCasBonneSeparation(jeu1, 1, 2, true);
    testCasBonneSeparation(jeu1, 1, 3, false);
    testCasBonneSeparation(jeu1, 4, 2, false);
    testCasBonneSeparation(jeu1, 4, -1, false);
    testCasBonneSeparation(jeu1, 4, 4, false);
    testCasBonneSeparation(jeu1, 4, 1, true);
    testCasBonneSeparation(jeu1, 0, 2, false);
    testCasBonneSeparation(jeu1, 0, 1, false);
    testCasBonneSeparation(jeu1, 6, 2, false);
  }

  /**
   * Tests a call of bonneSeparation
   *
   * @param tab game board
   * @param ligne selected line
   * @param separer number of matches the player entered
   * @param result expected result
   */
  void testCasBonneSeparation(ArrayList<Integer> tab, int ligne, int separer, boolean result) {
    System.out.print(
        "bonneSeparation (" + tab + ", " + ligne + ", " + separer + ") \t= " + result + "\t : ");
    boolean resExec = bonneSeparation(tab, ligne, separer);
    if (resExec == result) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * Returns the next empty spot in the array
   *
   * @param tab game board
   * @return the next empty spot in the array
   */
  int premierZero(ArrayList<Integer> tab) {
    int ret = tab.size() + 1;

    return ret;
  }

  /** Tests premierZero */
  void testPremierZero() {
    System.out.println();
    System.out.println("*** testPremierZero()");

    int[] a = {9, 3, 6, 1, 2};
    ArrayList<Integer> jeu1 = new ArrayList<Integer>();
    for (int i = 0; i < a.length; i++) {
      jeu1.add(a[i]);
    }
    testCasPremierZero(jeu1, 6);
    int[] b = {8, 3, 5};
    ArrayList<Integer> jeu2 = new ArrayList<Integer>();
    for (int i = 0; i < b.length; i++) {
      jeu2.add(b[i]);
    }
    testCasPremierZero(jeu2, 4);
    int[] c = {};
    ArrayList<Integer> jeu3 = new ArrayList<Integer>();
    for (int i = 0; i < c.length; i++) {
      jeu3.add(c[i]);
    }
    testCasPremierZero(jeu3, 1);
    int[] d = {8, 3, 1, 2};
    ArrayList<Integer> jeu4 = new ArrayList<Integer>();
    for (int i = 0; i < d.length; i++) {
      jeu4.add(d[i]);
    }
    testCasPremierZero(jeu4, 5);
  }

  /**
   * Tests a call of premierZero
   *
   * @param tab game board
   * @param result expected result
   */
  void testCasPremierZero(ArrayList<Integer> tab, int result) {
    System.out.print("premierZero (" + tab + ") \t= " + result + "\t : ");
    int resExec = premierZero(tab);
    if (resExec == result) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * Separates the array at a given line
   *
   * @param tab game board
   * @param ligne the line selected by the player
   * @param separer the number of matches the player entered
   * @return the array with the separated line
   */
  ArrayList<Integer> separation(ArrayList<Integer> tab, int ligne, int separer) {
    int tmp;

    if (bonneSeparation(tab, ligne, separer)) {
      try {
        if (tab.get(ligne + 1) == 0) {
          tmp = tab.get(ligne) - separer;
          tab.add(ligne + 1, tmp);
          tab.remove(ligne);
          tab.add(ligne, separer);
        } else {
          int zero = premierZero(tab);
          tmp = tab.get(ligne) - separer;
          tab.add(zero, tmp);
          tab.remove(ligne);
          tab.add(ligne, separer);
        }
      } catch (IndexOutOfBoundsException e) {
        tmp = tab.get(ligne) - separer;
        tab.add(ligne + 1, tmp);
        tab.remove(ligne);
        tab.add(ligne, separer);
      }
    }

    return tab;
  }

  /** Tests separation */
  void testSeparation() {
    System.out.println();
    System.out.println("*** testSeparation()");

    int[] a = {3, 5, 4, 3, 0, 0, 0, 0, 0};
    ArrayList<Integer> jeuA = new ArrayList<Integer>();
    for (int i = 0; i < a.length; i++) {
      jeuA.add(a[i]);
    }
    int[] resultA = {3, 2, 3, 4, 3, 0, 0, 0, 0, 0};
    ArrayList<Integer> jeuResultA = new ArrayList<Integer>();
    for (int i = 0; i < resultA.length; i++) {
      jeuResultA.add(resultA[i]);
    }
    testCasSeparation(jeuA, 1, 2, jeuResultA);
    int[] b = {3, 5, 4, 3, 0, 0, 0, 0, 0};
    ArrayList<Integer> jeuB = new ArrayList<Integer>();
    for (int i = 0; i < b.length; i++) {
      jeuB.add(b[i]);
    }
    int[] resultB = {3, 5, 4, 2, 1, 0, 0, 0, 0, 0};
    ArrayList<Integer> jeuResultB = new ArrayList<Integer>();
    for (int i = 0; i < resultB.length; i++) {
      jeuResultB.add(resultB[i]);
    }
    testCasSeparation(jeuB, 3, 2, jeuResultB);
    int[] c = {3, 5, 4, 3, 0, 0, 0, 0, 0};
    ArrayList<Integer> jeuC = new ArrayList<Integer>();
    for (int i = 0; i < c.length; i++) {
      jeuC.add(c[i]);
    }
    int[] resultC = {1, 2, 5, 4, 3, 0, 0, 0, 0, 0};
    ArrayList<Integer> jeuResultC = new ArrayList<Integer>();
    for (int i = 0; i < resultC.length; i++) {
      jeuResultC.add(resultC[i]);
    }
    testCasSeparation(jeuC, 0, 1, jeuResultC);
  }

  /**
   * Tests a call of separation
   *
   * @param tab game board
   * @param ligne selected line
   * @param separer number of matches to separate
   * @param result expected result
   */
  void testCasSeparation(
      ArrayList<Integer> tab, int ligne, int separer, ArrayList<Integer> result) {
    System.out.print(
        "separation (" + tab + ", " + ligne + ", " + separer + ") \t= " + result + "\t : ");
    ArrayList<Integer> resExec = separation(tab, ligne, separer);
    System.out.println(resExec);
    if (resExec.equals(result)) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * Determine who is going to play
   *
   * @param joueur even or odd number
   * @param joueur1 name of the first player
   * @param joueur2 name of the second player
   * @return returns the name of the player1 if the number is even, returns the name of the player2
   *     if the number is odd
   */
  String quiVaJouer(int joueur, String joueur1, String joueur2) {
    String ret;

    if (joueur % 2 == 0) {
      ret = joueur1;
    } else {
      ret = joueur2;
    }

    return ret;
  }

  /** Tests quiVaJouer */
  void testQuiVaJouer() {
    System.out.println();
    System.out.println("*** testQuiVaJouer()");

    String j1 = "nathan";
    String j2 = "basol";
    testCasQuiVaJouer(1, j1, j2, "basol");
    testCasQuiVaJouer(0, j1, j2, "nathan");
    testCasQuiVaJouer(4, j1, j2, "nathan");
    testCasQuiVaJouer(5, j1, j2, "basol");
  }

  /**
   * Tests a call of quiVaJouer
   *
   * @param joueur even or odd number
   * @param joueur1 name of the first player
   * @param joueur2 name of the second player
   * @param result expected result
   */
  void testCasQuiVaJouer(int joueur, String joueur1, String joueur2, String result) {
    System.out.print(
        "quiVaJouer (" + joueur + ", " + joueur1 + ", " + joueur2 + ") \t= " + result + "\t : ");
    String resExec = quiVaJouer(joueur, joueur1, joueur2);
    if (resExec == result) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * Asks the player for the starting number of matches as long as the number of matches entered is
   * incorrect
   *
   * @return an array of integers with index 0 initialized
   */
  ArrayList<Integer> demandeNombreAllumettes() {
    int initialisation = SimpleInput.getInt("Nombre d'allumettes : ");

    while (initialisation <= 2) {
      initialisation = SimpleInput.getInt("Nombre d'allumettes : ");
    }
    ArrayList<Integer> ret = new ArrayList<Integer>();
    ret.add(0, initialisation);

    return ret;
  }

  /** Tests demandeNombreAllumettes */
  void testDemandeNombreAllumettes() {
    System.out.println();
    System.out.println("*** testDemandeNombreAllumettes()");

    System.out.println(
        "\u001B[36mrentrez 1 puis 2 puis 7 (le programme renvoit ok si la méthode marche bien) :"
            + " \u001B[0m");
    int[] a = {7};
    ArrayList<Integer> a2 = new ArrayList<Integer>();
    for (int i = 0; i < a.length; i++) {
      a2.add(a[i]);
    }
    testCasDemandeNombreAllumettes(a2);
    System.out.println();
    System.out.println(
        "\u001B[36mrentrez 13 (le programme renvoit ok si la méthode marche bien) : \u001B[0m");
    int[] b = {13};
    ArrayList<Integer> b2 = new ArrayList<Integer>();
    for (int i = 0; i < b.length; i++) {
      b2.add(b[i]);
    }
    testCasDemandeNombreAllumettes(b2);
  }

  /**
   * Tests a call of demandeNombreAllumettes
   *
   * @param result expected result
   */
  void testCasDemandeNombreAllumettes(ArrayList<Integer> result) {
    System.out.println(
        "demandeNombreAllumettes ("
            + " nombres rentrés par le testeur "
            + ") \t= "
            + result
            + "\t ");
    ArrayList<Integer> resExec = demandeNombreAllumettes();
    if (resExec.equals(result)) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * Checks if an heap is separable
   *
   * @param tab game board
   * @return true only if one heap is separable
   */
  boolean unSeulTasSeparable(ArrayList<Integer> tab) {
    boolean ret = false;
    int compteur = 0;
    int i = 0;

    while (i < tab.size()) {
      if (tab.get(i) > 2) {
        compteur++;
      }
      i++;
    }
    if (compteur == 1) {
      ret = true;
    }

    return ret;
  }

  /** Tests unSeulTasSeparable */
  void testUnSeulTasSeparable() {
    System.out.println();
    System.out.println("*** testUnSeulTasSeparable()");

    int[] a = {2, 1, 3, 2};
    ArrayList<Integer> a2 = new ArrayList<Integer>();
    for (int i = 0; i < a.length; i++) {
      a2.add(a[i]);
    }
    testCasUnSeulTasSeparable(a2, true);
    int[] b = {2, 4, 3, 2};
    ArrayList<Integer> b2 = new ArrayList<Integer>();
    for (int i = 0; i < b.length; i++) {
      b2.add(b[i]);
    }
    testCasUnSeulTasSeparable(b2, false);
    int[] c = {3};
    ArrayList<Integer> c2 = new ArrayList<Integer>();
    for (int i = 0; i < c.length; i++) {
      c2.add(c[i]);
    }
    testCasUnSeulTasSeparable(c2, true);
  }

  /**
   * Tests a call of unSeulTasSeparable
   *
   * @param tab game board
   * @param result expected result
   */
  void testCasUnSeulTasSeparable(ArrayList<Integer> tab, boolean result) {
    System.out.print("unSeulTasSeparable (" + tab + ") \t= " + result + "\t : ");
    boolean resExec = unSeulTasSeparable(tab);
    if (resExec == result) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * Searches for the index of the only separable heap
   *
   * @param tab game board with the only separable heap
   * @return the index of that heap
   */
  int indiceDuSeulTasSeparable(ArrayList<Integer> tab) {
    int ret = 0;
    int i = 0;
    boolean sort = false;

    while ((i < tab.size()) && !sort) {
      if (tab.get(i) > 2) {
        ret = i;
        sort = true;
      }
      i++;
    }

    return ret;
  }

  /** Tests indiceDuSeulTasSeparable */
  void testIndiceDuSeulTasSeparable() {
    System.out.println();
    System.out.println("*** testIndiceDuSeulTasSeparable()");

    int[] a = {3, 1, 2, 1};
    ArrayList<Integer> a2 = new ArrayList<Integer>();
    for (int i = 0; i < a.length; i++) {
      a2.add(a[i]);
    }
    testCasIndiceDuSeulTasSeparable(a2, 0);
    int[] b = {1, 1, 2, 3};
    ArrayList<Integer> b2 = new ArrayList<Integer>();
    for (int i = 0; i < b.length; i++) {
      b2.add(b[i]);
    }
    testCasIndiceDuSeulTasSeparable(b2, 3);
    int[] c = {1, 3, 2, 1};
    ArrayList<Integer> c2 = new ArrayList<Integer>();
    for (int i = 0; i < c.length; i++) {
      c2.add(c[i]);
    }
    testCasIndiceDuSeulTasSeparable(c2, 1);
    int[] d = {3};
    ArrayList<Integer> d2 = new ArrayList<Integer>();
    for (int i = 0; i < d.length; i++) {
      d2.add(d[i]);
    }
    testCasIndiceDuSeulTasSeparable(d2, 0);
  }

  /**
   * Tests a call of indiceDuSeulTasSeparable
   *
   * @param tab game board
   * @param result expected result
   */
  void testCasIndiceDuSeulTasSeparable(ArrayList<Integer> tab, int result) {
    System.out.print("indiceDuSeulTasSeparable (" + tab + ") \t= " + result + "\t : ");
    int resExec = indiceDuSeulTasSeparable(tab);
    if (resExec == result) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * Asks the player for a valid line as long as the line entered is incorrect
   *
   * @param tab game board
   * @return valid line entered by the player
   */
  int demandeLigne(ArrayList<Integer> tab) {
    int ret;

    if (!unSeulTasSeparable(tab)) {
      ret = SimpleInput.getInt("Ligne : ");
      while (!bonChoixLigne(tab, ret)) {
        ret = SimpleInput.getInt("Ligne : ");
      }
    } else {
      ret = indiceDuSeulTasSeparable(tab);
    }

    return ret;
  }

  /** Tests demandeLigne */
  void testDemandeLigne() {
    System.out.println();
    System.out.println("*** testDemandeLigne()");

    System.out.println(
        "\u001B[36mrentrez 0 puis 1 puis 2 (le programme renvoit ok si la méthode marche bien) :"
            + " \u001B[0m");
    int[] a = {2, 1, 5, 3, 0, 0, 0, 0, 0};
    ArrayList<Integer> a2 = new ArrayList<Integer>();
    for (int i = 0; i < a.length; i++) {
      a2.add(a[i]);
    }
    testCasDemandeLigne(a2, 2);
    System.out.println();
    System.out.println("ici vous n'avez rien a rentrer car il n'y a qu'un seul tas separable");
    int[] b = {2, 1, 5, 2, 0, 0, 0, 0, 0};
    ArrayList<Integer> b2 = new ArrayList<Integer>();
    for (int i = 0; i < b.length; i++) {
      b2.add(b[i]);
    }
    testCasDemandeLigne(b2, 2);
  }

  /**
   * Tests a call of demandeLigne
   *
   * @param tab game board
   * @param result expected result
   */
  void testCasDemandeLigne(ArrayList<Integer> tab, int result) {
    System.out.println("demandeLigne (" + tab + ") \t= " + result + "\t ");
    int resExec = demandeLigne(tab);
    if (resExec == result) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * Ask the player for a valid number of matches to separate as long as the number entered is
   * incorrect
   *
   * @param tab game board
   * @param ligne valid line
   * @return valid separation entered by the player
   */
  int demandeSeparer(ArrayList<Integer> tab, int ligne) {
    int ret = SimpleInput.getInt("Nombre d'allumettes à séparer : ");

    while (!bonneSeparation(tab, ligne, ret)) {
      ret = SimpleInput.getInt("Nombre d'allumettes à séparer : ");
    }

    return ret;
  }

  /** Tests demandeSeparer */
  void testDemandeSeparer() {
    System.out.println();
    System.out.println("*** testDemandeSeparer()");

    System.out.println(
        "\u001B[36mrentrez 0 puis 5 puis 3 (le programme renvoit ok si la méthode marche bien) :"
            + " \u001B[0m");
    int[] a = {2, 1, 5, 3, 0, 0, 0, 0, 0};
    ArrayList<Integer> a2 = new ArrayList<Integer>();
    for (int i = 0; i < a.length; i++) {
      a2.add(a[i]);
    }
    testCasDemandeSeparer(a2, 2, 3);
  }

  /**
   * Tests a call of demandeSeparer
   *
   * @param tab game board
   * @param ligne valid line
   * @param result expected result
   */
  void testCasDemandeSeparer(ArrayList<Integer> tab, int ligne, int result) {
    System.out.println("demandeSeparer (" + tab + ", " + ligne + ") \t= " + result + "\t ");
    int resExec = demandeSeparer(tab, ligne);
    if (resExec == result) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * Tests if an array contains only numbers less than or equal to 2
   *
   * @param tab game board
   * @return true only if the array contains only numbers less than or equal to 2
   */
  boolean finPartie(ArrayList<Integer> tab) {
    boolean ret = false;
    int i = 0;
    boolean sort = true;

    while (i < tab.size()) {
      if (tab.get(i) > 2) {
        sort = false;
      }
      i++;
    }
    if (sort) {
      ret = true;
    }

    return ret;
  }

  /** Tests finPartie */
  void testFinPartie() {
    System.out.println();
    System.out.println("*** testFinPartie()");

    int[] a = {8, 4, 6, 3};
    ArrayList<Integer> a2 = new ArrayList<Integer>();
    for (int i = 0; i < a.length; i++) {
      a2.add(a[i]);
    }
    testCasFinPartie(a2, false);
    int[] b = {2, 1, 2, 1};
    ArrayList<Integer> b2 = new ArrayList<Integer>();
    for (int i = 0; i < b.length; i++) {
      b2.add(b[i]);
    }
    testCasFinPartie(b2, true);
    int[] c = {2, 4, 1, 3};
    ArrayList<Integer> c2 = new ArrayList<Integer>();
    for (int i = 0; i < c.length; i++) {
      c2.add(c[i]);
    }
    testCasFinPartie(c2, false);
    int[] d = {2};
    ArrayList<Integer> d2 = new ArrayList<Integer>();
    for (int i = 0; i < d.length; i++) {
      d2.add(d[i]);
    }
    testCasFinPartie(d2, true);
    int[] e = {1, 1, 1, 3};
    ArrayList<Integer> e2 = new ArrayList<Integer>();
    for (int i = 0; i < e.length; i++) {
      e2.add(e[i]);
    }
    testCasFinPartie(e2, false);
    int[] f = {2, 1, 2, 1, 2, 1, 1};
    ArrayList<Integer> f2 = new ArrayList<Integer>();
    for (int i = 0; i < f.length; i++) {
      f2.add(f[i]);
    }
    testCasFinPartie(f2, true);
  }

  /**
   * Tests a call of finPartie
   *
   * @param tab game board
   * @param result expected result
   */
  void testCasFinPartie(ArrayList<Integer> tab, boolean result) {
    System.out.print("finPartie (" + tab + ") \t= " + result + "\t : ");
    boolean resExec = finPartie(tab);
    if (resExec == result) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /** Prints a line */
  void ligneDeSeparation() {
    System.out.println();
    System.out.println("-----------------");
    System.out.println();
  }

  /** Tests ligneDeSeparation */
  void testLigneDeSeparation() {
    System.out.println();
    System.out.println("*** testLigneDeSeparation()");
    ligneDeSeparation();
  }

  /**
   * Asks who plays first
   *
   * @return 0 if the player plays first, 1 if the AI plays first
   */
  int choixPremierJoueur() {
    System.out.println("Premier à jouer");
    System.out.println("- 0 le joueur");
    System.out.println("- 1 l'ordinateur");
    int ret = SimpleInput.getInt("qui est le premier à jouer : ");

    while ((ret > 1) || (ret < 0)) {
      ret = SimpleInput.getInt("qui est le premier à jouer : ");
    }

    return ret;
  }

  /** Tests choixPremierJoueur */
  void testChoixPremierJoueur() {
    System.out.println();
    System.out.println("*** testChoixPremierJoueur()");

    System.out.println(
        "\u001B[36mrentrez -1 puis 3 puis 2 puis 1 (le programme renvoit ok si la méthode marche"
            + " bien) : \u001B[0m");
    int a = 1;
    testCasChoixPremierJoueur(a);
    System.out.println();
    System.out.println(
        "\u001B[36mrentrez 0 (le programme renvoit ok si la méthode marche bien) : \u001B[0m");
    int b = 0;
    testCasChoixPremierJoueur(b);
  }

  /**
   * Tests a call of choixPremierJoueur
   *
   * @param result expected result
   */
  void testCasChoixPremierJoueur(int result) {
    System.out.println(
        "choixPremierJoueur (" + "nombres rentrés par le testeur" + ") \t= " + result + "\t ");
    int resExec = choixPremierJoueur();
    if (resExec == result) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * The AI choose a random number between 0 and the size of the array
   *
   * @param tab game board
   * @return valid line number chosen by the AI
   */
  int demandeLigneOrdinateur(ArrayList<Integer> tab) {
    int ret = (int) (Math.random() * (tab.size()));

    while (!bonChoixLigne(tab, ret)) {
      ret = (int) (Math.random() * (tab.size()));
    }

    return ret;
  }

  /** Tests demandeLigneOrdinateur */
  void testDemandeLigneOrdinateur() {
    System.out.println();
    System.out.println("*** testDemandeLigneOrdinateur()");

    int[] a1 = {2, 7, 5, 1, 1, 3, 4, 2, 6};
    ArrayList<Integer> a = new ArrayList<Integer>();
    for (int i = 0; i < a1.length; i++) {
      a.add(a1[i]);
    }
    testCasDemandeLigneOrdinateur(a, 0, 8);
    testCasDemandeLigneOrdinateur(a, 0, 8);
    testCasDemandeLigneOrdinateur(a, 0, 8);
    testCasDemandeLigneOrdinateur(a, 0, 8);
    testCasDemandeLigneOrdinateur(a, 0, 8);
    testCasDemandeLigneOrdinateur(a, 0, 8);
    testCasDemandeLigneOrdinateur(a, 0, 8);
    testCasDemandeLigneOrdinateur(a, 0, 8);
    testCasDemandeLigneOrdinateur(a, 0, 8);
    testCasDemandeLigneOrdinateur(a, 0, 8);
    testCasDemandeLigneOrdinateur(a, 0, 8);
  }

  /**
   * Tests a call of demandeLigneOrdinateur
   *
   * @param tab game board
   * @param min minimum value that the method can return
   * @param max maximum value that the method can return
   */
  void testCasDemandeLigneOrdinateur(ArrayList<Integer> tab, int min, int max) {
    System.out.print(
        "demandeLigneOrdinateur ("
            + tab
            + ") \t= "
            + "supérieur à "
            + min
            + " et inférieur à "
            + max
            + "\t ");
    int resExec = demandeLigneOrdinateur(tab);
    if ((resExec >= min) && (resExec <= max)) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /**
   * The AI chooses a random number between 0 and the number of matches in the heap chosen
   *
   * @param tab game board
   * @param ligne valid line
   * @return a valid separation chosen by the AI
   */
  int demandeSeparerOrdinateur(ArrayList<Integer> tab, int ligne) {
    int ret = (int) (Math.random() * (tab.get(ligne)));

    while (!bonneSeparation(tab, ligne, ret)) {
      ret = (int) (Math.random() * (tab.get(ligne)));
    }

    return ret;
  }

  /** Tests demandeSeparerOrdinateur */
  void testDemandeSeparerOrdinateur() {
    System.out.println();
    System.out.println("*** testDemandeSeparerOrdinateur()");

    int[] a2 = {2, 7, 5, 1, 1, 3, 4, 2, 6};
    ArrayList<Integer> a = new ArrayList<Integer>();
    for (int i = 0; i < a2.length; i++) {
      a.add(a2[i]);
    }
    testCasDemandeSeparerOrdinateur(a, 1, 0, 6);
    testCasDemandeSeparerOrdinateur(a, 1, 0, 6);
    testCasDemandeSeparerOrdinateur(a, 1, 0, 6);
    testCasDemandeSeparerOrdinateur(a, 1, 0, 6);
    testCasDemandeSeparerOrdinateur(a, 1, 0, 6);
    testCasDemandeSeparerOrdinateur(a, 1, 0, 6);
    testCasDemandeSeparerOrdinateur(a, 1, 0, 6);
    testCasDemandeSeparerOrdinateur(a, 2, 0, 4);
    testCasDemandeSeparerOrdinateur(a, 2, 0, 4);
    testCasDemandeSeparerOrdinateur(a, 2, 0, 4);
    testCasDemandeSeparerOrdinateur(a, 2, 0, 4);
    testCasDemandeSeparerOrdinateur(a, 2, 0, 4);
  }

  /**
   * Tests a call of demandeSeparerOrdinateur
   *
   * @param tab game board
   * @param ligne valid line
   * @param min minimum value that the method can return
   * @param max maximum value that the method can return
   */
  void testCasDemandeSeparerOrdinateur(ArrayList<Integer> tab, int ligne, int min, int max) {
    System.out.print(
        "demandeSeparerOrdinateur ("
            + tab
            + ", "
            + ligne
            + ") \t= "
            + "supérieur à "
            + min
            + " et inférieur à "
            + max
            + "\t ");
    int resExec = demandeSeparerOrdinateur(tab, ligne);
    if ((resExec >= min) && (resExec <= max)) {
      System.out.println("\u001B[32mOK\u001B[0m");
    } else {
      System.err.println("\u001B[31mERREUR\u001B[0m");
    }
  }

  /** Plays a Player vs AI game */
  void partieJoueurContreOrdinateur() {
    // Initialisation du tableau d'allumettes
    ArrayList<Integer> tabAllumettes = demandeNombreAllumettes();
    // Demande le nom du joueur + initilalisation du nom de l'ordinateur
    String joueur1 = SimpleInput.getString("Nom du joueur : ");
    String ordi = "IA";
    ligneDeSeparation();
    // Demande qui joue en premier
    int joueur = choixPremierJoueur();
    String quiJoue = quiVaJouer(joueur, joueur1, ordi);
    ligneDeSeparation();
    // Affichage de la ligne 0 contenant un certains nombre d'allumettes
    creerEtAfficherLesTableaux(tabAllumettes);
    // La partie continue tant que l'on peut séparer les allumettes
    while (!finPartie(tabAllumettes)) {
      // Détermine qui va jouer cette manche et affichage du nom
      quiJoue = quiVaJouer(joueur, joueur1, ordi);
      ligneDeSeparation();
      System.out.println("Tour du joueur : " + quiJoue);
      // Initialisation des variables ligne et separer
      int ligne;
      int separer;
      // Cas de quand le joueur 1 joue
      if ((joueur % 2) == 0) {
        // Demande la ligne au joueur
        ligne = demandeLigne(tabAllumettes);
        // Demande le nombre d'allumettes qu'il veut séparer
        separer = demandeSeparer(tabAllumettes, ligne);
        separation(tabAllumettes, ligne, separer);
        ligneDeSeparation();
        // Cas de quand l'ordinateur joue
      } else {
        if (jouerGagnant(tabAllumettes)) {
          ;
        } else {
          // l'ordinateur choisi une ligne au hasard
          ligne = demandeLigneOrdinateur(tabAllumettes);
          // l'ordinateur choisi une séparation au hasard
          separer = demandeSeparerOrdinateur(tabAllumettes, ligne);
          separation(tabAllumettes, ligne, separer);
        }
        ligneDeSeparation();
      }
      // On affiche les tableaux d'allumettes
      creerEtAfficherLesTableaux(tabAllumettes);
      // On incrémente la variable joueur pour changer de joueur
      joueur++;
    }
    // Affichage du nom du gagnant
    ligneDeSeparation();
    System.out.println(quiJoue + " a gagné !");
  }

  /** Tests partieJoueurContreOrdinateur() */
  void testPartieJoueurContreOrdinateur() {
    System.out.println();
    System.out.println("*** testPartieJoueurContreOrdinateur()");
    partieJoueurContreOrdinateur();
  }
}
