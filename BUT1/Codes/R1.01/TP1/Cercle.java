/**
 * Calcule le périmètre et l'aire d'un cercle à partir du rayon
 *
 * @author EDM115
 */
class Cercle {
  void principal() {
    float pi = 3.41592653589793238462643383279F;
    int rayon = SimpleInput.getInt("Le rayon du cercle : ");

    double perimetre = pi * 2 * rayon;
    double aire = rayon * rayon * pi;

    System.out.println(
        "Le cercle de rayon "
            + rayon
            + " a un périmètre de "
            + perimetre
            + " et une aire de "
            + aire);
  }
}
