#include <iostream>
#include <sstream>
#include <errno.h>
#include <iomanip>      // std::setw
#include <string>
#include <string.h>
#include <vector>
#include <ctime>
#include <linux/byteorder/little_endian.h>

using namespace std;

// ========================================
struct minix_inode {
  __u16 i_mode ;
  __u16 i_uid ;
  __u32 i_size ;
  __u32 i_time ;
  __u8 i_gid ;
  __u8 i_nlinks ;
  __u16 i_zone [9];
};

struct minix_dir_entry {
  __u16 inode ;
  char name [30];
};

const int zonePerUa (0x400 / sizeof (__u16));

