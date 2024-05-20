
public class ScooterEssence extends Scooter
{
  public ScooterEssence(String modele, String couleur,
    int puissance)
  {
    super(modele, couleur, puissance);
  }

  public void afficheCaracteristiques()
  {
    System.out.println("Scooter à essence de modèle : " +
      modele + " de couleur : " + couleur + 
      " de puissance : " + puissance);
  }

}
