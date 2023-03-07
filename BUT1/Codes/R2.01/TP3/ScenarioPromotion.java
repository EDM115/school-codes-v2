/**
 * Tests the Promotion class
 *
 * @author EDM115
 */
public class ScenarioPromotion {
  /**
   * Main method
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    System.out.println("\t\u001B[33mTests Promotion :\u001B[0m");

    System.out.println("");
    System.out.println("\u001B[36m*** Promotion()\u001B[0m");
    String[] matieres = {"Math", "NSI", "Arts"};
    double[] coeff1 = {1.5, 2, 1};
    String nom1 = "Nathan";
    int nb1 = 6;
    Etudiant etudiant1 = new Etudiant(nom1, matieres, coeff1, nb1);

    double[] coeff2 = {1, 2.5, 0};
    String nom2 = "Ronflex";
    int nb2 = 5;
    Etudiant etudiant2 = new Etudiant(nom2, matieres, coeff2, nb2);

    double[] coeff3 = {1.8, 0.5, 0.2};
    String nom3 = "Test";
    int nb3 = 2;
    Etudiant etudiant3 = new Etudiant(nom3, matieres, coeff3, nb3);

    Etudiant[] etudiants = {etudiant1, etudiant2, etudiant3};
    String nom = "Pallan Paccrez";
    Promotion promo = new Promotion(nom, etudiants);
    System.out.println("Nathan, Ronflex, Test -> Promotion(" + nom + ") = \n" + promo.toString());

    System.out.println("");
    System.out.println("\u001B[36m*** getNom()\u001B[0m");
    System.out.println("Pallan Paccrez -> getNom() = " + promo.getNom());

    System.out.println("");
    System.out.println("\u001B[36m*** setNom()\u001B[0m");
    String nom4 = "Promo Info 2023";
    promo.setNom(nom4);
    System.out.println("Pallan Paccrez -> setNom(" + nom4 + ") = " + promo.getNom());

    System.out.println("");
    System.out.println("\u001B[36m*** moyenne()\u001B[0m");
    System.out.println("Promo Info 2023 -> moyenne() = " + promo.moyenne());

    System.out.println("");
    System.out.println("\u001B[36m*** moyenneMax()\u001B[0m");
    System.out.println("Promo Info 2023 -> moyenneMax() = " + promo.moyenneMax());

    System.out.println("");
    System.out.println("\u001B[36m*** moyenneMin()\u001B[0m");
    System.out.println("Promo Info 2023 -> moyenneMin() = " + promo.moyenneMin());

    System.out.println("");
    System.out.println("\u001B[36m*** moyenneMatiere()\u001B[0m");
    System.out.println(
        "Promo Info 2023 -> moyenneMatiere(" + matieres[0] + ") = " + promo.moyenneMatiere(0));
    System.out.println(
        "Promo Info 2023 -> moyenneMatiere(" + matieres[1] + ") = " + promo.moyenneMatiere(1));
    System.out.println(
        "Promo Info 2023 -> moyenneMatiere(" + matieres[2] + ") = " + promo.moyenneMatiere(2));

    System.out.println("");
    System.out.println("\u001B[36m*** getMajor()\u001B[0m");
    System.out.println("Promo Info 2023 -> getMajor() = " + promo.getMajor());

    System.out.println("");
    System.out.println("\u001B[36m*** getEtudiant()\u001B[0m");
    System.out.println("Promo Info 2023 -> getEtudiant(" + nom1 + ") = " + promo.getEtudiant(nom1));
    System.out.println("Promo Info 2023 -> getEtudiant(" + nom2 + ") = " + promo.getEtudiant(nom2));
    System.out.println("Promo Info 2023 -> getEtudiant(" + nom3 + ") = " + promo.getEtudiant(nom3));

    System.out.println("");
    System.out.println("\u001B[36m*** toString()\u001B[0m");
    System.out.println("Promo Info 2023 -> toString() = \n" + promo.toString());
  }
}
