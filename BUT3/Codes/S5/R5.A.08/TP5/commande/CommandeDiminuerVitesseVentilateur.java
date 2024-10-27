public class CommandeDiminuerVitesseVentilateur implements Commande {
  private Ventilateur ventilateur;

  public CommandeDiminuerVitesseVentilateur(Ventilateur ventilateur) {
    this.ventilateur = ventilateur;
  }

  @Override
  public void executer() {
    ventilateur.diminuerVitesse();
  }
}
