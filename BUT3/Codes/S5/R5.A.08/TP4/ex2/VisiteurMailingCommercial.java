public class VisiteurMailingCommercial implements Visiteur {
  @Override
  public void visiteSocieteMere(SocieteMere societeMere) {
    System.out.println("Envoi d'une proposition commerciale à " + societeMere.getNom());
  }

  @Override
  public void visiteSocieteSansFiliale(SocieteSansFiliale societeSansFiliale) {
    System.out.println("Envoi d'une proposition commerciale à " + societeSansFiliale.getNom());
  }
}
