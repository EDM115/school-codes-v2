#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <assert.h>
#include "nains.h"

int main() {
  struct shm_struct *shm = open_shm();

  int pid = fork();
  int status;
  pid_t ret;
  if(pid) {
    ret = wait(&status);
    CHECK_WAIT(ret, pid, status, 0);

  } else {
    execlp("nains/gimli", "gimli", NULL);
    perror("execle failed\n");
  }

  wait_status(shm, STATUS_SUCCESS);
  return EXIT_SUCCESS;
}
