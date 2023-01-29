/**
 * Determine les valeurs communes de 2 tableaux
 *
 * @author EDM115
 */
class Intersection {
  void principal() {}

  /**
   * calcule les valeurs communes de 2 tableaux
   *
   * @param tab1 tableau d'entiers
   * @param tab2 tableau d'entiers
   * @return tableau d'entiers contenant les valeurss communes aux 2 premiers tableaux sans doublons
   */
  int[] intersection(int[] tab1, int[] tab2) {
    int nbValues, i, k, l = 0;
    boolean present = false;
    int l1 = tab1.length;
    int l2 = tab2.length;
    if (l1 < l2) {
      int len = l1;
    } else {
      int len = l2;
    }
    int[] tab3 = new int[len];

    while (i < l1) {
      int j = 0;
      while (j < l2) {
        if (tab1[i] == tab2[j]) {
          while (!present && k <= nbValues) {
            if (tab1[i] == tab3[k]) {
              present = true;
            }
            k++;
          }
          if (!present) {
            tab3[nbValues] = tab1[1];
            nbValues++;
          }
          present = false;
        }
        j++;
      }
      i++;
    }
    int[] tab4 = new int[nbValues];
    while (l < nbValues) {
      tab4[l] = tab3[l];
      l++;
    }
    return tab4;
  }
}
