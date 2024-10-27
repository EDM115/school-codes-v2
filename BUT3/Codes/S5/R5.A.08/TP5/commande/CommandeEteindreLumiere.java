public class CommandeEteindreLumiere implements Commande {
  private Lumiere lumiere;

  public CommandeEteindreLumiere(Lumiere lumiere) {
    this.lumiere = lumiere;
  }

  @Override
  public void executer() {
    lumiere.eteindre();
  }
}
