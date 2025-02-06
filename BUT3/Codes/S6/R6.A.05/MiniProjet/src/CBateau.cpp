#include "CBateau.h"

CBateau::CBateau() {
  m_taille = 0;
  m_nom = "neant";
  m_position = make_pair(0, 0);
  m_pDegats = NULL;
}

CBateau::CBateau(string n, pair<int, int> p, int t) {
  if (t < 0) {
    throw invalid_argument("La taille doit être positive.");
  }

  if (p.first < 0 || p.second < 0) {
    throw invalid_argument("La position doit être positive en x et y.");
  }

  m_taille = t;
  m_nom = n;
  m_position = p;
  m_pDegats = new bool[t];

  for (int i = 0; i < t; i++) {
    m_pDegats[i] = false;
  }
}

CBateau::CBateau(const CBateau& other) {
  m_taille = other.m_taille;
  m_nom = other.m_nom;
  m_position = other.m_position;
  m_pDegats = new bool[m_taille];

  for (int i = 0; i < m_taille; ++i) {
    m_pDegats[i] = other.m_pDegats[i];
  }
}

CBateau::~CBateau() {
  delete[] m_pDegats;
}

CBateau& CBateau::operator=(const CBateau& other) {
  if (this != &other) {
    delete[] m_pDegats;

    m_taille = other.m_taille;
    m_nom = other.m_nom;
    m_position = other.m_position;
    m_pDegats = new bool[m_taille];

    for (int i = 0; i < m_taille; ++i) {
      m_pDegats[i] = other.m_pDegats[i];
    }
  }

  return *this;
}

ostream& operator<<(ostream& os, CBateau& theB) {
  os << "Nom : " << theB.m_nom << endl;
  os << "Position : " << theB.m_position.first << " " << theB.m_position.second << endl;
  os << "Taille : " << theB.m_taille << endl;
  os << "Degats : ";

  for (int i = 0; i < theB.m_taille; i++) {
    os << theB.m_pDegats[i] << " ";
  }

  os << endl;

  return os;
}

bool CBateau::getDegats(int i) {
  return m_pDegats[i];
}

int CBateau::getTaille() const {
  return m_taille;
}

string CBateau::getNom() const {
  return m_nom;
}

pair<int, int> CBateau::getPosition() const {
  return m_position;
}

void CBateau::setPosition(int i, int j) {
  m_position = make_pair(i, j);
}

bool CBateau::estCoule() {
  for (int i = 0; i < m_taille; i++) {
    if (!m_pDegats[i]) {
      return false;
    }
  }

  return true;
}

bool CBateau::tirAdverse(pair<int, int> p) {
  if (p.first == m_position.first && p.second >= m_position.second && p.second < m_position.second + m_taille) {
    m_pDegats[p.second - m_position.second] = true;

    return true;
  }

  return false;
}
