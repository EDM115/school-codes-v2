public class Utilisateur
{
  public static void main(String[] args)
  {
    // cr�er 3 v�hicules
    // cr�er un catalogue et lui ajouter les v�hicules
      
    System.out.println("Affichage du catalogue initial");
    catalogue.affiche();
    System.out.println();
    
    CommandeSolder commmandeSolder = new CommandeSolder(10, 5, 0.1);
    catalogue.lanceCommandeSolder(commmandeSolder);
    System.out.println("Affichage du catalogue apres " + "execution de la premiere commande");
    catalogue.affiche();
    System.out.println();
    CommandeSolder commmandeSolder2 = new CommandeSolder(10, 5, 0.5);
    catalogue.lanceCommandeSolder(commmandeSolder2);
    System.out.println("Affichage du catalogue apres " + "execution de la seconde commande");
    catalogue.affiche();
    System.out.println();
    catalogue.annuleCommandeSolder(1);
    System.out.println("Affichage du catalogue apres " +"annulation de la premiere commande");
    catalogue.affiche();
    System.out.println();
      
    catalogue.retablitCommandeSolder(1);
    System.out.println("Affichage du catalogue apres " +
      "r�tablissement de la premi�re commande");
    catalogue.affiche();
    System.out.println();
  }
}
