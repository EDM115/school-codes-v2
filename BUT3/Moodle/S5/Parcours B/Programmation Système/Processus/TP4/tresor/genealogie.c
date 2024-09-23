#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <unistd.h>
#include <assert.h>

int main() {
  int pid = fork();
  if(pid) {
    int status;
    pid_t ret = waitpid(pid, &status, 0);
    assert(ret == pid);
  } else {
    int ret = execlp("nains/durin", "durin", NULL);
    perror("execle failed\n");
  }
  return 0;
}
