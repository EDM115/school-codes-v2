#include <iostream>
#include <vector>
#include <thread>
#include "get_cpu_time.h"
#include <chrono>
using namespace std::chrono;

constexpr int nb_count = 100000000;
constexpr int nb_threads = 4;

void computeDecrement(const int nb_decrement, int& result) {
    int totalDecrement = 0;
    for (auto i = nb_decrement ; i > 0; --i) {
        ++totalDecrement;
    }
    result = totalDecrement;
}

int main()
{
    auto counter = nb_count;

    auto start = high_resolution_clock::now();
    auto start_cpu = get_cpu_time();
    std::vector<std::thread> threads(nb_threads);
    std::vector<int> results(nb_threads);
    int numThread = 0;
    for (auto& t : threads) {
        t = std::thread(computeDecrement, nb_count / nb_threads, std::ref(results[numThread]));
        ++numThread;
    }
    numThread = 0;
    for (auto &t:threads) {
        t.join();
        counter -= results[numThread];
        ++numThread;
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
