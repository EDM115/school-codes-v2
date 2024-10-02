#include <iostream>
#include <mutex>
#include <thread>
#include <vector>

constexpr int nb_threads = 4;
constexpr int nb_times = 3;

void display(std::atomic_int const& v_main, int v_work, std::atomic_int const& v_incr_static, int v_incr_thread_local, int v_incr) {
    static std::mutex for_cout;
    std::scoped_lock lock(for_cout);
    std::cout << "Thread ID:" << std::this_thread::get_id()
        << ", v_main = " << v_main
        << ", v_work = " << v_work
        << ", v_incr_static = " << v_incr_static
        << ", v_incr_thread_local = " << v_incr_thread_local
        << ", v_incr = " << v_incr
        << std::endl;
}

void incr(std::atomic_int& v_main, int& v_work) {
    static std::atomic_int v_incr_static = 0;
    thread_local int v_incr_thread_local = 0;
    int v_incr = 0;
    ++v_main;
    ++v_work;
    ++v_incr_static;
    ++v_incr_thread_local;
    ++v_incr;
    display(v_main, v_work, v_incr_static, v_incr_thread_local, v_incr);
}

void work(std::atomic_int& v_main) {
    int v_work = 0;
    for (auto i = nb_times; i > 0; --i) {
        incr(v_main, v_work);
    }
}

int main()
{
    std::atomic_int v_main = 0;

    std::vector<std::jthread> threads(nb_threads);
    for (auto& t : threads) {
        t = std::jthread(work, std::ref(v_main));
    }
    for (auto& t : threads) {
        t.join();
    }

    std::cout << "When all threads are done, v_main = " << v_main << std::endl;
    return 0;
}
