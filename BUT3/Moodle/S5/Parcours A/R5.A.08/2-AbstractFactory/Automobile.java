public abstract class Automobile
{
  protected String modele;
  protected String couleur;
  protected int puissance;
  protected double espace;

  public Automobile(String modele, String couleur, int
    puissance, double espace)
  {
    this.modele = modele;
    this.couleur = couleur;
    this.puissance = puissance;
    this.espace = espace;
  }

  public abstract void afficheCaracteristiques();
}
