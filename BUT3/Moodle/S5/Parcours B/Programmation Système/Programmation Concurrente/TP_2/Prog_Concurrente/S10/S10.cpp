#include <iostream>
#include <vector>
#include <thread>
#include <mutex>
#include <condition_variable>

using namespace std::literals; //To be able to use 1s instead of std::chrono::seconds(1)

constexpr int max_cars_in_parking = 4;
constexpr int nb_threads = 10;

void car_life(const int num_thread) {
    static std::mutex mtx;
    static std::condition_variable cond_var;
    static int nb_cars_in_parking = 0;

    {
        std::unique_lock lck(mtx);
        std::cout << "Thread " << num_thread << " wants to enter parking" << std::endl;
        cond_var.wait(lck, [] { return nb_cars_in_parking < max_cars_in_parking; });
        ++nb_cars_in_parking;
    }
    std::cout << "Thread " << num_thread << " enters parking" << std::endl;
    std::this_thread::sleep_for(0.2s); // Note: We do not use sleep, otherwise the whole process would stop.
    std::cout << "Thread " << num_thread << " exits parking" << std::endl;
    {
        std::lock_guard lck(mtx);
        --nb_cars_in_parking;
    }
    cond_var.notify_one();
    std::cout << "Thread " << num_thread << " exited parking" << std::endl;
}

int main()
{
    std::vector<std::thread> threads(nb_threads);
    int num_thread = 0;
    for (auto& t : threads) {
        t = std::thread(car_life, num_thread);
        ++num_thread;
    }
    for (auto& t : threads) {
        t.join();
    }

    return 0;
}
