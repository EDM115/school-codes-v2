public class VisiteurCalculeEntretien implements Visiteur {
  @Override
  public void visiteSocieteMere(SocieteMere societeMere) {
    double cout = societeMere.getNbrVehicules() * Societe.coutUnitVehicule;
    for (Societe filiale : societeMere.filiales) {
      cout += filiale.calculeCoutEntretien();
    }
    System.out.println("Cout d'entretien pour " + societeMere.getNom() + " : " + cout);
  }

  @Override
  public void visiteSocieteSansFiliale(SocieteSansFiliale societeSansFiliale) {
    double cout = societeSansFiliale.getNbrVehicules() * Societe.coutUnitVehicule;
    System.out.println("Cout d'entretien pour " + societeSansFiliale.getNom() + " : " + cout);
  }
}
