#include <iostream>
#include <vector>
#include <thread>
#include <latch>
#include <syncstream>

using namespace std::literals; //To be able to use 1s instead of std::chrono::seconds(1)

constexpr int minibus_capacity = 8; //passengers (in addition to the driver)

void passenger(const int num_passenger, std::latch& onboarding_remaining) {
    std::osyncstream(std::cout) << "Passenger " << num_passenger << " starting to board" << std::endl;
    std::this_thread::sleep_for(1s);
    std::osyncstream(std::cout) << "Passenger " << num_passenger << " is done boarding" << std::endl;
    onboarding_remaining.count_down();
}

void driver(std::latch& onboarding_remaining) {
    std::osyncstream(std::cout) << "Driver waiting for " << minibus_capacity << " passengers to get onboard" << std::endl;
    onboarding_remaining.wait();
    std::osyncstream(std::cout) << "Driver sees that " << minibus_capacity << " passengers are onboard: He can start the trip!" << std::endl;
}

int main()
{
    std::latch onboarding_remaining(minibus_capacity);

    std::thread thDriver(driver, std::ref(onboarding_remaining));

    std::vector<std::thread> threads(minibus_capacity);
    int num_passenger = 0;
    for (auto& t : threads) {
        t = std::thread(passenger, num_passenger, std::ref(onboarding_remaining));
        ++num_passenger;
    }

    thDriver.join();
    for (auto& t : threads) {
        t.join();
    }

    return 0;
}
