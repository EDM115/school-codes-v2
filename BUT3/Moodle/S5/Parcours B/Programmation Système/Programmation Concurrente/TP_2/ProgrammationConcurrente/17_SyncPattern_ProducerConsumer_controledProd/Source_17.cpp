#include <iostream>
#include <vector>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <latch>
#include <syncstream>

using namespace std::literals; //To be able to use 1s instead of std::chrono::seconds(1)

constexpr int nb_producers = 8;
constexpr int nb_prod_per_producer = 4;
constexpr int nb_consumers = 8;
constexpr int buffer_size = 5;

struct prod_cons_struct {
    std::vector<int> buffer;
    int nb_places_dispo{ buffer_size };
    int nb_data_dispo{ 0 };
    std::mutex mtx_places_dispo;
    std::condition_variable cond_places_dispo;
    std::mutex mtx_data_dispo;
    std::condition_variable cond_data_dispo;
    // The following field could be a static variable in producer() bacause it is not shared with consumer().
    // We prefer to declare it in this struct to centralize everything.
    int slot_dispo{ 0 };
    // The following field could be a static variable in consumer() bacause it is not shared with producer().
    // We prefer to declare it in this struct to centralize everything.
    int slot_data{ 0 };
};

void producer(const int num, prod_cons_struct& pc_data) {
    std::osyncstream(std::cout) << "Producer " << num << " starting" << std::endl;
    for (int num_prod = 1; num_prod <= nb_prod_per_producer; ++num_prod) {
        int data_produced = num_prod * 10 + num;
        std::osyncstream(std::cout) << "Producer " << num << " starts producing data " << data_produced << std::endl;
        // If we want, we can insert here a sleep_for to simulate the duration of the production.
        std::osyncstream(std::cout) << "Producer " << num << " ends producing data " << data_produced << std::endl;
        std::osyncstream(std::cout) << "Producer " << num << " wants to publish data " << data_produced << std::endl;
        {
            std::unique_lock lck(pc_data.mtx_places_dispo);
            pc_data.cond_places_dispo.wait(lck, [&pc_data] { return pc_data.nb_places_dispo > 0; });
            --pc_data.nb_places_dispo;
            pc_data.buffer[pc_data.slot_dispo] = data_produced;
            std::osyncstream(std::cout) << "Producer " << num << " has published data " << data_produced << std::endl;
            pc_data.slot_dispo = (pc_data.slot_dispo + 1) % buffer_size;
        }


        {
            std::lock_guard lck(pc_data.mtx_data_dispo);
            ++pc_data.nb_data_dispo;
        }
        pc_data.cond_data_dispo.notify_one();
    }
    std::osyncstream(std::cout) << "Producer " << num << " is done producing" << std::endl;
}

void consumer(const int num, prod_cons_struct& pc_data, std::latch& the_end_signal) {
    std::osyncstream(std::cout) << "Consumer " << num << " starting" << std::endl;
    while (true) {
        int data;
        {
            std::unique_lock lck(pc_data.mtx_data_dispo);
            pc_data.cond_data_dispo.wait(lck, [&pc_data] { return pc_data.nb_data_dispo > 0; });
            --pc_data.nb_data_dispo;
            data = pc_data.buffer[pc_data.slot_data];
            std::osyncstream(std::cout) << "Consumer " << num << " has read data " << data << std::endl;
            pc_data.slot_data = (pc_data.slot_data + 1) % buffer_size;
        }

        {
            std::lock_guard lck(pc_data.mtx_places_dispo);
            ++pc_data.nb_places_dispo;
        }
        pc_data.cond_places_dispo.notify_one();

        std::osyncstream(std::cout) << "Consumer " << num << " is beginning processing data " << data << std::endl;
        std::this_thread::sleep_for(0.1s);
        std::osyncstream(std::cout) << "Consumer " << num << " has finished processing data " << data << std::endl;

        the_end_signal.count_down();
    }
}

int main()
{
    // Variable for producer-consumer.
    prod_cons_struct pc_data;
    for (int i = 0; i < buffer_size; ++i) {
        pc_data.buffer.push_back(0);
    }

    // This latch is used for exiting the program
    std::latch the_end_signal(nb_producers * nb_prod_per_producer);

    std::vector<std::jthread> threads_prod(nb_producers);
    int num = 0;
    for (auto& t : threads_prod) {
        t = std::jthread(producer, num, std::ref(pc_data));
        ++num;
    }

    std::vector<std::jthread> threads_cons(nb_consumers);
    num = 0;
    for (auto& t : threads_prod) {
        t = std::jthread(consumer, num, std::ref(pc_data), std::ref(the_end_signal));
        ++num;
    }

    the_end_signal.wait();

    exit(0); // To force the death of all detached threads()

    return 0;
}
