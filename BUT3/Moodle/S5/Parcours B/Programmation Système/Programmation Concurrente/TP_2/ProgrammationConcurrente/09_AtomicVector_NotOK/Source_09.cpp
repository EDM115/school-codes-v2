#include <iostream>
#include <vector>
#include <thread>
#include <atomic>
#include "get_cpu_time.h"
#include <chrono>
using namespace std::chrono;

constexpr int nb_push_back = 1000000;
constexpr int nb_threads = 4;

void pusher(const int num_thread, const int my_nb_push_back, std::vector<int>& v) {
    for (auto i = 0; i < my_nb_push_back; ++i) {
        v.push_back((num_thread * my_nb_push_back) + i);
    }
}

int main()
{
    // NB : Does not compile with std::atomic<std::vector<int>> 
    std::vector<int> v;

    auto start = high_resolution_clock::now();
    auto start_cpu = get_cpu_time();
    std::vector<std::thread> threads(nb_threads);
    int num_thread = 0;
    for (auto& t : threads) {
        t = std::thread(pusher, num_thread, nb_push_back / nb_threads, std::ref(v));
    }
    for (auto& t:threads) {
        t.join();
    }
    auto end_cpu = get_cpu_time();
    auto stop = high_resolution_clock::now();

    // Predefined units are nanoseconds, microseconds, milliseconds, seconds, minutes, hours.
    // See https://www.geeksforgeeks.org/measure-execution-time-function-cpp/
    auto duration = duration_cast<microseconds>(stop - start);

    std::cout << "Waiting for " << nb_push_back << "elements in vector and found " << v.size() << " elements";
    std::cout << " (elapsed time = " << duration.count() << " microseconds";
    std::cout << " ; CPU time = " << (end_cpu - start_cpu) << " microseconds)" << std::endl;
    return 0;
}
