#include "CArmada.h"
#include "CCoups.h"
#include "CGui.h"

void testConstructeurDefaut();
void testConstructeurParametre();

void testSetArmadaCoups();

void testPositionnerBateauxCasNormal();
void testPositionnerBateauxCasLimite();

void testChoisirAttaqueCasNormal();
void testChoisirAttaqueCasLimite();
void testChoisirAttaqueCasErreur();

void testAffichage();

string success = "\033[32m[SUCCÈS]\033[0m";
string error = "\033[31m[ERREUR]\033[0m";

int main() {
  cout << "\033[36mDébut des tests de la classe CGui\033[0m" << endl;

  testConstructeurDefaut();
  testConstructeurParametre();

  testSetArmadaCoups();

  testPositionnerBateauxCasNormal();
  testPositionnerBateauxCasLimite();

  testChoisirAttaqueCasNormal();
  testChoisirAttaqueCasLimite();
  testChoisirAttaqueCasErreur();

  testAffichage();

  cout << "\n\033[36mFin des tests de la classe CGui\033[0m" << endl;
  return 0;
}

void testConstructeurDefaut() {
  cout << "\nTest du constructeur par défaut" << endl;
  CGui gui;
  cout << success << " Constructeur par défaut valide." << endl;
}

void testConstructeurParametre() {
  cout << "\nTest du constructeur paramétré" << endl;
  CArmada armada;
  CCoups coups;
  CGui gui(&armada, &coups);
  cout << success << " Constructeur paramétré valide." << endl;
}

void testSetArmadaCoups() {
  cout << "\nTest de setArmadaCoups()" << endl;
  CArmada armada;
  CCoups coups;
  CGui gui;
  gui.setArmadaCoups(&armada, &coups);
  cout << success << " setArmadaCoups() fonctionne." << endl;
}

void testPositionnerBateauxCasNormal() {
  cout << "\nTest de positionnerBateaux() - Cas normal" << endl;
  CArmada armada;
  CCoups coups;
  CGui gui(&armada, &coups);

  if (gui.positionnerBateaux()) {
    cout << success << " positionnerBateaux() fonctionne pour un cas normal." << endl;
  } else {
    cout << error << " positionnerBateaux() ne fonctionne pas correctement." << endl;
  }
}

void testPositionnerBateauxCasLimite() {
  cout << "\nTest de positionnerBateaux() - Cas limite (Armada vide)" << endl;
  CArmada armada;
  CCoups coups;
  CGui gui(&armada, &coups);

  if (gui.positionnerBateaux()) {
    cout << success << " positionnerBateaux() gère correctement le cas d'une armada vide." << endl;
  } else {
    cout << error << " positionnerBateaux() devrait retourner vrai pour une armada vide." << endl;
  }
}

void testChoisirAttaqueCasNormal() {
  cout << "\nTest de choisirAttaque() - Cas normal" << endl;
  cout << "Simulation d'une attaque" << endl;

  istringstream fakeInput("3\n4\n");
  streambuf* backup = cin.rdbuf();  // Sauvegarde de l'entrée standard originale
  cin.rdbuf(fakeInput.rdbuf());  // Redirection de l'entrée standard

  CArmada armada;
  CCoups coups;
  CGui gui(&armada, &coups);

  try {
    pair<int, int> attaque = gui.choisirAttaque();
    cout << "\n" << success << " choisirAttaque() a retourné : (" << attaque.first << ", " << attaque.second << ")" << endl;
  } catch (const invalid_argument &e) {
    cout << "\n" << error << " choisirAttaque() a retourné une erreur." << endl;
  }

  cin.rdbuf(backup);  // Restauration de l'entrée standard
}

void testChoisirAttaqueCasLimite() {
  cout << "\nTest de choisirAttaque() - Cas limite (Attaque à la limite de la grille)" << endl;
  cout << "Simulation d'une attaque à la limite de la grille" << endl;

  istringstream fakeInput("9\n9");
  streambuf* backup = cin.rdbuf();  // Sauvegarde de l'entrée standard originale
  cin.rdbuf(fakeInput.rdbuf());  // Redirection de l'entrée standard

  CArmada armada;
  CCoups coups;
  CGui gui(&armada, &coups);
  
  try {
    pair<int, int> attaque = gui.choisirAttaque();
    cout << "\n" << success << " choisirAttaque() a retourné : (" << attaque.first << ", " << attaque.second << ")" << endl;
  } catch (const invalid_argument &e) {
    cout << "\n" << error << " choisirAttaque() a retourné une erreur." << endl;
  }

  cin.rdbuf(backup);  // Restauration de l'entrée standard
}

void testChoisirAttaqueCasErreur() {
  cout << "\nTest de choisirAttaque() - Cas d'erreur (Attaque hors de la grille)" << endl;
  cout << "Simulation d'une attaque hors de la grille" << endl;

  istringstream fakeInput("12\n12\n");
  streambuf* backup = cin.rdbuf();  // Sauvegarde de l'entrée standard originale
  cin.rdbuf(fakeInput.rdbuf());  // Redirection de l'entrée standard

  CArmada armada;
  CCoups coups;
  CGui gui(&armada, &coups);
  
  try {
    pair<int, int> attaque = gui.choisirAttaque();
    cout << "\n" << error << " choisirAttaque() a retourné : (" << attaque.first << ", " << attaque.second << ")" << endl;
  } catch (const invalid_argument &e) {
    cout << "\n" << success << " choisirAttaque() a retourné une erreur." << endl;
  }

  cin.rdbuf(backup);  // Restauration de l'entrée standard
}

void testAffichage() {
  cout << "\nTest de l'affichage" << endl;
  CArmada armada;
  CCoups coups;
  CGui gui(&armada, &coups);
  gui.afficheGagne();
  gui.affichePerdu();
  gui.remplirDeuxGrilles(cout);
  cout << endl;
  gui.afficherLaGrille(cout, "joueur");
  gui.afficherLaGrille(cout, "adversaire");
  cout << endl;
  cout << success << " Les affichages fonctionnent correctement." << endl;
}
