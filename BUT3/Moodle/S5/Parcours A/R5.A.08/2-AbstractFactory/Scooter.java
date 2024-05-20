public abstract class Scooter
{
  protected String modele;
  protected String couleur;
  protected int puissance;

  public Scooter(String modele, String couleur, int
    puissance)
  {
    this.modele = modele;
    this.couleur = couleur;
    this.puissance = puissance;
  }
  public abstract void afficheCaracteristiques();
}
