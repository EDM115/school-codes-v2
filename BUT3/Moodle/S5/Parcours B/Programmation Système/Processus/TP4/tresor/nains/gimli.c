#include <stdio.h>
#include <string.h>
#include "nains.h"

int main(int argc, char**argv) {
  pid_t entered_pid;
  pid_t ppid = getppid();
  struct shm_struct *shm = open_shm();

  printf("[Gimli] Remettons un peu d'ordre dans ses notes...\n");
  printf("[Gimli] Pour commencer, j'ai besoin de retrouver la fiche concernant mon père.\n");
  printf("[Gimli] Quel est l'identifiant (PID) de mon père ?\n");

  scanf("%d", &entered_pid);

  if(entered_pid != ppid) {
    printf("[Gimli] Hum... Non, ce n'est pas la bonne fiche ! \n");
    printf("[Gimli] Puisque c'est comme ça, Je m'en vais !\n");
    set_status(shm, STATUS_FAILURE);
    return EXIT_FAILURE;
  } else {
    set_status(shm, STATUS_PS);
    printf("[Gimli] Oui, c'est bien la fiche de Gloin.\n");
    printf("[Gimli] Mais elle est erronnée ! Il devrait être mort !\n");
    printf("[Gimli] Corrigez cela en tuant le processus gloin.\n");
    wait_status(shm, STATUS_KILL);
    printf("[Gimli] OK, mon père est mort\n");
    set_status(shm, STATUS_KILL2);

    wait_status(shm, STATUS_RENICE);
    sleep(5);
    printf("[Gimli] Bon, fini la pause. On retourne au boulot !\n");
    printf("[Gimli] On a presque fini en plus. Il ne reste plus qu'à retrouver l'endroit où Balin a rangé les clés de l'écurie.\n");
    printf("[Gimli] Le plus simple pour les retrouver serait de regarder le deuxième paramètre passé au processus balin\n");
    printf("[Gimli] Quel est ce paramètre ?\n");
    char buffer[1024];
    scanf("%s", buffer);
    if(strncmp(buffer, "chaussette", strlen("chaussette")) == 0) {
      set_status(shm, STATUS_PROC);

    } else {
      printf("[Gimli] '%s' ? Mais ça ne veut rien dire ! C'est nul comme indice !\n", buffer);
      set_status(shm, STATUS_FAILURE);
      return EXIT_FAILURE;
    }
  }

  wait_status(shm, STATUS_SUCCESS);
  return EXIT_SUCCESS;
}
