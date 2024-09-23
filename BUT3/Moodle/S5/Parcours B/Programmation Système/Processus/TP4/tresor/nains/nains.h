#ifndef NAINS_H
#define NAINS_H

#include <unistd.h>
#include <stdlib.h>
#include <assert.h>
#include <sys/mman.h>
#include <sys/stat.h>        /* For mode constants */
#include <fcntl.h>           /* For O_* constants */

#define SLEEP_DURATION 10

#define my_assert(t) do { \
    if(!(t)) {		  \
      fprintf(stderr, "\n\n[Gandalf le blanc] : Il s'est passé un problème dans %s. Il faut tout recommencer !\n\n\n", __FILE__); \
assert(t);		  \
    }			  \
 } while(0)

#define CHECK_WAIT(retval, _pid, _status, expected_retcode)		\
  my_assert(retval == _pid);						\
  my_assert(WIFEXITED(_status));						\
  my_assert(WEXITSTATUS(_status) == expected_retcode);			\
  if(WIFSIGNALED(_status)) { printf("Process %d finished with a signal\n", _pid); abort();}


enum shm_status {
  STATUS_NONE,
  STATUS_INIT,
  STATUS_PS,
  STATUS_KILL,
  STATUS_KILL2,
  STATUS_TOP,
  STATUS_RENICE,
  STATUS_PROC,
  STATUS_FAILURE,
  STATUS_SUCCESS
};

struct shm_struct {
  enum shm_status status;
  int expected_value;
};

#define SHM_SIZE sizeof(struct shm_struct)

static void set_status(struct shm_struct *ptr, int status) __attribute__ ((unused));
static void wait_status(struct shm_struct *ptr, int status) __attribute__ ((unused));
static struct shm_struct *open_shm() __attribute__ ((unused));

static struct shm_struct *open_shm() {
  struct shm_struct*buffer = NULL;;
  int fd = shm_open("/moria", O_RDWR, 0666);
  if(ftruncate(fd, SHM_SIZE) <0) {
    perror("Problème lors de l'initialisation: ftruncate");
    exit(EXIT_FAILURE);
  }
  if((buffer = mmap(NULL, SHM_SIZE, PROT_READ|PROT_WRITE, MAP_SHARED, fd, 0))<0){
    perror("Problème lors de l'initialisation: mmap");
    exit(EXIT_FAILURE);
  }
  return buffer;
}



static void set_status(struct shm_struct *ptr, int status) {
  ptr->status = status;
}

static void wait_status(struct shm_struct *ptr, int status) {
  while(ptr->status != status) {
    if(ptr->status == STATUS_FAILURE)
      abort();
    sleep(1);
  }
}
#endif
