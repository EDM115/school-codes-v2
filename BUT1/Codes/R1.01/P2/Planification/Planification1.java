import java.util.ArrayList;

/**
 * @author EDM115
 */
public class Planification1 {

  void principal() {
    testAffichages();
  }

  void testAffichages() {
    ArrayList<String> trajets = new ArrayList<String>();
    ArrayList<Integer> horaires = new ArrayList<Integer>();
    System.out.println("");
    System.out.println("\u001B[36mremplirLesCollections(trajets, horaires);\u001B[0m");
    remplirLesCollections(trajets, horaires);
    System.out.println("");
    System.out.println("\u001B[36mafficherHorairesEtDureeTousTrajets(trajets, horaires);\u001B[0m");
    afficherHorairesEtDureeTousTrajets(trajets, horaires);
    System.out.println("");
    System.out.println(
        "\u001B[36mafficherHorairesEtDureeTrajets2Gares(trajets, horaires, \"Vannes\","
            + " \"Redon\")\u001B[0m");
    afficherHorairesEtDureeTrajets2Gares(trajets, horaires, "Vannes", "Redon");
    System.out.println("");
    System.out.println(
        "\u001B[36mafficherHorairesEtDureeTrajets2Gares(trajets, horaires, \"Paris\","
            + " \"Marseille\")\u001B[0m");
    afficherHorairesEtDureeTrajets2Gares(trajets, horaires, "Paris", "Marseille");
  }

  void remplirLesCollections(ArrayList<String> trajets, ArrayList<Integer> horaires) {
    trajets.add("01");
    trajets.add("TER");
    trajets.add("Vannes");
    trajets.add("Redon");

    trajets.add("02");
    trajets.add("TGV");
    trajets.add("Vannes");
    trajets.add("Redon");

    trajets.add("03");
    trajets.add("TER");
    trajets.add("Redon");
    trajets.add("Nantes");

    trajets.add("04");
    trajets.add("TER");
    trajets.add("Redon");
    trajets.add("Nantes");

    trajets.add("05");
    trajets.add("TGV");
    trajets.add("Vannes");
    trajets.add("Nantes");

    trajets.add("06");
    trajets.add("TGV");
    trajets.add("Vannes");
    trajets.add("Nantes");

    horaires.add(01);
    horaires.add(9);
    horaires.add(35);
    horaires.add(10);
    horaires.add(30);

    horaires.add(02);
    horaires.add(8);
    horaires.add(0);
    horaires.add(10);
    horaires.add(05);

    horaires.add(03);
    horaires.add(11);
    horaires.add(0);
    horaires.add(12);
    horaires.add(30);

    horaires.add(04);
    horaires.add(14);
    horaires.add(15);
    horaires.add(15);
    horaires.add(37);

    horaires.add(05);
    horaires.add(10);
    horaires.add(03);
    horaires.add(12);
    horaires.add(11);

    horaires.add(06);
    horaires.add(11);
    horaires.add(25);
    horaires.add(13);
    horaires.add(38);
  }

  void afficherHorairesEtDureeTousTrajets(ArrayList<String> trajets, ArrayList<Integer> horaires) {
    System.out.println("");
    int trajetLen = trajets.size();
    int horairesLen = horaires.size();
    if (trajetLen % 4 != 0 && !trajets.isEmpty()) {
      System.err.println(
          "\u001B[31mtrajets n'est pas correct (il doit manquer une valeur)\u001B[0m");
    } else if (horairesLen % 5 != 0 && !horaires.isEmpty()) {
      System.err.println(
          "\u001B[31mhoraires n'est pas correct (il doit manquer une valeur)\u001B[0m");
    } else {
      int i = 0;
      int j = 0;
      while (i < trajetLen) {
        int num = Integer.parseInt(trajets.get(i));
        int indiceHoraires = 0;
        boolean found = false;
        while (!found && j < horairesLen) {
          int num2 = horaires.get(j);
          if (num2 == num) {
            found = true;
            indiceHoraires = j;
          }
          j += 5;
        }
        if (!found) {
          System.err.println(
              "\u001B[31mLe trajet " + num + " n'existe pas dans la table horaires\u001B[0m");
        } else {
          System.out.println("Train " + trajets.get(i + 1) + " numéro " + num + " :");
          Duree depart =
              new Duree(horaires.get(indiceHoraires + 1), horaires.get(indiceHoraires + 2), 0);
          Duree arrivee =
              new Duree(horaires.get(indiceHoraires + 3), horaires.get(indiceHoraires + 4), 0);
          System.out.println("\tDépart de " + trajets.get(i + 2) + " à " + depart.enTexte('H'));
          System.out.println("\tArrivée à " + trajets.get(i + 3) + " à " + arrivee.enTexte('H'));
          arrivee.soustraire(depart);
          System.out.println("\tDurée du trajet - " + arrivee.enTexte('H'));
        }
        i += 4;
      }
    }
  }

  void afficherHorairesEtDureeTrajets2Gares(
      ArrayList<String> trajets, ArrayList<Integer> horaires, String gareDep, String gareDest) {
    System.out.println("");
    int trajetLen = trajets.size();
    int horairesLen = horaires.size();
    if (trajetLen % 4 != 0 && !trajets.isEmpty()) {
      System.err.println(
          "\u001B[31mtrajets n'est pas correct (il doit manquer une valeur)\u001B[0m");
    } else if (horairesLen % 5 != 0 && !horaires.isEmpty()) {
      System.err.println(
          "\u001B[31mhoraires n'est pas correct (il doit manquer une valeur)\u001B[0m");
    } else {
      int i = 0;
      boolean found = false;
      boolean foundOnce = false;
      while (i < trajetLen) {
        if (trajets.get(i + 2).equals(gareDep) && trajets.get(i + 3).equals(gareDest)) {
          found = true;
          foundOnce = true;
        }
        if (found) {
          int j = 0;
          int indiceHoraires = 0;
          boolean found2 = false;
          int num = Integer.parseInt(trajets.get(i));
          while (!found2 && j < horairesLen) {
            int num2 = horaires.get(j);
            if (num2 == num) {
              found2 = true;
              indiceHoraires = j;
            }
            j += 5;
          }
          if (!found2) {
            System.err.println(
                "\u001B[31mLe trajet " + num + " n'existe pas dans la table horaires\u001B[0m");
          } else {
            System.out.println("Train " + trajets.get(i + 1) + " numéro " + num + " :");
            Duree depart =
                new Duree(horaires.get(indiceHoraires + 1), horaires.get(indiceHoraires + 2), 0);
            Duree arrivee =
                new Duree(horaires.get(indiceHoraires + 3), horaires.get(indiceHoraires + 4), 0);
            System.out.println("\tDépart de " + trajets.get(i + 2) + " à " + depart.enTexte('H'));
            System.out.println("\tArrivée à " + trajets.get(i + 3) + " à " + arrivee.enTexte('H'));
            arrivee.soustraire(depart);
            System.out.println("\tDurée du trajet - " + arrivee.enTexte('H'));
          }
        }
        i += 4;
        found = false;
      }
      if (!foundOnce) {
        System.err.println(
            "\u001B[31mLe trajet entre "
                + gareDep
                + " et "
                + gareDest
                + " n'existe pas dans la table trajets\u001B[0m");
      }
    }
  }
}
