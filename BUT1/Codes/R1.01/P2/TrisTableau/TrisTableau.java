import java.util.Arrays;

/**
 * Cette classe effectue des opérations plus complexes sur un tableaux d'entiers : recherche
 * dichotomique, tris, etc. La taille d'un tableau est par définition le nombre TOTAL de cases =
 * tab.length. Un tableau d'entiers créé possède nb élements qui ne correspond pas forcément à la
 * taille du tableau. Il faut donc toujours considérer que nb &lt;= tab.length (= taille). Il est
 * fait usage de la classe SimplesTableau pour accéder aux méthodes de cette classe
 *
 * @author EDM115
 */
public class TrisTableau {

  /** Variable globale, compteur du nombre d'opérations */
  long cpt;

  /** Variable globale permettant l'accès aux méthodes de la classe SimplesTableau */
  SimplesTableau monSimpleTab = new SimplesTableau();

  /** Le point d'entrée du programme */
  void principal() {
    System.out.println("\t\t\u001B[33mTests :\u001B[0m");
    testRechercheSeq();
    testVerifTri();
    testRechercheDicho();
    testTriSimple();
    testSeparer();
    testTriRapideRec();
    testTriRapide();
    testCreerTabFreq();
    testTriParComptageFreq();
    testTriABulles();
    System.out.println("");
    System.out.println("");
    System.out.println("\t\t\u001B[33mEfficacité :\u001B[0m");
    testRechercheSeqEfficacite();
    testRechercheDichoEfficacite();
    testTriSimpleEfficacite();
    testTriRapideEfficacite();
    testTriParComptageFreqEfficacite();
    testTriABullesEfficacite();
  }

  /**
   * Recherche séquentielle d'une valeur dans un tableau. La valeur à rechercher peut exister en
   * plusieurs exemplaires mais la recherche s'arrête dès qu'une première valeur est trouvée. On
   * suppose que le tableau passé en paramètre est créé et possède au moins une valeur (nb &gt; 0).
   * Ceci ne doit donc pas être vérifié
   *
   * @param tab le tableau dans lequel effectuer la recherche
   * @param nb le nombre d'entiers que contient le tableau
   * @param aRech l'entier à rechercher dans le tableau
   * @return l'indice (&lt;= 0) de la position de l'entier dans le tableau ou -1 s'il n'est pas
   *     présent
   */
  int rechercheSeq(int[] tab, int nb, int aRech) {
    int i = 0;
    boolean found = false;

    while (!found && i < nb) {
      if (tab[i] == aRech) {
        found = true;
      } else {
        i++;
        cpt++;
      }
    }
    if (!found) {
      i = -1;
    }

    return i;
  }

  void testRechercheSeq() {
    System.out.println("");
    System.out.println("\u001B[36m*** testRechercheSeq()\u001B[0m");
    // Cas normaux
    int[] tab = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasRechercheSeq(tab, 10, 10, 9);
    testCasRechercheSeq(tab, 10, 70, -1);
    // Cas limites
    int[] tab2 = {1};
    testCasRechercheSeq(tab2, 1, 1, 0);
  }

  void testCasRechercheSeq(int[] tab, int nb, int aRech, int result) {
    System.out.print(
        "testCasRechercheSeq(" + Arrays.toString(tab) + ", " + nb + ", " + aRech + ") : ");
    int temp = rechercheSeq(tab, nb, aRech);
    System.out.println(temp);
    if (temp == result) {
      System.out.println("\u001B[32m\tOK\u001B[0m");
    } else {
      System.err.println("\u001B[31m\tERREUR\u001B[0m");
    }
  }

  void testRechercheSeqEfficacite() {
    System.out.println("");
    System.out.println("\u001B[36m*** testRechercheSeqEfficacite()\u001B[0m");
    testCasRechercheSeqEfficacite(200000, 400001);
    testCasRechercheSeqEfficacite(500000, 800001);
    testCasRechercheSeqEfficacite(1000000, 5000001);
  }

