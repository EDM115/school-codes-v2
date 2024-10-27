public class Telecommande {
  private Commande commande;

  public void setCommande(Commande commande) {
    this.commande = commande;
  }

  public void appuyerBouton() {
    if (commande != null) {
      commande.executer();
    }
  }
}
