public class Client {
  public static void main(String[] args) {
    Telecommande telecommande = new Telecommande();

    // Commandes pour Lumière
    Lumiere lumiere = new Lumiere();
    Commande allumerLumiere = new CommandeAllumerLumiere(lumiere);
    Commande eteindreLumiere = new CommandeEteindreLumiere(lumiere);

    // Commandes pour Ventilateur
    Ventilateur ventilateur = new Ventilateur();
    Commande allumerVentilateur = new CommandeAllumerVentilateur(ventilateur);
    Commande eteindreVentilateur = new CommandeEteindreVentilateur(ventilateur);
    Commande augmenterVitesse = new CommandeAugmenterVitesseVentilateur(ventilateur);
    Commande diminuerVitesse = new CommandeDiminuerVitesseVentilateur(ventilateur);

    // Commandes pour Porte de Garage
    PorteGarage porteGarage = new PorteGarage();
    Commande ouvrirPorteGarage = new CommandeOuvrirPorteGarage(porteGarage);
    Commande fermerPorteGarage = new CommandeFermerPorteGarage(porteGarage);

    // Utilisation des commandes avec la télécommande
    telecommande.setCommande(allumerLumiere);
    telecommande.appuyerBouton();

    telecommande.setCommande(eteindreLumiere);
    telecommande.appuyerBouton();

    telecommande.setCommande(allumerVentilateur);
    telecommande.appuyerBouton();

    telecommande.setCommande(augmenterVitesse);
    telecommande.appuyerBouton();
    telecommande.appuyerBouton();

    telecommande.setCommande(diminuerVitesse);
    telecommande.appuyerBouton();

    telecommande.setCommande(ouvrirPorteGarage);
    telecommande.appuyerBouton();

    telecommande.setCommande(fermerPorteGarage);
    telecommande.appuyerBouton();
  }
}
