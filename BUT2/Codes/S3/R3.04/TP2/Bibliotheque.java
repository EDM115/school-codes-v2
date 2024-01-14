public class Bibliotheque {
    private Pret p;
    private String err1 = "Livre non disponible";
    private String err2 = "Vous avez plus de 3 emprunts en cours";

    public void emprunter(Ouvrage ouv, Client cli) {
        int nb = cli.nbEmprunts();
        if (nb < 3) {
            int dispo = ouv.nbEnStock();
            if (dispo > 0) {
                ouv.emprunter();
                this.p = new Pret(ouv, cli);
            } else {
                a = new Affichage();
                a.afficher(err1);
            }
        } else {
            a = new Affichage();
            a.afficher(err2);
        }
    }
}
