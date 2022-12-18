/**
 * Elimine les valeurs en double d'un tableau
 *
 * @author EDM115
 */
import java.util.Arrays;

class ElimineDouble {
  void principal() {
    testEliminerDouble();
  }

  /**
   * élimine les valeurs en plusieurs exemplaires dans un tableau un élément présent plusieurs fois
   * n'est plus qu'en un seul exemplaire chaque élément en double est décalé à la fin du tableau
   * sans être modifié l'ordre des valeurs n'a pas d'importance
   *
   * @param tab tableau d'entiers
   * @return le nombre d'éléments du tableau sans double
   */
  int eliminerDouble(int[] tab) {
    int nb = tab.length;
    int i = 0;
    while (i < nb) {
      int j = i + 1;
      while (j < nb) {
        if (tab[i] == tab[j]) {
          int temp = tab[j];
          tab[j] = tab[nb - 1];
          tab[nb - 1] = temp;
          nb--;
          j--;
        }
        j++;
      }
      i++;
    }
    return nb;
  }

  /** Teste la méthode eliminerDouble() */
  void testEliminerDouble() {
    System.out.println();
    System.out.println("*** testEliminerDouble()");
    int[] tab1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] tab2 = {2, 8, 3, -1, 8, -9, 56, 8, -127, 0, 1};
    int[] tab3 = {9, 8, 7, 6, 5, 5, 4, 3, 2, 1, 0, 9};
    int result1 = 1;
    int result2 = 9;
    int result3 = 10;
    testCasEliminerDouble(tab1, result1);
    testCasEliminerDouble(tab2, result2);
    testCasEliminerDouble(tab3, result3);
  }

  /**
   * teste un appel de eliminerDouble
   *
   * @param tab le tableau avec potentiellement des doubles
   * @param result le nombre d'élements du tableau sans double
   */
  void testCasEliminerDouble(int[] tab, int result) {
    // Arrange
    System.out.println("");
    System.out.print("testCasEliminerDouble(" + Arrays.toString(tab) + ") \t= " + result + "\t : ");
    // Act
    int resExec = eliminerDouble(tab);
    // Assert
    if (resExec == result) {
      System.out.println("OK");
    } else {
      System.err.println("ERREUR");
    }
    System.out.print(Arrays.toString(tab));
  }
}
