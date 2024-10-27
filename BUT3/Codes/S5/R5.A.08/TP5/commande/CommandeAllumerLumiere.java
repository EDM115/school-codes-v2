public class CommandeAllumerLumiere implements Commande {
  private Lumiere lumiere;

  public CommandeAllumerLumiere(Lumiere lumiere) {
    this.lumiere = lumiere;
  }

  @Override
  public void executer() {
    lumiere.allumer();
  }
}
