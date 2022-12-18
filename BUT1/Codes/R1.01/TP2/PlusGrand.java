/**
 * Continue tant que le nombre saisi est supérieur au précédent
 *
 * @author EDM115
 */
class PlusGrand {
  void principal() {
    int valeur = SimpleInput.getInt("Entrez un nombre entier : ");
    int max = valeur;
    while (valeur >= max) {
      max = valeur;
      valeur = SimpleInput.getInt("Entrez un nombre entier : ");
    }
    System.out.println("Le plus grand nombre est " + max);
  }
}
