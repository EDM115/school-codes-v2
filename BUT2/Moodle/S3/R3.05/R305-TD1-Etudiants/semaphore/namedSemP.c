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

  sem_t *sem;
  if ((sem = sem_open (argv[1], 0)) == SEM_FAILED) {
    perror ("can't find semaphore");
    exit (1);
  }

  if (sem_wait (sem)) {
    perror ("can't P");
    exit (1);
  }
}
