#include "CArmada.h"
#include "CBateau.h"

void testAjouterBateauCasNormal();
void testAjouterBateauCasLimite();
void testAjouterBateauCasErreur();

void testGetBateauCasNormal();
void testGetBateauCasLimite();
void testGetBateauCasErreur();

void testGetEffectifTotalCasNormal();

void testGetNbreTotCasesCasNormal();

void testGetEffectifCasNormal();

void testGetArmadaFromFileCasNormal();
void testGetArmadaFromFileCasErreur();

void testPlacerAleatoirementCasNormal();
void testPlacerAleatoirementCasLimite();

string success = "\033[32m[SUCCÈS]\033[0m";
string error = "\033[31m[ERREUR]\033[0m";

int main() {
  cout << "\033[36mDébut des tests de la classe CArmada\033[0m" << endl;

  testAjouterBateauCasNormal();
  testAjouterBateauCasLimite();
  testAjouterBateauCasErreur();

  testGetBateauCasNormal();
  testGetBateauCasLimite();
  testGetBateauCasErreur();

  testGetEffectifTotalCasNormal();

  testGetNbreTotCasesCasNormal();

  testGetEffectifCasNormal();

  testGetArmadaFromFileCasNormal();
  testGetArmadaFromFileCasErreur();

  testPlacerAleatoirementCasNormal();
  testPlacerAleatoirementCasLimite();

  cout << "\n\033[36mFin des tests de la classe CArmada\033[0m" << endl;
  return 0;
}

void testAjouterBateauCasNormal() {
  cout << "\nTest de ajouterBateau() - Cas normal" << endl;
  CArmada armada;
  CBateau b("Destroyer", make_pair(0, 0), 3);
  armada.ajouterBateau(b);

  if (armada.getEffectifTotal() != 1) {
    cout << error << " Le bateau n'a pas été correctement ajouté." << endl;
  } else {
    cout << success << " ajouterBateau() fonctionne pour un cas normal." << endl;
  }
}

void testAjouterBateauCasLimite() {
  cout << "\nTest de ajouterBateau() - Cas limite" << endl;
  CArmada armada;
  CBateau b("Sous-Marin", make_pair(0, 0), 1);
  armada.ajouterBateau(b);

  if (armada.getEffectifTotal() != 1) {
    cout << error << " ajouterBateau() incorrect pour un bateau de taille minimale." << endl;
  } else {
    cout << success << " ajouterBateau() fonctionne pour un cas limite." << endl;
  }
}

void testAjouterBateauCasErreur() {
  cout << "\nTest de ajouterBateau() - Cas d'erreur" << endl;
  CArmada armada;

  try {
    CBateau b("Frégate", make_pair(-1, -1), -3);
    armada.ajouterBateau(b);
    cout << error << " ajouterBateau() accepte un bateau avec des paramètres invalides." << endl;
  } catch (const exception &e) {
    cout << success << " ajouterBateau() rejette un bateau avec des paramètres invalides." << endl;
  }
}

void testGetBateauCasNormal() {
  cout << "\nTest de getBateau() - Cas normal" << endl;
  CArmada armada;
  CBateau b("Destroyer", make_pair(0, 0), 3);
  armada.ajouterBateau(b);

  if (armada.getBateau(0)->getNom() != "Destroyer") {
    cout << error << " getBateau() retourne un mauvais bateau." << endl;
  } else {
    cout << success << " getBateau() fonctionne pour un cas normal." << endl;
  }
}

void testGetBateauCasLimite() {
  cout << "\nTest de getBateau() - Cas limite" << endl;
  CArmada armada;
  CBateau b("Sous-Marin", make_pair(0, 0), 1);
  armada.ajouterBateau(b);

  if (armada.getBateau(0)->getTaille() != 1) {
    cout << error << " getBateau() ne fonctionne pas pour un bateau de taille minimale." << endl;
  } else {
    cout << success << " getBateau() fonctionne pour un cas limite." << endl;
  }
}

void testGetBateauCasErreur() {
  cout << "\nTest de getBateau() - Cas d'erreur" << endl;
  CArmada armada;

  try {
    armada.getBateau(-1);
    cout << error << " getBateau() accepte un index invalide." << endl;
  } catch (const out_of_range &e) {
    cout << success << " getBateau() gère correctement un index invalide." << endl;
  }
}