  void testCasRechercheSeqEfficacite(int nb, int aRech) {
    cpt = 0;
    long time = 0;
    long start = 0;
    long stop = 0;
    int[] tab = new int[nb];
    monSimpleTab.remplirAleatoire(tab, nb, 0, aRech - 1);
    start = System.nanoTime();
    int tmp = rechercheSeq(tab, nb, aRech);
    stop = System.nanoTime();
    time = stop - start;
    System.out.print("rechercheSeq(tab{" + nb + "}, " + nb + ", " + aRech + ") : ");
    System.out.println("result : " + tmp + ", time : " + time + ", cpt : " + cpt);
  }

  /**
   * Vérifie que le tableau passé en paramètre est trié par ordre croissant des valeurs. On suppose
   * que le tableau passé en paramètre est créé et possède au moins une valeur (nb &gt; 0). Ceci ne
   * doit donc pas être vérifié
   *
   * @param tab le tableau à vérifier (trié en ordre croissant)
   * @param nb le nombre d'entiers présents dans le tableau
   * @return true si le tableau est trié
   */
  boolean verifTri(int[] tab, int nb) {
    boolean trie = true;
    int i = 1;
    int valeur1 = tab[0];
    int valeur2;

    while (trie && i < nb) {
      valeur2 = tab[i];
      if (valeur2 < valeur1) {
        trie = false;
      } else {
        valeur1 = valeur2;
        i++;
      }
    }

    return trie;
  }

