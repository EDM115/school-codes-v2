public class CommandeFermerPorteGarage implements Commande {
  private PorteGarage porteGarage;

  public CommandeFermerPorteGarage(PorteGarage porteGarage) {
    this.porteGarage = porteGarage;
  }

  @Override
  public void executer() {
    porteGarage.fermer();
  }
}
