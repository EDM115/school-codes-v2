public class CommandeEteindreVentilateur implements Commande {
  private Ventilateur ventilateur;

  public CommandeEteindreVentilateur(Ventilateur ventilateur) {
    this.ventilateur = ventilateur;
  }

  @Override
  public void executer() {
    ventilateur.eteindre();
  }
}
