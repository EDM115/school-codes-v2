#include "CManFch.h"

CManFch::CManFch() : m_nomFich(""), m_emplcmtFich("") {
  cout << "Construction de l'objet CManFch vide" << endl;
}

CManFch::CManFch(string str) {
  cout << "Construction de l'objet CManFch avec une chaine de caractères" << endl;
  size_t pos = str.find_last_of("/");
  m_emplcmtFich = str.substr(0, pos + 1);
  m_nomFich = str.substr(pos + 1);
}

CManFch::~CManFch() {
  cout << "Destruction de l'objet CManFch" << endl;
}

void CManFch::setNomFichier(string str) {
  size_t pos = str.find_last_of("/");
  m_emplcmtFich = str.substr(0, pos + 1);
  m_nomFich = str.substr(pos + 1);
}

void CManFch::afficherAttributs() {
  cout << "Nom du fichier : " << m_nomFich << endl;
  cout << "Emplacement du fichier : " << m_emplcmtFich << endl;
}

void CManFch::afficherFichier() {
  ifstream fichier(m_emplcmtFich + m_nomFich);

  if (fichier && m_nomFich.substr(m_nomFich.find_last_of(".") + 1) == "txt") {
    string ligne;
    int i = 1;

    while (getline(fichier, ligne)) {
      cout << i << ":\t" << ligne << endl;
      i++;
    }

    fichier.close();
  } else {
    cerr << "Impossible d'ouvrir le fichier " << m_nomFich << endl;
  }
}

string CManFch::getLigne(int numLign1, int numLign2) {
  ifstream fichier(m_emplcmtFich + m_nomFich);
  string ligne;
  string res;
  int i = 1;

  if (fichier && m_nomFich.substr(m_nomFich.find_last_of(".") + 1) == "txt") {
    while (getline(fichier, ligne)) {
      if (i == numLign1 || i == numLign2) {
        res += ligne;
        res += " ";
      }

      i++;
    }

    fichier.close();
  } else {
    cerr << "Impossible d'ouvrir le fichier " << m_nomFich << endl;
  }

  return res;
}
