#ifndef CPOINT_H
#define CPOINT_H

#include "AllIncludes.h"

class CPoint {
  // attributs
  private:
    int m_abs;
    int m_ord;

  // méthodes
  public:
    // constructeurs
    CPoint();
    CPoint(int x, int y);

    // destructeur
    ~CPoint();

    // accesseurs
    int getAbscisse() const;
    int getOrdonnee() const;
    void setAbscisse(int x);
    void setOrdonnee(int y);

    // méthodes
    void presentation() const;

};
#endif
