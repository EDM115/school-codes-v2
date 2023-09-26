// compilation : g++ testParam.cpp
// exécution   : ./a.out a b c

#include<stdio.h> // utilisation des E/S du langage C

int
main (int argc, char **argv, char **envp) {

  for (int i (0); i < argc; ++i)
    fprintf (stdout, "%2d - %s\n", i, argv [i]);

  for (int i (0); i < 5 && envp [i]; ++i)
    fprintf (stdout, "   %s\n", envp [i]);
  fprintf (stdout, "   ...\n");
  
  return 0;
}
