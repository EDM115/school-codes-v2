public class CommandeAugmenterVitesseVentilateur implements Commande {
  private Ventilateur ventilateur;

  public CommandeAugmenterVitesseVentilateur(Ventilateur ventilateur) {
    this.ventilateur = ventilateur;
  }

  @Override
  public void executer() {
    ventilateur.augmenterVitesse();
  }
}
