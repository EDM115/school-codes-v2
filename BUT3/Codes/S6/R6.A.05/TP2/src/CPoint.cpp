#include "CPoint.h"

CPoint::CPoint() : m_abs(0), m_ord(0) {
  cout << "Construction de l'objet CPoint d'adresse : " << this << endl;
}

CPoint::CPoint(int x, int y) : m_abs(x), m_ord(y) {
  cout << "Construction de l'objet CPoint d'adresse : " << this << endl;
}

CPoint::~CPoint() {
  cout << "Destruction de l'objet CPoint d'adresse : " << this << endl;
}

int CPoint::getAbscisse() const {
  return m_abs;
}

int CPoint::getOrdonnee() const {
  return m_ord;
}

void CPoint::setAbscisse(int x) {
  m_abs = x;
}

void CPoint::setOrdonnee(int y) {
  m_ord = y;
}

void CPoint::presentation() const {
  cout << "Point(" << m_abs << ", " << m_ord << ")" << endl;
}
