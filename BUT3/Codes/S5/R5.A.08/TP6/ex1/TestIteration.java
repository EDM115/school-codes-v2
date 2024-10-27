import java.awt.Color;
import java.util.Iterator;

public class TestIteration {
  public static void main(String[] args) {
    FeuTricolor feuTricolor = new FeuTricolor();
    Iterator<Color> feuIterator = feuTricolor.iterator();

    System.out.println("Défilement des couleurs du FeuTricolor :");
    // Afficher les 10 premières couleurs de la séquence pour démonstration
    for (int i = 0; i < 10; i++) {
      Color color = feuIterator.next();
      if (color == Color.RED) {
        System.out.println("Rouge");
      } else if (color == Color.YELLOW) {
        System.out.println("Jaune");
      } else if (color == Color.GREEN) {
        System.out.println("Vert");
      }
    }

    FeuilleSalaire feuilleSalaire = new FeuilleSalaire("ABC Corp", 3000.0, 160, 500.0, 2500.0);

    System.out.println("\nInformations de la FeuilleSalaire :");
    // Utilisation de l'itérateur pour afficher les attributs de FeuilleSalaire
    for (Object valeur : feuilleSalaire) {
      System.out.println(valeur);
    }
  }
}
