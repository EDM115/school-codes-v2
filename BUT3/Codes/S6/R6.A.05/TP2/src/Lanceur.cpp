#include "CPoint.h"

void fonction1(CPoint theP) {
  cout << "Appel de fonction1 (passage par valeur):" << endl;
  theP.presentation();
}

void fonction2(CPoint& theP) {
  cout << "Appel de fonction2 (passage par référence):" << endl;
  theP.presentation();
}

void fonction3(CPoint* pTheP) {
  cout << "Appel de fonction3 (passage par pointeur):" << endl;

  if (pTheP) {
    pTheP->presentation();
  }
}

void fonction4(CPoint* pTabPt, int tailleTab) {
  cout << "Appel de fonction4 (tableau de points):" << endl;

  for (int i = 0; i < tailleTab; ++i) {
    pTabPt[i].presentation();
  }
}

int main() {
    CPoint p1(1, 2);
    fonction1(p1);

    CPoint p2(3, 4);
    fonction2(p2);

    CPoint* p3 = new CPoint(5, 6);
    fonction3(p3);
    delete p3;

    CPoint tabPoints[3] = {CPoint(7, 8), CPoint(9, 10), CPoint(11, 12)};
    fonction4(tabPoints, 3);

    return 0;
}
