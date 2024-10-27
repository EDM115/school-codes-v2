public class Ventilateur {
  private int vitesse = 0;

  public void allumer() {
    System.out.println("Ventilateur allumé");
  }

  public void eteindre() {
    System.out.println("Ventilateur éteint");
  }

  public void augmenterVitesse() {
    if (vitesse < 3) {
      vitesse++;
      System.out.println("Vitesse du ventilateur augmentée à " + vitesse);
    } else {
      System.out.println("Vitesse maximale atteinte");
    }
  }

  public void diminuerVitesse() {
    if (vitesse > 0) {
      vitesse--;
      System.out.println("Vitesse du ventilateur diminuée à " + vitesse);
    } else {
      System.out.println("Ventilateur déjà à l'arrêt");
    }
  }
}
