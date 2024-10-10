public class SocieteSansFiliale extends Societe {
  
  public SocieteSansFiliale(String nom, String adresse, String email) {
    super(nom, adresse, email);
  }
  
  public boolean ajouteFiliale(Societe filiale)  {
    return false;
  }

  public double calculeCoutEntretien() {
    return nbrVehicules * coutUnitVehicule;
  }
  
  public void envoieEmailCommercial(Societe societe) {
    System.out.println("Envoi d'un email a " + 
      societe.getNom() + " adresse : " + societe.getEmail () +
                       " Proposition commerciale pour votre societe"); 
  }
    
}
