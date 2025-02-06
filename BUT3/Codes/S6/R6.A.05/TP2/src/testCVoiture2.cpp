#include "CVoiture2.h"

int main() {
  char nom[] = "Renault";
  char couleur[] = "Rouge";
  CVoiture voiture(1, nom, couleur);

  voiture.presentation();

  // Modification de la chaîne de caractères
  strcpy(nom, "Peugeot");
  voiture.presentation();

  // Test du copy-constructeur
  CVoiture voiture2 = voiture;
  voiture2.presentation();

  return 0;
}
