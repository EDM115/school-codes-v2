#include "CVoiture1.h"

int main() {
  char nom[] = "Renault";
  char couleur[] = "Rouge";
  CVoiture voiture(1, nom, couleur);

  voiture.presentation();

  // Modification de la chaîne de caractères
  strcpy(nom, "Peugeot");
  voiture.presentation();

  return 0;
}
