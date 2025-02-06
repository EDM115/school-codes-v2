#ifndef CMANFCH_H
#define CMANFCH_H

#include "AllIncludes.h"

class CManFch {
  private:
    string m_nomFich;
    string m_emplcmtFich;
  
  public:
    CManFch();
    CManFch(string str);
    ~CManFch();
    void setNomFichier(string str);
    void afficherAttributs();
    void afficherFichier();
    string getLigne(int numLign1, int numLign2);
};

#endif