void testGetEffectifTotalCasNormal() {
  cout << "\nTest de getEffectifTotal() - Cas normal" << endl;
  CArmada armada;
  CBateau b1("Destroyer", make_pair(0, 0), 3);
  CBateau b2("Sous-Marin", make_pair(1, 1), 2);
  armada.ajouterBateau(b1);
  armada.ajouterBateau(b2);

  if (armada.getEffectifTotal() != 2) {
    cout << error << " getEffectifTotal() retourne une valeur incorrecte." << endl;
  } else {
    cout << success << " getEffectifTotal() fonctionne pour un cas normal." << endl;
  }
}

void testGetNbreTotCasesCasNormal() {
  cout << "\nTest de getNbreTotCases() - Cas normal" << endl;
  CArmada armada;
  CBateau b1("Destroyer", make_pair(0, 0), 3);
  CBateau b2("Sous-Marin", make_pair(1, 1), 2);
  armada.ajouterBateau(b1);
  armada.ajouterBateau(b2);

  if (armada.getNbreTotCases() != 5) {
    cout << error << " getNbreTotCases() retourne une valeur incorrecte." << endl;
  } else {
    cout << success << " getNbreTotCases() fonctionne pour un cas normal." << endl;
  }
}

void testGetEffectifCasNormal() {
  cout << "\nTest de getEffectif() - Cas normal" << endl;
  CArmada armada;
  CBateau b1("Destroyer", make_pair(0, 0), 3);
  CBateau b2("Sous-Marin", make_pair(1, 1), 2);
  armada.ajouterBateau(b1);
  armada.ajouterBateau(b2);

  if (armada.getEffectif() != 2) {
    cout << error << " getEffectif() retourne une valeur incorrecte." << endl;
  } else {
    cout << success << " getEffectif() fonctionne pour un cas normal." << endl;
  }
}

void testGetArmadaFromFileCasNormal() {
  cout << "\nTest de getArmadaFromFile() - Cas normal" << endl;
  CArmada armada;
  armada.getArmadaFromFile();

  if (armada.getEffectifTotal() <= 0) {
    cout << error << " getArmadaFromFile() n'a pas chargé correctement les bateaux." << endl;
  } else {
    cout << success << " getArmadaFromFile() fonctionne pour un cas normal." << endl;
  }
}

void testGetArmadaFromFileCasErreur() {
  cout << "\nTest de getArmadaFromFile() - Cas d'erreur" << endl;
  CArmada armada;

  system("mv ../ws/flotille.txt ../ws/flotille.txt.bak");

  try {
    armada.getArmadaFromFile();
    cout << success << " getArmadaFromFile() gère correctement l'absence de fichier." << endl;
  } catch (const exception &e) {
    cout << error << " getArmadaFromFile() ne gère pas correctement l'absence de fichier." << endl;
  }

  system("mv ../ws/flotille.txt.bak ../ws/flotille.txt");
}

void testPlacerAleatoirementCasNormal() {
  cout << "\nTest de placerAleatoirement() - Cas normal" << endl;
  CArmada armada;
  CBateau b1("Destroyer", make_pair(0, 0), 3);
  CBateau b2("Sous-Marin", make_pair(1, 1), 2);
  armada.ajouterBateau(b1);
  armada.ajouterBateau(b2);

  if (!armada.placerAleatoirement()) {
    cout << error << " placerAleatoirement() ne fonctionne pas." << endl;
  } else {
    cout << success << " placerAleatoirement() fonctionne pour un cas normal." << endl;
  }
}

void testPlacerAleatoirementCasLimite() {
  cout << "\nTest de placerAleatoirement() - Cas limite" << endl;
  CArmada armada;

  try {
    if (armada.placerAleatoirement()) {
      cout << success << " placerAleatoirement() gère correctement les cas limites (aucun bateau)." << endl;
    } else {
      cout << error << " placerAleatoirement() devrait retourner vrai pour une armada vide." << endl;
    }
  } catch (const exception &e) {
    cout << error << " placerAleatoirement() a levé une exception inattendue." << endl;
  }
}
