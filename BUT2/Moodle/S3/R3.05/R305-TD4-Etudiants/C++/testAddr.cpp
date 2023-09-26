// compilation : g++ testAddr.cpp
// exécution   : ./a.out

#include<stdio.h>	// utilisation des E/S du langage C
#include <stdlib.h>	// utilisation de malloc et free

int
main (int argc, char **argv, char **envp) {

  int	*pi, *pti;

  pi = new int;
  *pi = 10;

  fprintf (stdout, "pi  0x%x : %d (0x%02x)\n", pi, *pi, *pi);

  delete pi;

  pti = new int[3];
  pti[0] = 20;
  pti[1] = 30;
  pti[2] = 40;

  for (int i (0); i < 3; ++i)
    fprintf (stdout, "pti 0x%x : %d (0x%02x)\n", &pti[i], pti[i], pti[i]);

  delete []pti;
  return 0;
}
