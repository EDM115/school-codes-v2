#include "testCConvert.h"

void testCConvert() {
  CConvert c1;
  CConvert c2("Hello");
  CConvert c3(3.14);
  cout << "c1 : " << c1.getString() << " " << c1.getReel() << endl;
  cout << "c2 : " << c2.getString() << " " << c2.getReel() << endl;
  cout << "c3 : " << c3.getString() << " " << c3.getReel() << endl;

  CConvert c4("120");
  cout << "c4 : " << c4.getString() << " " << c4.getReel() << endl;
}

int main() {
  testCConvert();

  return 0;
}
