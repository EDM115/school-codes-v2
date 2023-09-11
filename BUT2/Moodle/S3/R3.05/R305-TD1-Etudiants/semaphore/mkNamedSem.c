// (c) F. Merciol 
// Plus d'informations sur http://r305.merciol.fr
#include <stdio.h>
#include <errno.h>
#include <stdlib.h>

#include <fcntl.h>
#include <sys/stat.h>
#include <semaphore.h>

void usage (char *prog) {
  fprintf (stderr, "Usage: %s semName initialValue\n", prog);
  exit (-1);
}

int main (int argc, char** argv, char** envp) {

  if (argc != 3)
    usage (argv [0]);

  int value = atoi (argv [2]);
  sem_t *sem;
  if ((sem = sem_open (argv[1], O_CREAT, S_IRUSR|S_IWUSR, value)) == SEM_FAILED) {
    perror ("can't create semaphore");
    exit (1);
  }
}
