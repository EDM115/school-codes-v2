/**
 * C'est un pendu
 *
 * @author EDM115
 */
import java.util.Arrays;

class Pendu {
  void principal() {
    testCreerDico();
    testChoisirMot();
    testAfficherReponse();
    testCreerReponse();
    testTester();
    testEstComplet();
    testPartie();
    // partie(creerDico());
  }

  /**
   * Affiche le contenu d'un tableau contenant des String
   *
   * @param t tableau avec des String dedans
   * @return Le tableau affiché correctement
   */
  String affStringTab(String[] t) {
    boolean nullTab = false;
    int len = t.length;
    if (len == 0) {
      nullTab = true;
    }
    int i = 0;
    String value;
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

  /**
   * Affiche le contenu d'un tableau contenant des char
   *
   * @param t tableau avec des char dedans
   * @return Le tableau affiché correctement
   */
  String affStringChar(char[] t) {
    boolean nullTab = false;
    int len = t.length;
    if (len == 0) {
      nullTab = true;
    }
    int i = 0;
    char value;
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

  /**
   * Création d'un dictionnaire de mots
   *
   * @return dictionnaire initialisé
   */
  String[] creerDico() {
    String[] dico = {
      "administrateur",
      "android",
      "apple",
      "binaire",
      "bluetooth",
      "code",
      "discord",
      "emoji",
      "gamer",
      "google",
      "java",
      "informatique",
      "internet",
      "linux",
      "mail",
      "microsoft",
      "programmation",
      "python",
      "rgb",
      "serveur",
      "smartphone",
      "usb",
      "vpn",
      "wifi",
      "windows",
      "wooclap"
    };
    return dico;
  }

  /** Teste la méthode creerDico() */
  void testCreerDico() {
    System.out.println();
    System.out.println("*** testCreerDico()");
    System.out.println(affStringTab(creerDico()));
    testCasCreerDico(0, "administrateur");
    testCasCreerDico(6, "discord");
  }

  /**
   * teste un appel de creerDico
   *
   * @param i indice du tableau
   * @param value valeur attendue de tableau[i]
   */
  void testCasCreerDico(int i, String value) {
    // Arrange
    System.out.print("testCasCreerDico(" + i + ") \t= " + value + "\t : ");
    // Act
    String[] dico = creerDico();
    String resExec = dico[i];
    // Assert
    if (resExec == value) {
      System.out.println("OK");
    } else {
      System.err.println("ERREUR");
    }
  }

  /**
   * choix aléatoire d'un mot dans un dictionnaire
   *
   * @param dico dictionnaire des mots à choisir
   * @return chaine choisie de manière aléatoire
   */
  String choisirMot(String[] dico) {
    double random = Math.random() * dico.length;
    int r = (int) random;
    String mot = dico[r];
    return mot;
  }

  /** Teste la méthode choisirMot() */
  void testChoisirMot() {
    System.out.println();
    System.out.println("*** testChoisirMot()");
    String test1 = choisirMot(creerDico());
    testCasChoisirMot(test1, true);
    String test2 = choisirMot(creerDico());
    testCasChoisirMot(test2, true);
    String test3 = "pasdansletableau";
    testCasChoisirMot(test3, false);
  }

  /**
   * teste un appel de testChoisirMot
   *
   * @param value un mot du dictionnaire
   * @param resultat dit si le mot en fait bien partie
   */
  void testCasChoisirMot(String value, boolean resultat) {
    // Arrange
    System.out.print("testCasChoisirMot(" + value + ") \t= " + resultat + "\t : ");
    // Act
    String[] dico = creerDico();
    int len = dico.length;
    int i = 0;
    boolean resExec = false;
    while (i < len && !resExec) {
      if (dico[i] == value) {
        resExec = true;
      }
      i++;
    }
    // Assert
    if (resExec == resultat) {
      System.out.println("OK");
    } else {
      System.err.println("ERREUR");
    }
  }

  /**
   * affiche la réponse du joueur
   *
   * @param reponse reponse du joueur
   */
  void afficherReponse(char[] reponse) {
    for (int i = 0; i < reponse.length; i++) {
      System.out.print(reponse[i] + " ");
    }
    System.out.println();
  }

  /** Test de afficherReponse() */
  void testAfficherReponse() {
    System.out.println();
    System.out.println("*** testAfficherReponse()");
    char[] reponse1 = {'P', 'R', 'O', 'G', 'R', 'A', 'M', 'M', 'E'};
    testCasAfficherReponse(reponse1);
    char[] reponse2 = {};
    testCasAfficherReponse(reponse2);
  }

  /**
   * teste un appel à afficherReponse()
   *
   * @param reponse tableau des réponse à afficher
   */
  void testCasAfficherReponse(char[] reponse) {
    System.out.print("afficherReponse (" + Arrays.toString(reponse) + ") : ");
    afficherReponse(reponse);
  }

  /**
   * création d'un tableau de reponse contenant des '_'
   *
   * @param lg longueur du tableau à créer
   * @return tableau de reponse contenant des '_'
   */
  char[] creerReponse(int lg) {
    char[] reponse;
    reponse = new char[lg];
    for (int i = 0; i < lg; i++) {
      reponse[i] = '_';
    }
    return reponse;
  }

  /**
   * Test de creerReponse()
   *
   * @return resultat attendu
   */
  void testCreerReponse() {
    System.out.println();
    System.out.println("*** testCreerReponse()");
    testCasCreerReponse(0);
    testCasCreerReponse(10);
  }

  /**
   * @param lg longueur du tableau
   */
  void testCasCreerReponse(int lg) {
    // Act
    char[] reponse = creerReponse(lg);
    // Arrange
    System.out.print("testCasCreerReponse(" + lg + ") \t= " + affStringChar(reponse) + "\t : ");
    if (lg > 0) {
      double random = Math.random() * reponse.length;
      int r = (int) random;
      char resExec = reponse[r];
      int len = reponse.length;
      // Assert
      if (resExec == '_' && len == lg) {
        System.out.println("OK");
      } else {
        System.err.println("ERREUR");
      }
    } else {
      int len = reponse.length;
      if (len == lg) {
        System.out.println("OK");
      } else {
        System.err.println("ERREUR");
      }
    }
  }

  /**
   * teste la présence d'un caractère dans le mot et le place au bon endroit dans réponse
   *
   * @param mot mot à deviner
   * @param reponse réponse à compléter si le caractère est présent dans le mot
   * @param car caractère à chercher dans le mot
   * @return vrai ssi le caractère est dans le mot à deviner
   */
  boolean tester(String mot, char[] reponse, char car) {
    boolean test = false;
    int i = 0;
    int len = mot.length();
    char motTest;
    while (i < len) {
      motTest = mot.charAt(i);
      if (motTest == car) {
        test = true;
        reponse[i] = motTest;
      }
      i++;
    }
    return test;
  }

  /** Teste la méthode tester() */
  void testTester() {
    System.out.println();
    System.out.println("*** testTester()");
    testCasTester("test", creerReponse(4), 's', true);
    testCasTester("suisse", creerReponse(6), 's', true);
    testCasTester("faux", creerReponse(4), 'z', false);
  }

  /**
   * teste un appel de tester
   *
   * @param mot le mot de test
   * @param reponse le tableau qui représente la potence
   * @param car la réponse de l'utilisateur
   */
  void testCasTester(String mot, char[] reponse, char car, boolean resultat) {
    // Act
    boolean tester = tester(mot, reponse, car);
    // Arrange
    System.out.print(
        "testCasTester("
            + mot
            + ", "
            + affStringChar(reponse)
            + ", "
            + car
            + ") \t= "
            + resultat
            + "\t : ");
    // Assert
    if (tester == resultat) {
      System.out.println("OK");
    } else {
      System.err.println("ERREUR");
    }
  }

  /**
   * rend vrai ssi le mot est trouvé
   *
   * @param mot mot à deviner
   * @param reponse réponse du joueur
   * @return vrai ssi le mot est égal caractère par caractère à la réponse
   */
  boolean estComplet(String mot, char[] reponse) {
    boolean complet = false;
    boolean test = true;
    if (mot.length() == reponse.length) {
      int i = 0;
      int len = reponse.length;
      while (test && i < len) {
        if (mot.charAt(i) != reponse[i]) {
          test = false;
        }
        i++;
      }
      if (test) {
        complet = true;
      }
    }
    return complet;
  }

  /** Teste la méthode estComplet() */
  void testEstComplet() {
    System.out.println();
    System.out.println("*** testEstComplet()");
    char[] test = {'t', 'e', 's', 't'};
    testCasEstComplet("test", test, true);
    testCasEstComplet("tess", test, false);
    testCasEstComplet("faux", creerReponse(4), false);
  }

  /**
   * teste un appel de estComplet
   *
   * @param mot le mot de test
   * @param reponse le tableau qui représente la potence
   * @param car la réponse de l'utilisateur
   */
  void testCasEstComplet(String mot, char[] reponse, boolean resultat) {
    // Arrange
    System.out.print(
        "testCasEstComplet(" + mot + ", " + affStringChar(reponse) + ") \t= " + resultat + "\t : ");
    // Act
    boolean complet = estComplet(mot, reponse);
    // Assert
    if (complet == resultat) {
      System.out.println("OK");
    } else {
      System.err.println("ERREUR");
    }
  }

  /**
   * lancement d'une partie du jeu du pendu
   *
   * @param dico dictionnaire des mots à deviner
   */
  void partie(String[] dico) {
    System.out.println();
    int i = 0;
    int j = 0;
    int err = 0;
    String mot = choisirMot(dico);
    boolean test;
    int len = mot.length();
    int appear = 0;
    char[] reponse = creerReponse(len);
    char[] totalreponse = new char[len + 9];
    boolean complet = estComplet(mot, reponse);
    boolean egal = true;
    while (!complet && err < 9) {
      afficherReponse(reponse);
      char lettre = SimpleInput.getChar("Donnez-moi une lettre : ");
      totalreponse[i] = lettre;
      i++;
      test = tester(mot, reponse, lettre);
      while (j < (len + 9)) {
        if (totalreponse[j] == lettre) {
          appear++;
        }
        j++;
      }
      if (appear > 1) {
        test = false;
      }
      j = 0;
      appear = 0;
      if (!test) {
        err++;
      }
      complet = estComplet(mot, reponse);
    }
    afficherReponse(reponse);
    System.out.println();
    if (err < 9) {
      System.out.println("Bravo ! Vous avez trouvé " + mot + " en " + i + " essais");
    } else {
      System.out.println(
          "Vous n'avez pas trouvé " + mot + ". Vous avez fait 9 erreurs en " + i + " essais");
    }
  }

  /** Teste la méthode partie() */
  void testPartie() {
    System.out.println();
    System.out.println("*** testPartie()");
    partie(creerDico());
  }
}
