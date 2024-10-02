#include <iostream>
#include <vector>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <syncstream>

using namespace std::literals; //To be able to use 1s instead of std::chrono::seconds(1)

constexpr int nb_runners = 8; //How many runners can run in parallel in a stadium

struct stadium_t {
    std::mutex mtx;
    std::condition_variable cond_var;
    bool run_signal{ false };
};

void runner(const int num_runner, stadium_t& stadium) {
    std::osyncstream(std::cout) << "Runner " << num_runner << " waiting for run signal" << std::endl;
    {
        std::unique_lock lck(stadium.mtx);
        stadium.cond_var.wait(lck, [&stadium] { return stadium.run_signal; });
    }
    std::osyncstream(std::cout) << "Runner " << num_runner << " starts running" << std::endl;
}

void referee(stadium_t& stadium) {
    std::osyncstream(std::cout) << "Referee is about to fire run signal" << std::endl;
    {
        std::lock_guard lck(stadium.mtx);
        stadium.run_signal = true;
    }
    std::osyncstream(std::cout) << "Referee has fired run signal" << std::endl;
    stadium.cond_var.notify_all();
}

int main()
{
    stadium_t stadium;

    std::vector<std::thread> threads(nb_runners);
    int num_runner = 0;
    for (auto& t : threads) {
        t = std::thread(runner, num_runner, std::ref(stadium));
        ++num_runner;
    }

    std::this_thread::sleep_for(0.2s); // Note: We do not use sleep, otherwise the whole process would stop.
    std::thread thReferee(referee, std::ref(stadium));

    thReferee.join();
    for (auto &t:threads) {
        t.join();
    }

    return 0;
}
