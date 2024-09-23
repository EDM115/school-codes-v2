#include <stdio.h>
#include "nains.h"

int main(int argc, char**argv) {
  struct shm_struct *shm = open_shm();

  wait_status(shm, STATUS_KILL2);
  printf("[Oin] C'est l'heure du goûter !\n");
  printf("[Oin] Mais... Qui a mangé toute la tarte ?\n");
  printf("[Oin] Quel est l'identifiant du processus qui consomme beaucoup de CPU ?\n");

  int entered_cpu = -1;
  scanf("%d", &entered_cpu);
  if(entered_cpu == shm->expected_value) {
    set_status(shm, STATUS_TOP);
    printf("[Oin] Mais oui, c'est bien Dwalin !\n");
    printf("[Oin] Puisqu'il s'empiffre, on va le rendre moins prioritaire.\n");
    printf("[Oin] Changez la priorité du processus Dwalin à 17\n");
  } else {
    printf("[Oin] Mais non!\n");
    set_status(shm, STATUS_FAILURE);
    abort();
  }

  wait_status(shm, STATUS_SUCCESS);
  return EXIT_SUCCESS;
}
