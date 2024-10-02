#include <iostream>
#include <vector>
#include <thread>
#include <mutex>
#include <semaphore> // Since CPP 20
#include <syncstream>

using namespace std::literals; //To be able to use 1s instead of std::chrono::seconds(1)

constexpr int nb_readers = 8;
constexpr int nb_read_per_reader = 4;
constexpr int nb_writers = 2;
constexpr int nb_write_per_writer = 4;

void reader(const int num, std::mutex& mtx_glob, std::counting_semaphore<1>& sem_wrt) {
    static std::mutex mtx_reader;
    static int nb_reader = 0;
    std::osyncstream(std::cout) << "Reader " << num << " starting" << std::endl;
    for (int num_read = 0; num_read < nb_read_per_reader; ++num_read) {
        std::osyncstream(std::cout) << "Reader " << num << " wants to read" << std::endl;
        {
            std::scoped_lock lock_glob(mtx_glob); // Since CPP 17
            ++nb_reader;
            if (nb_reader == 1) {
                sem_wrt.acquire();
            }
        }

        std::osyncstream(std::cout) << "Reader " << num << " starts reading" << std::endl;
        std::this_thread::sleep_for(0.2s);
        std::osyncstream(std::cout) << "Reader " << num << " stops reading" << std::endl;

        {
            std::scoped_lock lock_reader(mtx_reader);
            --nb_reader;
            if (nb_reader == 0) {
                sem_wrt.release();
            }
        }
    }
    std::osyncstream(std::cout) << "Reader " << num << " is done" << std::endl;
}

void writer(const int num, std::mutex& mtx_glob, std::counting_semaphore<1>& sem_wrt) {
    std::osyncstream(std::cout) << "Writer " << num << " starting" << std::endl;
    for (int num_write = 0; num_write <= nb_write_per_writer; ++num_write) {
        std::osyncstream(std::cout) << "Writer " << num << " wants to write" << std::endl;
        std::scoped_lock lock_glob(mtx_glob);
        sem_wrt.acquire();

        std::osyncstream(std::cout) << "Writer " << num << " starts writing" << std::endl;
        std::this_thread::sleep_for(0.1s);
        std::osyncstream(std::cout) << "Writer " << num << " stops writing" << std::endl;

        sem_wrt.release();
    }
    std::osyncstream(std::cout) << "Writer " << num << " is done" << std::endl;
}

int main()
{
    // Variables for reader-write
    std::mutex mtx_glob;
    std::counting_semaphore<1> sem_wrt(1);

    std::vector<std::jthread> threads_reader(nb_readers);
    int num = 0;
    for (auto& t : threads_reader) {
        t = std::jthread(reader, num, std::ref(mtx_glob), std::ref(sem_wrt));
        ++num;
    }
    std::vector<std::jthread> threads_writer(nb_writers);
    num = 0;
    for (auto& t : threads_writer) {
        t = std::jthread(writer, num, std::ref(mtx_glob), std::ref(sem_wrt));
        ++num;
    }

    for (auto& t : threads_reader) {
        t.join();
    }
    for (auto& t : threads_writer) {
        t.join();
    }

    return 0;
}
