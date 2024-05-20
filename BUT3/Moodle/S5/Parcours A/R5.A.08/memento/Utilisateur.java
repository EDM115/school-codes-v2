public class Utilisateur{
  public static void main(String[] args){
    Memento memento;
    
    OptionVehicule option1 = new OptionVehicule("Sieges en cuir");
    OptionVehicule option2 = new OptionVehicule( "Accoudoirs");
    OptionVehicule option3 = new OptionVehicule("Sieges sportifs");
      // les sieges sprtifs sont incompatibles avec les sièges en cuir ou les sccoudoirs.
    
    option1.ajouteOptionIncompatible(option3);
    option2.ajouteOptionIncompatible(option3);
    
      // Creation d'un chariot
      // ajouter les options 1 et 2 et afficher.
      
      // ajouter de l'option 3 et création d'un mémento
      // afficher les options du chariot
      // annuler la dernière option
      // afficher les options du chariot
   
      
  }
}
