
public class ScooterElectricite extends Scooter
{
  public ScooterElectricite(String modele, String couleur,
    int puissance)
  {
    super(modele, couleur, puissance);
  }

  public void afficheCaracteristiques()
  {
    System.out.println("Scooter électrique de modèle : " 
      + modele + " de couleur : " + couleur + 
      " de puissance : " + puissance);
  }

}
