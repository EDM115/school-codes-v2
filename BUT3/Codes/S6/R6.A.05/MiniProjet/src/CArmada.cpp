#include "CArmada.h"

void CArmada::ajouterBateau(CBateau& unBat) {
  m_listeBateaux.push_back(unBat);
}

CBateau* CArmada::getBateau(int i) {
  if (i < 0 || size_t(i) >= m_listeBateaux.size()) {
    throw out_of_range("L'index est invalide.");
  }

  return &m_listeBateaux[i];
}

int CArmada::getEffectifTotal() {
  return m_listeBateaux.size();
}

int CArmada::getNbreTotCases() {
  int nbCases = 0;

  for (size_t i = 0; i < m_listeBateaux.size(); i++) {
    nbCases += m_listeBateaux[i].getTaille();
  }

  return nbCases;
}

int CArmada::getEffectif() {
  int nbBateaux = 0;

  for (size_t i = 0; i < m_listeBateaux.size(); i++) {
    if (!m_listeBateaux[i].estCoule()) {
      nbBateaux++;
    }
  }

  return nbBateaux;
}

void CArmada::getArmadaFromFile() {
  ifstream fichier("../ws/flotille.txt");

  if (fichier) {
    string ligne;

    while (getline(fichier, ligne)) {
      // On ignore les lignes vides et les commentaires
      if (ligne.empty() || ligne[0] == '#') {
        continue;
      }

      istringstream iss(ligne);
      string nom;
      int nbGrille, nbCases;

      // Mauvais format de ligne
      if (!(iss >> nom >> nbGrille >> nbCases)) {
        cerr << "Erreur de lecture dans flotille.txt ligne : " << ligne << endl;

        continue;
      }

      for (int i = 0; i < nbGrille; i++) {
        CBateau b(nom, make_pair(0, 0), nbCases);
        ajouterBateau(b);
      }
    }

    fichier.close();
  } else {
    // Probablement un fichier inexistant
    cerr << "Erreur lors de l'ouverture du fichier flotille.txt" << endl;
  }
}

bool CArmada::placerAleatoirement() {
  // Récupération du PID pour éviter que les grilles serveur et client soient identiques
  srand(time(NULL) + getpid());
  vector<bool> lignesOccupees(TAILLE_GRILLE, false);

  for (size_t i = 0; i < m_listeBateaux.size(); i++) {
    int nbEssais = 0;
    bool bateauPlace = false;

    // Tant que le bateau n'est pas placé et que le nombre d'essais n'est pas dépassé
    while (!bateauPlace && nbEssais < MAXESSAIS) {
      int ligne = rand() % (TAILLE_GRILLE - 1);
      int colonne = rand() % (TAILLE_GRILLE - 1 - m_listeBateaux[i].getTaille());

      // Vérifier que la ligne n'est pas déjà utilisée
      if (lignesOccupees[ligne]) {
        nbEssais++;

        continue;
      }

      // Vérifier qu'aucun bateau ne chevauche cette position
      bool bateauValide = true;
      for (size_t j = 0; j < i; j++) {
        pair<int, int> pos = m_listeBateaux[j].getPosition();

        if (pos.first == ligne) {
          bateauValide = false;

          break;
        }
      }

      // Vérifier que le bateau ne dépasse pas de la grille
      if (colonne + m_listeBateaux[i].getTaille() > TAILLE_GRILLE) {
        bateauValide = false;
      }

      if (bateauValide) {
        m_listeBateaux[i].setPosition(ligne, colonne);
        lignesOccupees[ligne] = true;
        bateauPlace = true;
      }

      nbEssais++;
    }

    if (!bateauPlace) {
      return false;
    }
  }

  return true;
}
