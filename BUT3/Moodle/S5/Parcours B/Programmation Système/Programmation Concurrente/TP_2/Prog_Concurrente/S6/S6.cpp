#include <iostream>
#include <vector>
#include <future>
#include "../get_cpu_time/get_cpu_time.h"
#include <chrono>
using namespace std::chrono;

constexpr int nb_count = 100000000;
constexpr int nb_tasks = 4;

int taskDecrement(const int nb_decrement) {
    int totalDecrement = 0;
    for (auto i = nb_decrement; i > 0; --i) {
        ++totalDecrement;
    }
    return totalDecrement;
}

int main()
{
    auto counter = nb_count;

    auto start = high_resolution_clock::now();
    auto start_cpu = get_cpu_time();

    /*  auto fut0 = std::async([] { return taskDecrement(nb_count / nb_tasks); });
        auto fut1 = std::async([] { return taskDecrement(nb_count / nb_tasks); });
        auto fut2 = std::async([] { return taskDecrement(nb_count / nb_tasks); });
        auto fut3 = std::async([] { return taskDecrement(nb_count / nb_tasks); });
        counter -= fut0.get();
        counter -= fut1.get();
        counter -= fut2.get();
        counter -= fut3.get();
    */
    std::vector<std::future<int>> futures(nb_tasks);
    for (auto& fut : futures) {
        fut = std::async([] { return taskDecrement(nb_count / nb_tasks); });
    }
    for (auto& fut : futures) {
        counter -= fut.get();
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
