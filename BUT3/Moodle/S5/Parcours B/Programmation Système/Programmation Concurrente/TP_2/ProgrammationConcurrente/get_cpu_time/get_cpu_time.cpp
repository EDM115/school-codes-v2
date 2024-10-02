#include "get_cpu_time.h"

// Code adapted from https://stackoverflow.com/questions/17432502/how-can-i-measure-cpu-time-and-wall-clock-time-on-both-linux-windows/17440673#17440673

//  Windows
#ifdef _WIN32 // A 32-bit platform. This value is also defined by the 64-bit compiler for backward compatibility.
              // (see https://docs.microsoft.com/en-us/windows-hardware/drivers/kernel/64-bit-compiler)
#include <Windows.h>

// WARNING: Under Windows, you must have a different an elapsed time of at least 100 milliseconds,
//          otherwaise values returned by get_cpu_time() are not precise enough.
unsigned long long get_cpu_time(){
    FILETIME start, exit, kernel, user;
    if (GetProcessTimes(GetCurrentProcess(),&start,&exit,&kernel,&user) != 0){
        //  Returns total kernel and user time.
        return
            ((kernel.dwLowDateTime |
                (unsigned long long)kernel.dwHighDateTime << 32)
             + (user.dwLowDateTime |
                (unsigned long long)user.dwHighDateTime << 32)) / 10;
    }else{
        //  Handle error
        return 0;
    }
}

//  Posix/Linux
#else
#include <time.h>
#include <sys/time.h>

unsigned long long get_cpu_time(){
    return (unsigned long long)clock();
}
#endif
