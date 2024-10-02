#include <iostream>
#include <vector>
#include <thread>
#include <latch>
#include <syncstream>

using namespace std::literals; //To be able to use 1s instead of std::chrono::seconds(1)

constexpr int nb_runners = 8; //How many runners can run in parallel in a stadium

void runner(const int num_runner, std::latch& fire_signal) {
    std::osyncstream(std::cout) << "Runner " << num_runner << " waiting for run signal" << std::endl;
    fire_signal.wait();
    std::osyncstream(std::cout) << "Runner " << num_runner << " starts running" << std::endl;
}

void referee(std::latch& fire_signal) {
    std::osyncstream(std::cout) << "Referee is about to fire run signal" << std::endl;
    std::osyncstream(std::cout) << "Referee has fired run signal" << std::endl;
    fire_signal.count_down();
}

int main()
{
    std::latch fire_signal(1);

    std::vector<std::thread> threads(nb_runners);
    int num_runner = 0;
    for (auto& t : threads) {
        t = std::thread(runner, num_runner, std::ref(fire_signal));
        ++num_runner;
    }

    std::this_thread::sleep_for(0.2s); // Note: We do not use sleep, otherwise the whole process would stop.
    std::thread thReferee(referee, std::ref(fire_signal));

    thReferee.join();
    for (auto &t:threads) {
        t.join();
    }

    return 0;
}
