// g++ catByte.cpp
// ./a.out 42 A5 7E

#include <iostream>
#include <iomanip>      // std::setw
#include <stdio.h>
#include <stdlib.h>
#include <algorithm>    // std::fill

using namespace std;

int
main (int argc, char** argv, char** envp) {

  int length (argc-1);
  int length2 (length-1);
  unsigned char tab [length];
  for (int i = 1; i < argc; i++) {
    unsigned int adresse;
    sscanf (argv[i], "%x", &adresse);
    tab [i-1] = adresse & 0xFF;
  }
  unsigned char tab2 [length2];
  fill (tab2, tab2+length2, 0);
  
  for (int i = 0; i < length2; i++) {
    tab2 [i] = (tab[i] << 4) & 0xF0;
    tab2 [i] |= (tab [i+1] >> 4) & 0x0F;
  }

  cout << hex;
  for (int i (0); i < length2; ++i) {
    if (! (i % 16))
      cout << endl;
    cout << setw (2) << setfill ('0') << (unsigned int) tab2[i] << " ";
  }

  return 0;
}
