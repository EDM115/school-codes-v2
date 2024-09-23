#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "nains.h"

int main(int argc, char**argv) {
  struct shm_struct*buffer;
  int fd = shm_open("/moria", O_RDWR|O_CREAT, 0666);
  if(ftruncate(fd, SHM_SIZE) <0) {
    perror("Problème lors de l'initialisation: ftruncate");
    exit(EXIT_FAILURE);
  }
  if((buffer = mmap(NULL, SHM_SIZE, PROT_READ|PROT_WRITE, MAP_SHARED, fd, 0))<0){
    perror("Problème lors de l'initialisation: mmap");
    exit(EXIT_FAILURE);
  }

  set_status(buffer, STATUS_INIT);

  int pid = fork();
  pid_t ret;
  if(pid) {
    int status;
    ret = waitpid(pid, &status, 0);
    CHECK_WAIT(ret, pid, status, 0);
  } else {
    execlp("nains/thorin", "thorin", NULL);
    perror("execle failed\n");
  }

  wait_status(buffer, STATUS_SUCCESS);
  return EXIT_SUCCESS;
}
