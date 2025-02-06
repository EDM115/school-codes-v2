#ifndef CVOITURE1_H
#define CVOITURE1_H

#include "AllIncludes.h"

class CVoiture {
  private:
    int m_type;
    char* m_pNom;
    char* m_pCouleur;

  public:
    // Constructeurs et destructeur
    CVoiture(int typeVt, char* nomVt, char* coulVt);
    ~CVoiture();

    // Méthodes
    void setVoiture(int typeVt, char* nomVt, char* coulVt);
    void presentation() const;
};

#endif
