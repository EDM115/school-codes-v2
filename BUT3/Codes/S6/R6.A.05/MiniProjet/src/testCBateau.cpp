#include "CBateau.h"

void testConstructeurDefaut();
void testConstructeurParametre();

void testAccesseurs();
void testModificateurs();

void testEstCouleCasNormal();
void testEstCouleCasLimite();
void testEstCouleCasErreur();

void testTirAdverseCasNormal();
void testTirAdverseCasLimite();
void testTirAdverseCasErreur();

string success = "\033[32m[SUCCÈS]\033[0m";
string error = "\033[31m[ERREUR]\033[0m";

int main() {
  cout << "\033[36mDébut des tests de la classe CBateau\033[0m" << endl;

  testConstructeurDefaut();
  testConstructeurParametre();

  testAccesseurs();
  testModificateurs();

  testEstCouleCasNormal();
  testEstCouleCasLimite();
  testEstCouleCasErreur();

  testTirAdverseCasNormal();
  testTirAdverseCasLimite();
  testTirAdverseCasErreur();

  cout << "\n\033[36mFin des tests de la classe CBateau\033[0m" << endl;

  return 0;
}

void testConstructeurDefaut() {
  cout << "\nTest du constructeur par défaut" << endl;
  CBateau b;

  if (b.getTaille() != 0 || b.getNom() != "neant" || b.getPosition() != make_pair(0, 0)) {
    cout << error << " Constructeur par défaut échoué !" << endl;
  } else {
    cout << success << " Constructeur par défaut valide." << endl;
  }
}

void testConstructeurParametre() {
  cout << "\nTest du constructeur paramétré" << endl;
  CBateau b("Destroyer", make_pair(1, 1), 3);

  if (b.getNom() != "Destroyer" || b.getPosition() != make_pair(1, 1) || b.getTaille() != 3) {
    cout << error << " Constructeur paramétré échoué !" << endl;
  } else {
    cout << success << " Constructeur paramétré valide." << endl;
  }
}

void testAccesseurs() {
  cout << "\nTest des accesseurs" << endl;
  CBateau b("Frégate", make_pair(2, 2), 4);

  if (b.getNom() != "Frégate") {
    cout << error << " getNom() incorrect." << endl;
  }

  if (b.getPosition() != make_pair(2, 2)) {
    cout << error << " getPosition() incorrect." << endl;
  }

  if (b.getTaille() != 4) {
    cout << error << " getTaille() incorrect." << endl;
  }

  cout << success << " Accesseurs valides." << endl;
}

void testModificateurs() {
  cout << "\nTest des modificateurs" << endl;
  CBateau b("Sous-Marin", make_pair(3, 3), 5);
  b.setPosition(4, 4);

  if (b.getPosition() != make_pair(4, 4)) {
    cout << error << " setPosition() incorrect." << endl;
  } else {
    cout << success << " Modificateurs valides." << endl;
  }
}

void testEstCouleCasNormal() {
  cout << "\nTest de estCoule() - Cas normal" << endl;
  CBateau b("Croiseur", make_pair(5, 5), 2);
  b.tirAdverse(make_pair(5, 5));
  b.tirAdverse(make_pair(5, 6));

  if (!b.estCoule()) {
    cout << error << " estCoule() incorrect pour bateau totalement touché." << endl;
  } else {
    cout << success << " estCoule() fonctionne pour cas normal." << endl;
  }
}

void testEstCouleCasLimite() {
  cout << "\nTest de estCoule() - Cas limite" << endl;
  CBateau b("Destroyer", make_pair(1, 1), 1);
  b.tirAdverse(make_pair(1, 1));

  if (!b.estCoule()) {
    cout << error << " estCoule() incorrect pour bateau d'une seule case." << endl;
  } else {
    cout << success << " estCoule() fonctionne pour cas limite." << endl;
  }
}

void testEstCouleCasErreur() {
  cout << "\nTest de estCoule() - Cas d'erreur" << endl;
  CBateau b("Frégate", make_pair(2, 2), 3);

  if (b.estCoule()) {
    cout << error << " estCoule() incorrect pour bateau intact." << endl;
  } else {
    cout << success << " estCoule() ne coule pas un bateau intact." << endl;
  }
}

void testTirAdverseCasNormal() {
  cout << "\nTest de tirAdverse() - Cas normal" << endl;
  CBateau b("Porte-avions", make_pair(1, 1), 4);

  if (!b.tirAdverse(make_pair(1, 1))) {
    cout << error << " tirAdverse() incorrect pour position valide." << endl;
  } else {
    cout << success << " tirAdverse() fonctionne pour position valide." << endl;
  }
}

void testTirAdverseCasLimite() {
  cout << "\nTest de tirAdverse() - Cas limite" << endl;
  CBateau b("Sous-Marin", make_pair(0, 0), 1);

  if (!b.tirAdverse(make_pair(0, 0))) {
    cout << error << " tirAdverse() incorrect pour tir sur la seule case du bateau." << endl;
  } else {
    cout << success << " tirAdverse() fonctionne pour cas limite." << endl;
  }
}

void testTirAdverseCasErreur() {
  cout << "\nTest de tirAdverse() - Cas d'erreur" << endl;
  CBateau b("Frégate", make_pair(2, 2), 3);

  if (b.tirAdverse(make_pair(3, 3))) {
    cout << error << " tirAdverse() ne doit pas fonctionner pour position hors du bateau." << endl;
  } else {
    cout << success << " tirAdverse() fonctionne pour position hors du bateau." << endl;
  }
}
