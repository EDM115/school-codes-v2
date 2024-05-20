public class AutomobileEssence extends Automobile
{
  public AutomobileEssence(String modele, String couleur,
    int puissance, double espace)
  {
    super(modele, couleur, puissance, espace);
  }

  public void afficheCaracteristiques()
  {
    System.out.println(
      "Automobile à essence de modèle : " + modele + 
      " de couleur : " + couleur + " de puissance : " +
      puissance + " d'espace : " + espace);
  }
}
