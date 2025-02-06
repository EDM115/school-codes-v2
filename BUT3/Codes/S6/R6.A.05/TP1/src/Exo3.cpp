#include "Exo3.h"

int main() {
  char* pCh = new char[INT_MAX];
  int tab[26] = {0};

  cout << "Enter a string : ";
  cin.getline(pCh, INT_MAX, '\n');

  cout << "String : " << *pCh << endl;

  char* p1 = pCh;
  int* p2 = tab;

  while (*p1 != '\0') {
    if ((*p1 >= 'A' && *p1 <= 'Z') || (*p1 >= 'a' && *p1 <= 'z')) {
      if (*p1 >= 'A' && *p1 <= 'Z') {
        *p1 += 32;
      }

      int index = *p1 - 'a';
      *(p2 + index) += 1;
    }

    p1++;
  }

  for (int i = 0; i < 26; i++) {
    if (*(p2 + i) > 0) { 
      cout << "Occurrences de '" << static_cast<char>(i + 'a') << "' : " << *(p2 + i) << endl;
    }
  }

  delete[] pCh;

  return 0;
}
