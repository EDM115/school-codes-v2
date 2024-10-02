#ifndef _GET_CPU_TIME_H
#define _GET_CPU_TIME_H

// Returns CPU time in microseconds
// WARNING: Under Windows, you must have a different an elapsed time of at least 100 milliseconds,
//          otherwaise values returned by get_cpu_time() are not precise enough.
unsigned long long get_cpu_time();

#endif /* _GET_CPU_TIME_H */
