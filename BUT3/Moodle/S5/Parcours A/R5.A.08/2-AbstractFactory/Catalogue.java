import java.util.*;
public class Catalogue
{
  public static int nbAutos = 3;
  public static int nbScooters = 2;

  public static void main(String[] args)
  {
    Scanner reader = new Scanner(System.in);
    FabriqueVehicule fabrique;
    Automobile[] autos = new Automobile[nbAutos];
    Scooter[] scooters = new Scooter[nbScooters];
    System.out.print("Voulez-vous utiliser des " + 
      "Véhicules électriques (1) ou à essence (2) :");
    String choix = reader.next();
    if (choix.equals("1"))
    {
      fabrique = new FabriqueVehiculeElectricite();
    }
    else
    {
      fabrique = new FabriqueVehiculeEssence();
    }
    for (int index = 0; index < nbAutos; index++)
      autos[index] = fabrique.creeAutomobile("standard", 
        "jaune", 6+index, 3.2);
    for (int index = 0; index < nbScooters; index++)
      scooters[index] = fabrique.creeScooter("classic", 
        "rouge", 2+index);
    for (Automobile auto: autos)
      auto.afficheCaracteristiques();
    for (Scooter scooter: scooters)
      scooter.afficheCaracteristiques();
  }
}
