public class Vehicule
{
  protected String nom;
  protected long dateEntreeStock;
  protected double prixVente;

  public Vehicule(String nom, long dateEntreeStock,
    double prixVente)
  {
    this.nom = nom;
    this.dateEntreeStock = dateEntreeStock;
    this.prixVente = prixVente;
  }

  public long getDureeStockage(long aujourdhui)
  {
    return aujourdhui - dateEntreeStock;
  }

  public void modifiePrix(double coefficient)
  {
    this.prixVente = 0.01 * Math.round(coefficient *
      this.prixVente * 100);
  }

  public void affiche()
  {
    System.out.println(nom + " prix : " + prixVente + 
      " date entrée Stock " + dateEntreeStock);
  }
}
