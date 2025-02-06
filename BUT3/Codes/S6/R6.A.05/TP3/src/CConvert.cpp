#include "CConvert.h"

CConvert::CConvert() : m_str(""), m_dbl(0) {
  cout << "Construction de l'objet CConvert vide" << endl;
}

CConvert::CConvert(string theCh) {
  cout << "Construction de l'objet CConvert avec une chaine de caractères" << endl;
  m_str = theCh;
  istringstream iss(theCh);
  iss >> m_dbl;
}

CConvert::CConvert(double theD) {
  cout << "Construction de l'objet CConvert avec un réel" << endl;
  m_dbl = theD;
  ostringstream oss;
  oss << theD;
}

CConvert::~CConvert() {
  cout << "Destruction de l'objet CConvert" << endl;
}

string CConvert::getString() const {
  return m_str;
}

double CConvert::getReel() const {
  return m_dbl;
}
