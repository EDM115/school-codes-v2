#include <iostream>
// ou #include <Arduino.h>
#include <string>

#include "R204Test.h"
#include "R204Map.h"

using namespace std;

// ========================================
template <class K, class T>
ostream &
operator<< (ostream &out, const R204Map<K, T> &m) {
  string sep ("");
  out << "{";
  for (auto it (m.begin ());
       it != m.end ();
       ++it) {
    out << sep << it->key << "=>" << it->value;
    sep = ", ";
  }
  out << "}";
  return out;
}

// ========================================
void
testSize () {
  R204Map<string, string> m;
  if (m.size ()) {
    msgKO ("testSize size!=0 ("+to_string (m.size ())+")");
    return;
  } 
  if (!m.empty ()) {
    msgKO ("testSize !empty ("+to_string (m.size ())+")");
    return;
  }
  m ["a"] = "x";
  m ["b"] = "y";
  m ["c"] = "z";
  if (m.empty ()) {
    msgKO ("testSize empty ("+to_string (m.size ())+")");
    return;
  }
  if (m.size () != 3) {
    msgKO ("testSize size==0 ("+to_string (m.size ())+")");
    return;
  } 
  
 msgOK ("testSize");
}

// ========================================
void
testString () {
  string key [] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", ""};
  string val [] = {"l", "m", "n", "o", "p", "q", "r", "s", "t", ""};
  R204Map<string, string> m;

  int len (0);
  for (int i (0); key [i].size (); ++i)
    m [key [i]] = val [i];

  // cout << "m: " << m << endl;

  try {
    m.at ("z");
    msgKO ("testIterator: no exception have been raised");
    return;
  } catch (const std::out_of_range& oor) {
  }
  msgOK ("testString");
}

// ========================================
int
main (int argc, char** argv) {
  cout << endl << "    ** Test R204Map" << endl;

  testSize ();
  testString ();

  cout << endl;
  return 0;
}

// ========================================
