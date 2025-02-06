#include "Exo2.h"

int main() {
  char str[MAX];

  cout << "Enter a string : ";
  cin.getline(str, MAX, '\n');

  cout << "Length : " << strlen(str) << endl;

  for (size_t i = 0; i <= strlen(str); i++) {
    cout << "Char at position " << i << " : " << str[i] << endl;
  }

  return 0;
}
