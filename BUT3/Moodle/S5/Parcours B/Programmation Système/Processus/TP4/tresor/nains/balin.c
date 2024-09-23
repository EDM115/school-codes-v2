#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include "nains.h"

int main(int argc, char**argv) {
  assert(argc == 3);
  assert(strcmp(argv[2], "chaussette") == 0);

  struct shm_struct *shm = open_shm();
  wait_status(shm, STATUS_PROC);

  printf("[Balin] Ah oui je me souviens ! J'ai laissé la clé dans ma chaussette !\n");
  printf("[Balin] On va pouvoir aller à la taverne maintenant. A plus !\n");

  set_status(shm, STATUS_SUCCESS);


  wait_status(shm, STATUS_SUCCESS);

  return EXIT_SUCCESS;
}
