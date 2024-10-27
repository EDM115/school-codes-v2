public class Utilisateur {
  public static void main(String[] args) {
    Societe societe1 = new SocieteSansFiliale("societe1", "rue de la societe 1", "info@societe1.com");
    Societe societe2 = new SocieteSansFiliale("societe2", "rue de la societe 2", "info@societe2.com");
    Societe groupe1 = new SocieteMere("groupe 1", "rue du groupe 1", "info@groupe1.com");
    societe1.ajouteVehicule();
    societe2.ajouteVehicule();
    societe2.ajouteVehicule();

    groupe1.ajouteFiliale(societe1);
    groupe1.ajouteFiliale(societe2);
    groupe1.ajouteVehicule();
    System.out.println(" Cout d'entretien total du groupe 1 : " + groupe1.calculeCoutEntretien());
    System.out.println(" nb vehicules" + groupe1.getNbrVehicules());

    Visiteur visiteurEntretien = new VisiteurCalculeEntretien();
    Visiteur visiteurMailing = new VisiteurMailingCommercial();

    groupe1.accepteVisiteur(visiteurEntretien);
    groupe1.accepteVisiteur(visiteurMailing);

    Societe societe3 = new SocieteSansFiliale("societe3", "rue de la societe 3", "info@societe3.com");
    societe3.ajouteVehicule();
    Societe groupe2 = new SocieteMere("groupe 2", "rue du groupe 2", "info@groupe2.com");
    groupe2.ajouteFiliale(groupe1);
    groupe2.ajouteFiliale(societe3);
    System.out.println(" nb vehicules 2" + groupe2.getNbrVehicules());
    System.out.println(" Cout d'entretien total du groupe 2 : " + groupe2.calculeCoutEntretien());

    societe1.envoieEmailCommercial(societe2);
    societe3.envoieEmailCommercial(societe1);

    groupe2.accepteVisiteur(visiteurEntretien);
    groupe2.accepteVisiteur(visiteurMailing);
  }
}
