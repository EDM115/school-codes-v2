#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "nains.h"


#define CHECK_SIGNAL(retval, _pid, _status)				\
  assert(retval == _pid);						\
  assert(WIFSIGNALED(_status));



int main(int argc, char**argv) {
  struct shm_struct *shm = open_shm();

  int pid = fork();
  int status;
  pid_t ret;
  if(pid) {
    int pid2 = fork();
    if(pid2) {

      ret = waitpid(pid, &status, 0);
      assert(ret == pid);
      assert(WIFSIGNALED(status));

      printf("[Groin] Mon fils Gloin est mort :'(\n");
      printf("[Groin] Cause du décès: 'Reception d'un signal' ?\n");
      set_status(shm, STATUS_KILL);

      ret = waitpid(pid2, &status, 0);
      CHECK_WAIT(ret, pid2, status, EXIT_SUCCESS);

    } else {
      execlp("nains/oin", "oin", NULL);
      perror("execle failed\n");
    }
  } else {
    execlp("nains/gloin", "gloin", NULL);
    perror("execle failed\n");
  }

  wait_status(shm, STATUS_SUCCESS);
  return EXIT_SUCCESS;
}
