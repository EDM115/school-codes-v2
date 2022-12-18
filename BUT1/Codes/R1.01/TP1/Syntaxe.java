/**
 * @author M.Adam
 */
class Syntaxe {
  void principal() {

    int val1;
    int val2;
    int val3;

    val1 = SimpleInput.getInt("Premier entier :");
    val2 = SimpleInput.getInt("Deuxième entier :");
    val3 = SimpleInput.getInt("Troisième entier :");

    if (val1 < val3 && val2 < val3) {
      System.out.println("Le troisième entier " + val3 + " est le plus grand.");
    } else if (val1 < val2 && val3 < val2) {
      System.out.println("Le deuxième entier " + val2 + " est le plus grand.");
    } else if (val2 < val1 && val3 < val1) {
      System.out.println("Le premier entier " + val1 + " est le plus grand.");
    }
  }
}
