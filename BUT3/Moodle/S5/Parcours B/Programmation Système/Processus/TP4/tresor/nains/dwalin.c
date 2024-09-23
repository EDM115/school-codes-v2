#include <stdio.h>
#include <unistd.h>
#include <sys/time.h>
#include <sys/resource.h>
#include "nains.h"

int main(int argc, char**argv) {
  struct shm_struct *shm = open_shm();
  wait_status(shm, STATUS_KILL2);
  shm->expected_value = getpid();

  while(1) {
    int prio = getpriority(PRIO_PROCESS, 0);
    if(prio == 17) {
      goto out;
    }
  }
 out:
  printf("[Dwalin] Bon d'accord, je vais laisser quelques parts de tarte aux autres...\n");
  set_status(shm, STATUS_RENICE);

  wait_status(shm, STATUS_SUCCESS);
  return EXIT_SUCCESS;
}
