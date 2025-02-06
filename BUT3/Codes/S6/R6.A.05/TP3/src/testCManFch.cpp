#include "testCManFch.h"

void testCConvert() {
  string testFile = "/home/edm115/Documents/R6.05/TP3/src/test.txt";
  string missingFile = "/home/edm115/Documents/R6.05/TP3/src/doesntexist.txt";

  CManFch test(testFile);
  CManFch missing(missingFile);

  test.afficherAttributs();
  test.afficherFichier();
  cout << test.getLigne(1, 2) << endl;
  cout << test.getLigne(1, 3) << endl;
  cout << test.getLigne(3, 4) << endl;
  cout << test.getLigne(5, 6) << endl;
  cout << test.getLigne(7, 8) << endl;
  cout << test.getLigne(-1, 0) << endl;

  missing.afficherAttributs();
  missing.afficherFichier();
  cout << missing.getLigne(1, 2) << endl;
}

int main() {
  testCConvert();

  return 0;
}
