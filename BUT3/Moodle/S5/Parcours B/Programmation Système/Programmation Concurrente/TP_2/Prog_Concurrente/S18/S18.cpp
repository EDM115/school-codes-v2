#include <iostream>
#include <vector>
#include <thread>
#include <semaphore>
#include <syncstream>

using namespace std::literals; //To be able to use 1s instead of std::chrono::seconds(1)

constexpr int max_cars_in_parking = 4;
constexpr int nb_threads = 10;

void car_life(const int num_thread) {
    static std::counting_semaphore<max_cars_in_parking> parking(max_cars_in_parking);
    std::osyncstream(std::cout) << "Thread " << num_thread << " wants to enter parking" << std::endl;
    parking.acquire();
    std::osyncstream(std::cout) << "Thread " << num_thread << " enters parking" << std::endl;
    std::this_thread::sleep_for(0.2s); // Note: We do not use sleep, otherwise the whole process would stop.
    std::osyncstream(std::cout) << "Thread " << num_thread << " exits parking" << std::endl;
    parking.release();
    std::osyncstream(std::cout) << "Thread " << num_thread << " exited parking" << std::endl;
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
