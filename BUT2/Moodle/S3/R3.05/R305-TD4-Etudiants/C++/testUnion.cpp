// compilation : g++ testAddr.cpp
// exécution   : ./a.out

#include<stdio.h>	// utilisation des E/S du langage C
#include <stdlib.h>	// utilisation de malloc et free

union U {
  int i;
  unsigned char b[4];
  struct {
    unsigned char b0, b1, b2, b3;
  } s1;
  struct {
    short s;
    unsigned short us;
  } s2;
  struct {
    int c1:12;
    int c2:2;
    int c3:18;
  } s3;
};

int
main (int argc, char **argv, char **envp) {

  fprintf (stdout, "sizeof int : %u \n", sizeof (int));

  U u;

  u.i = 7;
  //u.i = 0x80A5FF;
  if (argc > 1)
    sscanf (argv [1], "%d", &u.i);
  
  fprintf (stdout, "u.i : 0x%08x \n", u.i);
  fprintf (stdout, "u.s1.b0 : %x \n", u.s1.b0);
  for (int i (0); i < 4; ++i)
    fprintf (stdout, "u.b[%d] : %x \n", i, u.b[i]);

  u.s1.b2 = 0xA5;
  fprintf (stdout, "u.i : %08x \n", u.i);

  u.s3.c2 = 3; // essayez avec = 7
  fprintf (stdout, "u.s3.c2 <= %d \n", u.s3.c2);
  fprintf (stdout, "u.i : %08x \n", u.i);

  fprintf (stdout, "u.s2.s : %04x \n", u.s2.s);
  fprintf (stdout, "u.s2.us : %04x \n", u.s2.us);

  return 0;
}
