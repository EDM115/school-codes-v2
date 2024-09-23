#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>
#include <sys/time.h>
#include <sys/resource.h>


unsigned calcul(int n) {
  int i, j;
  unsigned res = 0;

  for(i=0; i<n; i++) {
    for(j=0; j<i; j++) {
      res+=j;
    }
  }

  return res;
}

int main(int argc, char**argv) {
  if(argc!=2) {
    printf("Usage: %s <N>\n", argv[0]);
    return 1;
  }


  int n=atoi(argv[1]);

  int prio = getpriority(PRIO_PROCESS, 0);
  printf("[%d] Début du calcul jusqu'à %d. Ma priorité: %d\n", getpid(), n, prio);
  struct timeval t1, t2;
	gettimeofday(&t1, 0);
  unsigned res = calcul(n);
	gettimeofday(&t2, 0);

  double duration = ((t2.tv_sec-t1.tv_sec)*1e6 + (t2.tv_usec-t1.tv_usec))/1e3;
  prio = getpriority(PRIO_PROCESS, 0);
  printf("[%d] Le résultat: %u. Calculé en %0.2lf ms. Ma priorité: %d\n", getpid(), res, duration, prio);
  return 0;
}
