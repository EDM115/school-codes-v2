// g++ readFile.cpp 
// ./a.out secret.data 10 20 30

#include <stdio.h>
#include <stdlib.h>

int
main (int argc, char** argv, char** envp) {
  FILE *file = fopen (argv[1], "r");

  for (int i = 2; i < argc; i++) {
    int adresse;
    sscanf (argv[i], "%x", &adresse);
    //fprintf (stdout, "coucou %x = %d\n", adresse, adresse);
    fseek (file, adresse, SEEK_SET);
    int valeur = 0;
    fread (&valeur, sizeof (char), 1, file);
    fprintf (stdout, "0x%04X : 0x%04X\n", adresse, valeur);
  }
  return 0;
}
