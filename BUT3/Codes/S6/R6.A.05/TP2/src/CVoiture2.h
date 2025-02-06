#ifndef CVOITURE2_H
#define CVOITURE2_H

#include "AllIncludes.h"

class CVoiture {
  private:
    int m_type;
    char* m_pNom;
    char* m_pCouleur;

  public:
    // Constructeurs et destructeur
    CVoiture(int typeVt, char* nomVt, char* coulVt);
    CVoiture(const CVoiture& vt);
    ~CVoiture();

    // Méthodes
    void setVoiture(int typeVt, char* nomVt, char* coulVt);
    void presentation() const;
};

#endif
