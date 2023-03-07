import java.util.ArrayList;

public class Banque {
  private String nomBanque;
  private ArrayList<Client> lesClients;
  private ArrayList<Compte> lesComptes;

  public Banque(String nomBanque) {
    if (nomBanque == null) {
      System.err.println("\u001B[31mBanque() : Le nom de la banque ne peut pas être null\u001B[0m");
      this.nom = "";
    } else {
      this.nomBanque = nomBanque;
    }
    this.lesClients = new ArrayList<Client>();
    this.lesComptes = new ArrayList<Compte>();
  }
}
