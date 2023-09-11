// (c) F. Merciol 
// Plus d'informations sur http://r305.merciol.fr
#include <stdio.h>
#include <errno.h>
#include <stdlib.h>

#include <semaphore.h>

void usage (char *prog) {
  fprintf (stderr, "Usage: %s semName\n", prog);
  exit (-1);
}

int main (int argc, char** argv, char** envp) {
 
  if (argc != 2)
    usage (argv [0]);
 
  if (sem_unlink (argv[1])) {
    perror ("can't remove semaphore");
    exit(1);
  }
}
