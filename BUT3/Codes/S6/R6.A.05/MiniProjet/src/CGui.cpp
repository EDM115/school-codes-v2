#include "CGui.h"

CGui::CGui() {
  m_pArmada = NULL;
  m_pCoups = NULL;
}

CGui::CGui(CArmada* pArmada, CCoups* pCoups) {
  m_pArmada = pArmada;
  m_pCoups = pCoups;
}

CGui::CGui(const CGui& other) {
  m_pArmada = other.m_pArmada;
  m_pCoups = other.m_pCoups;
}

CGui::~CGui() {
  m_pArmada = NULL;
  m_pCoups = NULL;
}

CGui& CGui::operator=(const CGui& other) {
  if (this != &other) {
    m_pArmada = other.m_pArmada;
    m_pCoups = other.m_pCoups;
  }

  return *this;
}

ostream& operator<<(ostream& os, CGui& theG) {
  theG.remplirDeuxGrilles(os);

  return os;
}

void CGui::setArmadaCoups(CArmada* pArmada, CCoups* pCoups) {
  m_pArmada = pArmada;
  m_pCoups = pCoups;
}

bool CGui::positionnerBateaux() {
  return m_pArmada->placerAleatoirement();
}

pair<int, int> CGui::choisirAttaque() {
  int ligne, colonne;
  cout << "Entrez la ligne de votre attaque : ";
  cin >> ligne;
  cout << "Entrez la colonne de votre attaque : ";
  cin >> colonne;

  if (ligne < 0 || ligne >= TAILLE_GRILLE - 1 || colonne < 0 || colonne >= TAILLE_GRILLE - 1) {
    throw invalid_argument("Attaque hors de la grille");
  }

  return make_pair(ligne, colonne);
}

void CGui::afficheGagne() {
  cout << "Vous avez gagné !" << endl;
}

void CGui::affichePerdu() {
  cout << "Vous avez perdu !" << endl;
}

void CGui::remplirDeuxGrilles(ostream& os) {
  // Initialisation de la grille du joueur
  for (int i = 0; i < TAILLE_GRILLE - 1; i++) {
    for (int j = 0; j < TAILLE_GRILLE - 1; j++) {
      m_grilleJou[i][j] = '-';
    }
  }

  // Placement des bateaux du joueur sur sa grille
  for (int i = 0; i < m_pArmada->getEffectifTotal(); i++) {
    CBateau* b = m_pArmada->getBateau(i);
    pair<int, int> pos = b->getPosition();

    for (int j = 0; j < b->getTaille(); j++) {
      if (b->getDegats(j)) {
        m_grilleJou[pos.first][pos.second + j] = 'X';
      } else {
        m_grilleJou[pos.first][pos.second + j] = 'B';
      }
    }
  }

  // Marquer les tirs adverses sur la grille du joueur
  for (int i = 0; i < m_pCoups->getTaille("ploufAdverse"); i++) {
    pair<int, int> pos = m_pCoups->getPair("ploufAdverse", i);
    m_grilleJou[pos.first][pos.second] = 'O';
  }

  afficherLaGrille(os, "joueur");

  // Initialisation de la grille de l'adversaire
  for (int i = 0; i < TAILLE_GRILLE - 1; i++) {
    for (int j = 0; j < TAILLE_GRILLE - 1; j++) {
      m_grilleAdv[i][j] = '-';
    }
  }

  // Affichage des tirs réussis du joueur sur l'adversaire
  for (int i = 0; i < m_pCoups->getCoupsBut(); i++) {
    pair<int, int> pos = m_pCoups->getPair("touche", i);
    m_grilleAdv[pos.first][pos.second] = 'X';
  }

  // Affichage des tirs ratés sur l'adversaire
  for (int i = 0; i < m_pCoups->getTaille("dansLEau"); i++) {
    pair<int, int> pos = m_pCoups->getPair("dansLEau", i);
    m_grilleAdv[pos.first][pos.second] = 'O';
  }

  afficherLaGrille(os, "adversaire");
}

void CGui::afficherLaGrille(ostream& os, string jouOuAdv) {
  if (jouOuAdv == "joueur") {
    os << "Grille du joueur : " << endl;
  } else if (jouOuAdv == "adversaire") {
    os << "Grille de l'adversaire : " << endl;
  }

  for (int i = 0; i < TAILLE_GRILLE - 1; i++) {
    os << i << " ";

    for (int j = 0; j < TAILLE_GRILLE - 1; j++) {
      if (jouOuAdv == "joueur") {
        os << m_grilleJou[i][j] << " ";
      } else if (jouOuAdv == "adversaire") {
        os << m_grilleAdv[i][j] << " ";
      }
    }

    os << endl;
  }

  os << "• ";

  for (int i = 0; i < TAILLE_GRILLE - 1; i++) {
    os << i << " ";
  }

  os << endl;
}