  void testVerifTri() {
    System.out.println("");
    System.out.println("\u001B[36m*** testVerifTri()\u001B[0m");
    // Cas normaux
    int[] tab = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasVerifTri(tab, 10, true);
    int[] tab2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1};
    testCasVerifTri(tab2, 11, false);
    // Cas limites
    int[] tab3 = {1};
    testCasVerifTri(tab3, 1, true);
  }

  void testCasVerifTri(int[] tab, int nb, boolean result) {
    System.out.print("testCasVerifTri(" + Arrays.toString(tab) + ", " + nb + ") : ");
    boolean temp = verifTri(tab, nb);
    System.out.println(temp);
    if (temp == result) {
      System.out.println("\u001B[32m\tOK\u001B[0m");
    } else {
      System.err.println("\u001B[31m\tERREUR\u001B[0m");
    }
  }

  /**
   * Recherche dichotomique d'une valeur dans un tableau. On suppose que le tableau est trié par
   * ordre croissant. La valeur à rechercher peut exister en plusieurs exemplaires, dans ce cas,
   * c'est la valeur à l'indice le + faible qui sera trouvé. On suppose également que le tableau
   * passé en paramètre est créé et possède au moins une valeur (nbElem &gt; 0). Ceci ne doit donc
   * pas être vérifié
   *
   * @param tab le tableau trié par ordre croissant dans lequel effectuer la recherche
   * @param nb le nombre d'entiers que contient le tableau
   * @param aRech l'entier à rechercher dans le tableau
   * @return l'indice (&gt;=0) de la position de l'entier dans le tableau ou -1 s'il n'est pas
   *     présent
   */
  int rechercheDicho(int[] tab, int nb, int aRech) {
    int indD = 0;
    int indF = nb - 1;
    int indM = 0;
    int indice = 0;

    while (indD != indF) {
      indM = (indD + indF) / 2;
      if (aRech > tab[indM]) {
        indD = indM + 1;
      } else {
        indF = indM;
      }
      cpt++;
    }
    if (aRech == tab[indD]) {
      indice = indD;
    } else {
      indice = -1;
    }

    return indice;
  }

  void testRechercheDicho() {
    System.out.println("");
    System.out.println("\u001B[36m*** testRechercheDicho()\u001B[0m");
    // Cas normaux
    int[] tab = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasRechercheDicho(tab, 10, 10, 9);
    testCasRechercheDicho(tab, 10, 70, -1);
    // Cas limites
    int[] tab2 = {1};
    testCasRechercheDicho(tab2, 1, 1, 0);
  }

  void testCasRechercheDicho(int[] tab, int nb, int aRech, int result) {
    System.out.print(
        "testCasRechercheDicho(" + Arrays.toString(tab) + ", " + nb + ", " + aRech + ") : ");
    int temp = rechercheDicho(tab, nb, aRech);
    System.out.println(temp);
    if (temp == result) {
      System.out.println("\u001B[32m\tOK\u001B[0m");
    } else {
      System.err.println("\u001B[31m\tERREUR\u001B[0m");
    }
  }

  void testRechercheDichoEfficacite() {
    System.out.println("");
    System.out.println("\u001B[36m*** testRechercheDichoEfficacite()\u001B[0m");
    testCasRechercheDichoEfficacite(200000, 400001);
    testCasRechercheDichoEfficacite(500000, 800001);
    testCasRechercheDichoEfficacite(1000000, 5000001);
  }

  void testCasRechercheDichoEfficacite(int nb, int aRech) {
    cpt = 0;
    long time = 0;
    long start = 0;
    long stop = 0;
    int[] tab = new int[nb];
    for (int i = 0; i < nb; i++) {
      tab[i] = i;
    }
    start = System.nanoTime();
    int tmp = rechercheDicho(tab, nb, aRech);
    stop = System.nanoTime();
    time = stop - start;
    System.out.print("rechercheDicho(tab{" + nb + "}, " + nb + ", " + aRech + ") : ");
    System.out.println("result : " + tmp + ", time : " + time + ", cpt : " + cpt);
  }

  /**
   * Tri par ordre croissant d'un tableau selon la méthode simple : l'élément minimum est placé en
   * début de tableau (efficacité en n carré). On suppose que le tableau passé en paramètre est créé
   * et possède au moins une valeur (nb &gt; 0). Ceci ne doit donc pas être vérifié
   *
   * @param tab le tableau à trier par ordre croissant
   * @param nb le nombre d'entiers que contient le tableau
   */
  void triSimple(int[] tab, int nb) {
    int i = 0;
    int j = 0;
    int min = 0;

    while (i < nb) {
      min = tab[i];
      j = i + 1;
      while (j < nb) {
        if (tab[j] < min) {
          min = tab[j];
          monSimpleTab.echange(tab, nb, i, j);
        }
        j++;
        cpt++;
      }
      i++;
    }
  }

  void testTriSimple() {
    System.out.println("");
    System.out.println("\u001B[36m*** testTriSimple()\u001B[0m");
    // Cas normaux
    int[] tab = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasTriSimple(tab, 10, result);
    int[] tab2 = {8, 5, 6, 9, 10, 0, 3, 1, 2, 4, 7};
    int[] result2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasTriSimple(tab2, 11, result2);
    int[] tab3 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    testCasTriSimple(tab3, 10, result);
    // Cas limites
    int[] tab4 = {1};
    int[] result3 = {1};
    testCasTriSimple(tab4, 1, result3);
  }

  void testCasTriSimple(int[] tab, int nb, int[] result) {
    System.out.print("testCasTriSimple(" + Arrays.toString(tab) + ", " + nb + ") : ");
    triSimple(tab, nb);
    System.out.println(Arrays.toString(tab));
    if (Arrays.equals(tab, result)) {
      System.out.println("\u001B[32m\tOK\u001B[0m");
    } else {
      System.err.println("\u001B[31m\tERREUR\u001B[0m");
    }
  }

  void testTriSimpleEfficacite() {
    System.out.println("");
    System.out.println("\u001B[36m*** testTriSimpleEfficacite()\u001B[0m");
    testCasTriSimpleEfficacite(2000);
    testCasTriSimpleEfficacite(5000);
    testCasTriSimpleEfficacite(15000);
  }

  void testCasTriSimpleEfficacite(int nb) {
    cpt = 0;
    long time = 0;
    long start = 0;
    long stop = 0;
    int[] tab = new int[nb];
    monSimpleTab.remplirAleatoire(tab, nb, 0, nb * 2);
    start = System.nanoTime();
    triSimple(tab, nb);
    stop = System.nanoTime();
    time = stop - start;
    System.out.print("triSimple(tab{" + nb + "}, " + nb + ") : ");
    System.out.println("time : " + time + ", cpt : " + cpt);
  }

  /**
   * Cette méthode renvoie l'indice de séparation du tableau en 2 parties par placement du pivot à
   * la bonne case
   *
   * @param tab le tableau des valeurs
   * @param indL indice Left de début de tableau
   * @param indR indice Right de fin de tableau
   * @return l'indice de séparation du tableau
   */
  int separer(int[] tab, int indL, int indR) {
    int pivot = indL;
    while (indR != indL) {
      if (pivot == indL) {
        if (tab[indL] <= tab[indR]) {
          indR--;
        } else {
          monSimpleTab.echange(tab, tab.length, indL, indR);
          indL++;
          pivot = indR;
        }
      } else {
        if (tab[indR] >= tab[indL]) {
          indL++;
        } else {
          monSimpleTab.echange(tab, tab.length, indL, indR);
          indR--;
          pivot = indL;
        }
      }
      cpt++;
    }

    return pivot;
  }

  void testSeparer() {
    System.out.println("");
    System.out.println("\u001B[36m*** testSeparer()\u001B[0m");
    // Cas normaux
    int[] tab = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasSeparer(tab, 0, 9, 0);
    int[] tab2 = {8, 5, 6, 9, 10, 0, 3, 1, 2, 4, 7};
    testCasSeparer(tab2, 0, 10, 8);
    int[] tab3 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    testCasSeparer(tab3, 0, 9, 9);
    int[] tab4 = {5, 7, 9, 1, 3, 2, 4, 6, 8, 10};
    testCasSeparer(tab4, 0, 9, 4);
    // Cas limites
    int[] tab5 = {1};
    testCasSeparer(tab5, 0, 0, 0);
  }

  void testCasSeparer(int[] tab, int indL, int indR, int result) {
    System.out.print("testCasSeparer(" + Arrays.toString(tab) + ", " + indL + ", " + indR + ") : ");
    int temp = separer(tab, indL, indR);
    System.out.println(temp);
    if (temp == result) {
      System.out.println("\u001B[32m\tOK\u001B[0m");
    } else {
      System.err.println("\u001B[31m\tERREUR\u001B[0m");
    }
  }

  /**
   * Méthode de tri récursive selon le principe de séparation. La méthode s'appelle elle-même sur
   * les tableaux gauche et droite par rapport à un pivot
   *
   * @param tab le tableau sur lequel est effectué la séparation
   * @param indL l'indice gauche de début de tableau
   * @param indR l'indice droite de fin de tableau
   */
  void triRapideRec(int[] tab, int indL, int indR) {
    int pivot = separer(tab, indL, indR);

    if (pivot - 1 > indL) {
      triRapideRec(tab, indL, pivot - 1);
    } else if (pivot + 1 < indR) {
      triRapideRec(tab, pivot + 1, indR);
    }
  }

  void testTriRapideRec() {
    System.out.println("");
    System.out.println("\u001B[36m*** testTriRapideRec()\u001B[0m");
    // Cas normaux
    int[] tab = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasTriRapideRec(tab, 0, 9, result);
    int[] tab2 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    int[] result2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasTriRapideRec(tab2, 0, 9, result2);
    // Cas limites
    int[] tab4 = {1};
    int[] result4 = {1};
    testCasTriRapideRec(tab4, 0, 0, result4);
  }

  void testCasTriRapideRec(int[] tab, int indL, int indR, int[] result) {
    System.out.print(
        "testCasTriRapideRec(" + Arrays.toString(tab) + ", " + indL + ", " + indR + ") : ");
    triRapideRec(tab, indL, indR);
    System.out.println(Arrays.toString(tab));
    if (Arrays.equals(tab, result)) {
      System.out.println("\u001B[32m\tOK\u001B[0m");
    } else {
      System.err.println("\u001B[31m\tERREUR\u001B[0m");
    }
  }

  /**
   * Tri par ordre croissant d'un tableau selon la méthode du tri rapide (QuickSort). On suppose que
   * le tableau passé en paramètre est créé et possède au moins une valeur (nb &gt; 0). Ceci ne doit
   * donc pas être vérifié. Cette méthode appelle triRapideRec(...) qui elle effectue réellement le
   * tri rapide selon la méthode de séparation récursive
   *
   * @param tab le tableau à trier par ordre croissant
   * @param nb le nombre d'entiers que contient le tableau
   */
  void triRapide(int[] tab, int nb) {
    boolean tri = verifTri(tab, tab.length);

    while (!tri) {
      triRapideRec(tab, 0, tab.length - 1);
      tri = verifTri(tab, tab.length);
    }
  }

  void testTriRapide() {
    System.out.println("");
    System.out.println("\u001B[36m*** testTriRapide()\u001B[0m");
    // Cas normaux
    int[] tab = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasTriRapide(tab, 10, result);
    int[] tab2 = {8, 5, 6, 9, 10, 0, 3, 1, 2, 4, 7};
    int[] result2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasTriRapide(tab2, 11, result2);
    int[] tab3 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    int[] result3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasTriRapide(tab3, 10, result3);
    int[] tab4 = {5, 7, 9, 1, 3, 2, 4, 6, 8, 10};
    int[] result4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasTriRapide(tab4, 10, result4);
    // Cas limites
    int[] tab5 = {1};
    int[] result5 = {1};
    testCasTriRapide(tab5, 1, result5);
  }

  void testCasTriRapide(int[] tab, int nb, int[] result) {
    System.out.print("testCasTriRapide(" + Arrays.toString(tab) + ", " + nb + ") : ");
    triRapide(tab, nb);
    System.out.println(Arrays.toString(tab));
    if (Arrays.equals(tab, result)) {
      System.out.println("\u001B[32m\tOK\u001B[0m");
    } else {
      System.err.println("\u001B[31m\tERREUR\u001B[0m");
    }
  }

  void testTriRapideEfficacite() {
    System.out.println("");
    System.out.println("\u001B[36m*** testTriRapideEfficacite()\u001B[0m");
    testCasTriRapideEfficacite(2000);
    testCasTriRapideEfficacite(5000);
    testCasTriRapideEfficacite(8000);
  }

  void testCasTriRapideEfficacite(int nb) {
    cpt = 0;
    long time = 0;
    long start = 0;
    long stop = 0;
    int[] tab = new int[nb];
    monSimpleTab.remplirAleatoire(tab, nb, 0, nb * 2);
    start = System.nanoTime();
    triRapide(tab, nb);
    stop = System.nanoTime();
    time = stop - start;
    System.out.print("triRapide(tab{" + nb + "}, " + nb + ") : ");
    System.out.println("time : " + time + ", cpt : " + cpt);
  }

  /**
   * A partir d'un tableau initial passé en paramètre "tab", cette méthode renvoie un nouveau
   * tableau "tabFreq" d'entiers où chaque case contient la fréquence d'apparition des valeurs dans
   * le tableau initial. Pour simplifier, on suppose que le tableau initial ne contient que des
   * entiers compris entre 0 et max (&gt; 0). Dès lors le tableau "tabFreq" se compose de (max + 1)
   * cases et chaque case "i" (0 &lt;= i &lt;= max) contient le nombre de fois que l'entier "i"
   * apparait dans le tableau initial. On suppose que le tableau passé en paramètre est créé et
   * possède au moins une valeur (nb &gt; 0). Ceci ne doit donc pas être vérifié. Par contre, on
   * vérifiera que le max du tableau initial est &gt;= min et que le min est &gt;= 0. Dans le cas
   * contraire, renvoyer un tableau "null"
   *
   * @param tab le tableau initial
   * @param nb le nombre d'entiers présents dans le tableau
   * @return le tableau des fréquences de taille (max + 1) ou null si la méthode ne s'applique pas
   */
  int[] creerTabFreq(int[] tab, int nb) {
    int[] tabFreq = null;
    int max = monSimpleTab.max(tab, nb);
    int min = 0;

    for (int j = 0; j < nb; j++) {
      if (tab[j] < min) {
        min = tab[j];
      }
    }
    if (min >= 0 && min < max) {
      tabFreq = new int[max + 1];
      int i = 0;
      while (i < nb) {
        tabFreq[tab[i]] = tabFreq[tab[i]] + 1;
        i++;
      }
    }

    return tabFreq;
  }

  void testCreerTabFreq() {
    System.out.println("");
    System.out.println("\u001B[36m*** testCreerTabFreq()\u001B[0m");
    // Cas normaux
    int[] tab1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] result1 = {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    testCasCreerTabFreq(tab1, 10, result1);
    int[] tab2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] result2 = {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
    testCasCreerTabFreq(tab2, 20, result2);
    int[] tab3 = {
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    };
    int[] result3 = {0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
    testCasCreerTabFreq(tab3, 30, result3);
    int[] tab4 = {
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    };
    int[] result4 = {0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
    testCasCreerTabFreq(tab4, 40, result4);
    int[] tab5 = {
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    };
    int[] result5 = {0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
    testCasCreerTabFreq(tab5, 50, result5);
    int[] tab6 = {1, 2, 3, 4, 8, 7, 1, 0, 5, 8, 9, 0};
    int[] result6 = {2, 2, 1, 1, 1, 1, 0, 1, 2, 1};
    testCasCreerTabFreq(tab6, 12, result6);
    int[] tab7 = {0, 5, 9, 21, 9};
    int[] result7 = {1, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
    testCasCreerTabFreq(tab7, 5, result7);
  }

  void testCasCreerTabFreq(int[] tab, int nb, int[] result) {
    System.out.print("testCasCreerTabFreq(" + Arrays.toString(tab) + ", " + nb + ") : ");
    int[] temp = creerTabFreq(tab, nb);
    System.out.println(Arrays.toString(temp));
    if (Arrays.equals(temp, result)) {
      System.out.println("\u001B[32m\tOK\u001B[0m");
    } else {
      System.err.println("\u001B[31m\tERREUR\u001B[0m");
    }
  }

  /**
   * Tri par ordre croissant d'un tableau selon la méthode du tri par comptage de fréquences. On
   * suppose que le tableau passé en paramètre est créé et possède au moins une valeur (nb &gt; 0).
   * Ceci ne doit donc pas être vérifié
   *
   * @param tab le tableau à trier par ordre croissant
   * @param nb le nombre d'entiers que contient le tableau
   */
  void triParComptageFreq(int[] tab, int nb) {
    int i = 0;
    int j = 0;
    int[] tabFreq = creerTabFreq(tab, nb);

    try {
      int len = tabFreq.length;
      while (i < len) {
        int val = tabFreq[i];
        while (val > 0) {
          tab[j] = i;
          val--;
          j++;
          cpt++;
        }
        i++;
      }
    } catch (NullPointerException e) {
      System.err.println("Erreur : tabFreq est null, tab na sera pas trié (" + e + ")");
    }
  }

  void testTriParComptageFreq() {
    System.out.println("");
    System.out.println("\u001B[36m*** testTriParComptageFreq() :\u001B[0m");
    int[] tab1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] result1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasTriParComptageFreq(tab1, 10, result1);
    int[] tab2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] result2 = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10};
    testCasTriParComptageFreq(tab2, 20, result2);
    int[] tab3 = {
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    };
    int[] result3 = {
      1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9, 9, 10, 10, 10
    };
    testCasTriParComptageFreq(tab3, 30, result3);
    int[] tab4 = {1, 2, 3, 4, 8, 7, 1, 0, 5, 8, 9, 0};
    int[] result4 = {0, 0, 1, 1, 2, 3, 4, 5, 7, 8, 8, 9};
    testCasTriParComptageFreq(tab4, 12, result4);
    int[] tab5 = {0, 5, 9, 21, 9};
    int[] result5 = {0, 5, 9, 9, 21};
    testCasTriParComptageFreq(tab5, 5, result5);
    // Cas d'erreur
    int[] tab6 = {-1, 2};
    int[] result6 = {-1, 2};
    testCasTriParComptageFreq(tab6, 2, result6);
  }

  void testCasTriParComptageFreq(int[] tab, int nb, int[] result) {
    System.out.print("testCasTriParComptageFreq(" + Arrays.toString(tab) + ", " + nb + ") : ");
    triParComptageFreq(tab, nb);
    System.out.println(Arrays.toString(tab));
    if (Arrays.equals(tab, result)) {
      System.out.println("\u001B[32m\tOK\u001B[0m");
    } else {
      System.err.println("\u001B[31m\tERREUR\u001B[0m");
    }
  }

  void testTriParComptageFreqEfficacite() {
    System.out.println("");
    System.out.println("\u001B[36m*** testTriParComptageFreqEfficacite()\u001B[0m");
    testCasTriParComptageFreqEfficacite(2000);
    testCasTriParComptageFreqEfficacite(5000);
    testCasTriParComptageFreqEfficacite(8000);
  }

  void testCasTriParComptageFreqEfficacite(int nb) {
    cpt = 0;
    long time = 0;
    long start = 0;
    long stop = 0;
    int[] tab = new int[nb];
    monSimpleTab.remplirAleatoire(tab, nb, 0, nb * 2);
    start = System.nanoTime();
    triParComptageFreq(tab, nb);
    stop = System.nanoTime();
    time = stop - start;
    System.out.print("triParCompategFreq(tab{" + nb + "}, " + nb + ") : ");
    System.out.println("time : " + time + ", cpt : " + cpt);
  }

  /**
   * Tri par ordre croissant d'un tableau selon la méthode du tri à bulles : tant que le tableau
   * n'est pas trié, permuter le contenu de 2 cases successives si tab[i] &gt; tab[i + 1]. On
   * suppose que le tableau passé en paramètre est créé et possède au moins une valeur (nb &gt; 0).
   * Ceci ne doit donc pas être vérifié
   *
   * @param tab le tableau à trier par ordre croissant
   * @param nb le nombre d'entiers que contient le tableau
   */
  void triABulles(int[] tab, int nb) {
    while (nb > 0) {
      int j = 0;
      while (j < nb - 1) {
        if (tab[j] > tab[j + 1]) {
          monSimpleTab.echange(tab, nb, j, j + 1);
          cpt++;
        }
        j++;
      }
      nb--;
    }
  }

  void testTriABulles() {
    System.out.println("");
    System.out.println("\u001B[36m*** testTriABulles()\u001B[0m");
    // Cas normaux
    int[] tab = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasTriABulles(tab, 10, tab);
    int[] tab2 = {8, 5, 6, 9, 10, 0, 3, 1, 2, 4, 7};
    int[] result = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    testCasTriABulles(tab2, 11, result);
    int[] tab3 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    testCasTriABulles(tab3, 10, tab);
    // Cas limites
    int[] tab4 = {1};
    testCasTriABulles(tab4, 1, tab4);
  }

  void testCasTriABulles(int[] tab, int nb, int[] result) {
    System.out.print("testCasTriABulles(" + Arrays.toString(tab) + ", " + nb + ") : ");
    triABulles(tab, nb);
    System.out.println(Arrays.toString(tab));
    if (Arrays.equals(tab, result)) {
      System.out.println("\u001B[32m\tOK\u001B[0m");
    } else {
      System.err.println("\u001B[31m\tERREUR\u001B[0m");
    }
  }

  void testTriABullesEfficacite() {
    System.out.println("");
    System.out.println("\u001B[36m*** testTriABullesEfficacite()\u001B[0m");
    testCasTriABullesEfficacite(2000);
    testCasTriABullesEfficacite(5000);
    testCasTriABullesEfficacite(8000);
  }

  void testCasTriABullesEfficacite(int nb) {
    cpt = 0;
    long time = 0;
    long start = 0;
    long stop = 0;
    int[] tab = new int[nb];
    monSimpleTab.remplirAleatoire(tab, nb, 0, nb * 2);
    start = System.nanoTime();
    triABulles(tab, nb);
    stop = System.nanoTime();
    time = stop - start;
    System.out.print("triABulles(tab{" + nb + "}, " + nb + ") : ");
    System.out.println("time : " + time + ", cpt : " + cpt);
  }
}
