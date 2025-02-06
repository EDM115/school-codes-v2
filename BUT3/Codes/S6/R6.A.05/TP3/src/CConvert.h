#ifndef CCONVERT_H
#define CCONVERT_H

#include "AllIncludes.h"

class CConvert {
  private:
    string m_str;
    double m_dbl;
  
  public:
    CConvert();
    CConvert(string theCh);
    CConvert(double theD);
    ~CConvert();

    string getString() const;
    double getReel() const;
};

#endif
