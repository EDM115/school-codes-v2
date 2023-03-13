#include <iostream>
// ou #include <Arduino.h>
#include <string>

#include "R204Test.h"
#include "R204Vector.h"

using namespace std;

// ========================================
template <class T>
ostream &
operator<< (ostream &out, const R204Vector<T> &v) {
  string sep ("");
  out << "{";
  for (size_t i = 0; i < v.size (); ++i) {
    out << sep << v [i];
    sep = ", ";
  }
  out << "}";
  return out;
}

// ========================================
void
testSize () {
  R204Vector<int> v;
  if (v.size ()) {
    msgKO ("testSize size!=0 ("+to_string (v.size ())+")");
    return;
  } 
  if (!v.empty ()) {
    msgKO ("testSize !empty ("+to_string (v.size ())+")");
    return;
  }
  v.push_back (1);
  if (!v.size ()) {
    msgKO ("testSize size==0 ("+to_string (v.size ())+")");
    return;
  } 
  if (v.empty ()) {
    msgKO ("testSize empty ("+to_string (v.size ())+")");
    return;
  }
  for (int i (0); i < 10; ++i)
    v.push_back (i);
  if (v.size () != 11) {
    msgKO ("testSize size!=11 ("+to_string (v.size ())+")");
    return;
  } 
 msgOK ("testSize");
}

// ========================================
void
testOutOfRange () {
  R204Vector<int> v;
  try {
    v.at (100);
    msgKO ("testSize at(100)");
    return;
  } catch (const std::out_of_range& oor) {
  }
  try {
    v[100];
  } catch (const std::out_of_range& oor) {
    msgKO ("testSize size[100] "+string (oor.what ()));
    return;
  }
  msgOK ("testOutOfRange");
}

// ========================================
void
testString () {
  string s[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", ""};

  R204Vector<string> v;
  int len (0);
  for (int i (0); s[i].size (); ++i, ++len)
    v.push_back (s[i]);
  if (v.size () != len) {
    msgKO ("testString size!="+to_string (len)+" ("+to_string (v.size ())+")");
    return;
  }
  for (int i (0); s[i].size (); ++i, ++len)
    if (v[i] != s[i]) {
      msgKO ("testString v["+to_string (i)+"]="+v[i]+" != "+s[i]);
      return;
    }
  msgOK ("testString");
}

// ========================================
void
testIterator () {
  string s[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", ""};

  R204Vector<string> v;
  int len (0);
  for (int i (0); s[i].size (); ++i)
    v.push_back (s[i]);
  for (auto it (v.begin ()); it != v.end (); ++it, ++len)
    if (*it != s[len]) {
      msgKO ("testIterator *it: "+*it+" != "+s[len]);
      return;
    }
  len = 0;
  v.foreach ([&len, &s] (const string &x) {
    if (x != s[len]) {
      msgKO ("testIterator x: "+x+" != "+s[len]);
      return;
    }
    ++len;
  });

  v.erase (v.begin ()+2, v.end () - 3);
  if (v.size () != 5) {
    cout << v << endl;
    msgKO ("testIterator erase len: "+to_string (v.size ())+" != 4");
    return;
  }
  len = 0;
  v.foreach ([&len, &s] (const string &x) {
    if (x != s[len]) {
      msgKO ("testIterator x: "+x+" != "+s[len]);
      return;
    }
    ++len;
    if (len == 2)
      len = 6;
  });

  msgOK ("testIterator");
}

// ========================================
int
main (int argc, char** argv) {
  cout << endl << "    ** Test R204Vector" << endl;

  testSize ();
  testOutOfRange ();
  testString ();
  testIterator ();

  cout << endl;
  return 0;
}

// ========================================
