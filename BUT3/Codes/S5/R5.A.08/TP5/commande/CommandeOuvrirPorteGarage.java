public class CommandeOuvrirPorteGarage implements Commande {
  private PorteGarage porteGarage;

  public CommandeOuvrirPorteGarage(PorteGarage porteGarage) {
    this.porteGarage = porteGarage;
  }

  @Override
  public void executer() {
    porteGarage.ouvrir();
  }
}
