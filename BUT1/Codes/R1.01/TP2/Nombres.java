/**
 * Affiche une suite de nombres et leur moyenne
 *
 * @author EDM115
 */
class Nombres {
  void principal() {
    int valeur = SimpleInput.getInt("Entrez un nombre entier : ");
    int somme = 0;
    int moyenne = 0;
    int tours = 0;
    while (valeur != -1) {
      tours++;
      somme = somme + valeur;
      System.out.println(valeur);
      valeur = SimpleInput.getInt("Entrez un nombre entier : ");
    }
    if (tours != 0) {
      moyenne = somme / tours;
      System.out.println("La moyenne est de " + moyenne);
    } else {
      System.out.println("Aucune valeur n'a été entrée");
    }
  }
}
