#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "nains.h"

int main(int argc, char**argv) {
  struct shm_struct *shm = open_shm();

  int pid = fork();
  pid_t ret;
  if(pid) {
    int status;
    ret = waitpid(pid, &status, 0);
    CHECK_WAIT(ret, pid, status, 0);
  } else {
    execlp("nains/farin", "farin", NULL);
    perror("execle failed\n");
  }

  wait_status(shm, STATUS_SUCCESS);
  return EXIT_SUCCESS;
}
