public class CommandeAllumerVentilateur implements Commande {
  private Ventilateur ventilateur;

  public CommandeAllumerVentilateur(Ventilateur ventilateur) {
    this.ventilateur = ventilateur;
  }

  @Override
  public void executer() {
    ventilateur.allumer();
  }
}
