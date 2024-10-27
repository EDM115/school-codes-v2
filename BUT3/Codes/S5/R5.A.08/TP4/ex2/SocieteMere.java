import java.util.ArrayList;

public class SocieteMere extends Societe {
  protected ArrayList<Societe> filiales;

  public SocieteMere(String nom, String adresse, String email) {
    super(nom, adresse, email);
    filiales = new ArrayList<Societe>();
  }

  public boolean ajouteFiliale(Societe filiale) {
    return filiales.add(filiale);
  }

  public double calculeCoutEntretien() {
    double cout = 0.0;
    for (Societe filiale : filiales) {
      cout = cout + filiale.calculeCoutEntretien();
    }

    return cout + nbrVehicules * coutUnitVehicule;
  }

  public void envoieEmailCommercial(Societe societe) {
    System.out.println("Envoi d'un email a" + societe.getNom() + " adresse : " + societe.getEmail() + " Proposition commerciale pour votre groupe");
    System.out.println("Impression d'un courrier  " + societe.getNom() + " adresse : " + societe.getAdresse() + " Proposition commerciale pour votre groupe");
  }

  public void accepteVisiteur(Visiteur visiteur) {
    visiteur.visiteSocieteMere(this);
    for (Societe filiale : filiales) {
      filiale.accepteVisiteur(visiteur);
    }
}
}
