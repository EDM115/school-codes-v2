#include "testCPoint.h"

int main() {
  testConstructeur1EtAccesseurs();
  testConstructeur2EtAccesseurs();
  testPresentation();
  return 0;
}

void testConstructeur1EtAccesseurs() {
  cout << "\nTest Constructeur avec arguments et accesseurs" << endl;
  CPoint p(3, 4);
  cout << "Abscisse : " << p.getAbscisse() << ", Ordonnee : " << p.getOrdonnee() << endl;
}

void testConstructeur2EtAccesseurs() {
  cout << "\nTest Constructeur sans arguments et accesseurs" << endl;
  CPoint p;
  cout << "Abscisse : " << p.getAbscisse() << ", Ordonnee : " << p.getOrdonnee() << endl;
}

void testPresentation() {
  cout << "\nTest de la méthode presentation()" << endl;
  CPoint p(5, 6);
  p.presentation();
}
