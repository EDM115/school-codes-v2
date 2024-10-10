public abstract class Societe {
  protected static double coutUnitVehicule = 5.0;
  protected int nbrVehicules;
  protected String nom, adresse, email;
  
  public Societe(String nom, String adresse, String email) {
    this.nom = nom;
    this.adresse = adresse;
    this.email= email;
    this.nbrVehicules = 0;
  }

  public String getNom() {
    return this.nom;
  }
  
  public String getAdresse() {
    return this.adresse;
  }
  
  public String getEmail () {
    return this.email;
  }  
    
  public void ajouteVehicule()   {
    nbrVehicules = nbrVehicules + 1;
  }
  
  public int getNbrVehicules() {
    return this.nbrVehicules;
  }

  public abstract double calculeCoutEntretien();
  
  public abstract void envoieEmailCommercial(Societe societe);

  public abstract boolean ajouteFiliale(Societe filiale);
}
