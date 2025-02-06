#include "CVoiture1.h"

CVoiture::CVoiture(int typeVt, char* nomVt, char* coulVt) : m_type(typeVt), m_pNom(nomVt), m_pCouleur(coulVt) {
  cout << "Construction de l'objet CVoiture d'adresse : " << this << endl;
}

CVoiture::~CVoiture() {
  cout << "Destruction de l'objet CVoiture d'adresse : " << this << endl;
}

void CVoiture::setVoiture(int typeVt, char* nomVt, char* coulVt) {
  m_type = typeVt;
  m_pNom = nomVt;
  m_pCouleur = coulVt;
}

void CVoiture::presentation() const {
  cout << "Voiture de type " << m_type << ", nom : " << m_pNom << ", couleur : " << m_pCouleur << endl;
}
