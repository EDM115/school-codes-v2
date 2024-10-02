#include <iostream>
#include <vector>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <syncstream>

using namespace std::literals; //To be able to use 1s instead of std::chrono::seconds(1)

constexpr int minibus_capacity = 8; //passengers (in addition to the driver)

struct minibus_t {
    std::mutex mtx;
    std::condition_variable cond_var;
    int nb_passengers{ 0 }; // Already boarded in minibus
};

void passenger(const int num_passenger, minibus_t& minibus) {
    std::osyncstream(std::cout) << "Passenger " << num_passenger << " starting to board" << std::endl;
    std::this_thread::sleep_for(1s); // Note: We do not use sleep, otherwise the whole process would stop.
    std::osyncstream(std::cout) << "Passenger " << num_passenger << " is done boarding" << std::endl;
    {
        std::lock_guard lck(minibus.mtx);
        ++minibus.nb_passengers;
    }
    minibus.cond_var.notify_one();
}

void driver(minibus_t& minibus) {
    std::osyncstream(std::cout) << "Driver waiting for " << minibus_capacity << " passengers to get onboard" << std::endl;
    {
        std::unique_lock lck(minibus.mtx);
        minibus.cond_var.wait(lck, [&minibus] { return minibus.nb_passengers == minibus_capacity; });
    }
    std::osyncstream(std::cout) << "Driver sees that " << minibus_capacity << " passengers are onboard: He can start the trip!" << std::endl;
}

int main()
{
    minibus_t minibus;

    std::thread thDriver(driver, std::ref(minibus));

    std::vector<std::thread> threads(minibus_capacity);
    int num_passenger = 0;
    for (auto& t : threads) {
        t = std::thread(passenger, num_passenger, std::ref(minibus));
        ++num_passenger;
    }

    thDriver.join();
    for (auto &t:threads) {
        t.join();
    }

    return 0;
}
