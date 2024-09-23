#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "nains.h"

#define BUF_SIZE 1024
int main(int argc, char**argv) {
  struct shm_struct *shm = open_shm();

  int status;
  int pid = fork();
  pid_t ret;
  if(pid) {
    int pid2 = fork();
    if(pid2) {
      ret = waitpid(pid2, &status, 0);
      CHECK_WAIT(ret, pid2, status, 0);
    } else {
      // pour "cacher" le deuxieme parametre lorsqu'on utilise la commande ps
      // -> obligé de passer par /proc/<PID>/cmdline
      char dummy_str[1024];
      int i;
      for(i=0; i<BUF_SIZE-1; i++) {
	dummy_str[i] = 'a'+(i%26);
      }
      dummy_str[BUF_SIZE-1] = '\0';
      execlp("nains/balin", "balin", dummy_str, "chaussette", NULL);
      perror("execle failed\n");
    }
    ret = waitpid(pid, &status, 0);
    CHECK_WAIT(ret, pid, status, 0);
  } else {
    execlp("nains/dwalin", "dwalin", NULL);
    perror("execle failed\n");
  }

  wait_status(shm, STATUS_SUCCESS);
  return EXIT_SUCCESS;
}
