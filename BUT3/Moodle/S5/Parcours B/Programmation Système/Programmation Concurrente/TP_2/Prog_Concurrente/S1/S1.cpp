#include <iostream>
#include <chrono>
#include "../get_cpu_time/get_cpu_time.h"
using namespace std::chrono;

constexpr int nb_count = 100000000;

int main()
{
    auto counter = nb_count;

    auto start = high_resolution_clock::now();
    auto start_cpu = get_cpu_time();
    auto i = nb_count;
    while (i > 0) {
        --counter;
        --i;
    }
    auto end_cpu = get_cpu_time();
    auto stop = high_resolution_clock::now();

    // Predefined units are nanoseconds, microseconds, milliseconds, seconds, minutes, hours.
    // See https://www.geeksforgeeks.org/measure-execution-time-function-cpp/
    auto duration = duration_cast<microseconds>(stop - start);

    std::cout << "counter = " << counter;
    std::cout << " (elapsed time = " << duration.count() << " microseconds";
    std::cout << " ; CPU time = " << (end_cpu - start_cpu) << " microseconds)" << std::endl;
    return 0;
}
