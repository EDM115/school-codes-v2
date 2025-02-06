#include "CVoiture2.h"

CVoiture::CVoiture(int typeVt, char* nomVt, char* coulVt) : m_type(typeVt) {
  m_pNom = new char[strlen(nomVt) + 1];
  strcpy(m_pNom, nomVt);

  m_pCouleur = new char[strlen(coulVt) + 1];
  strcpy(m_pCouleur, coulVt);

  cout << "Construction de l'objet CVoiture d'adresse : " << this << endl;
}

CVoiture::CVoiture(const CVoiture& vt) : m_type(vt.m_type) {
  m_pNom = new char[strlen(vt.m_pNom) + 1];
  strcpy(m_pNom, vt.m_pNom);

  m_pCouleur = new char[strlen(vt.m_pCouleur) + 1];
  strcpy(m_pCouleur, vt.m_pCouleur);

  cout << "Copy-construction de l'objet CVoiture d'adresse : " << this << endl;
}

CVoiture::~CVoiture() {
  delete[] m_pNom;
  delete[] m_pCouleur;
  cout << "Destruction de l'objet CVoiture d'adresse : " << this << endl;
}

void CVoiture::setVoiture(int typeVt, char* nomVt, char* coulVt) {
  delete[] m_pNom;
  delete[] m_pCouleur;

  m_type = typeVt;

  m_pNom = new char[strlen(nomVt) + 1];
  strcpy(m_pNom, nomVt);

  m_pCouleur = new char[strlen(coulVt) + 1];
  strcpy(m_pCouleur, coulVt);
}

void CVoiture::presentation() const {
  cout << "Voiture de type " << m_type << ", nom : " << m_pNom << ", couleur : " << m_pCouleur << endl;
}
