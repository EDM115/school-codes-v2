/*!
 * @file R204Misc.h
 * @brief Définie les classes R204Iterator et R204ConstIterator
 * @author F. Merciol
 * @version 0.1
 * @date 13 février 2023
 */
#ifndef _Misc_h_
#define _Misc_h_

#include "R204Vector.h"
// ========================================

inline R204Vector<String> split (String s, char c);

//  =======================================
R204Vector<String>
split (String s, char c) {
  R204Vector<String> result;
  for (int begin (0); ; ) {
    int end = s.indexOf (c, begin);
    if (end < 0)
      break;
    result.push_back (s.substring (begin, end));
    begin = end+1;
  }
  return result;
}

// ========================================
#endif // _Misc_h_
