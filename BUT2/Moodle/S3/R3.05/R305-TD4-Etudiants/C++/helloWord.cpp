// compilation : g++ helloWord.cpp
// exécution   : ./a.out

#include<stdio.h> // utilisation des E/S du langage C

int
main (int argc, char **argv, char **envp) {
  const char *firstName ("Charles Lucien");

  fprintf (stdout, "Bonjour %s.\n", firstName);
  fprintf (stdout, "Oups, je veux dire %s.\n", firstName+8);
  
  return 0;
}
