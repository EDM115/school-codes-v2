#ifndef _R204Test_hpp_
#define _R204Test_hpp_

#include<string>
#include<iostream>
// ========================================

const std::string BOLD ("[1m");
const std::string RED ("[0;31m");
const std::string GREEN ("[0;32m");
const std::string YELLOW ("[0;33m");
const std::string BLUE ("[0;34m");
const std::string MAGENTA ("[0;35m");
const std::string CYAN ("[0;36m");
const std::string NC ("[0m"); // No Color

inline void
msgOK (std::string msg) {
  std::cout << GREEN << BOLD << "[OK]" << NC << " " << msg << std::endl;
}

inline void
msgKO (std::string msg) {
  std::cout << RED << BOLD << "[BAD]" << NC << " " << msg << std::endl;
}

//  =======================================
#endif // _R204Test_hpp_
