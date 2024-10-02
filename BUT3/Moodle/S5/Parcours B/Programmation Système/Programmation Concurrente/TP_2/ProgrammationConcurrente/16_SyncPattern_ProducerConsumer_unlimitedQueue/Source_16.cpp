// Adapted from concurrentQueueFineLockingWithWaiting.cpp
// in "Concurrency witrh Modern C++, What every professional C++ programmer should know about concurrency."
//    by Rainer Grimm, Juillet 2021, https://leanpub.com/concurrencywithmodernc
#include <iostream>
#include <vector>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <memory>
#include <latch>
#include <syncstream>

using namespace std::literals; //To be able to use 1s instead of std::chrono::seconds(1)

constexpr int nb_producers = 8;
constexpr int nb_prod_per_producer = 4;
constexpr int nb_consumers = 8;

template <typename T>
class Queue {
private:
    struct Node {
        T data;
        std::unique_ptr<Node> next;
        Node(T data_) : data(std::move(data_)) {}
    };
    std::unique_ptr<Node> head;
    Node* tail;
    std::mutex headMutex;
    std::mutex tailMutex;
    std::condition_variable condVar;
public:
    Queue() : head(new Node(T{})), tail(head.get()) {};
    std::unique_ptr<T> pop() {
        std::lock_guard<std::mutex> headLock(headMutex);
        {
            std::unique_lock<std::mutex> tailLock(tailMutex);
            if (head.get() == tail) condVar.wait(tailLock);
        }
        std::unique_ptr<T> res = std::make_unique<T>(std::move(head->data));
        std::unique_ptr<Node> oldHead = std::move(head);
        head = std::move(oldHead->next);
        if (!head) tail = nullptr;
        return res;
    }
    void push(T val) {
        std::unique_ptr<Node> dummyNode = std::make_unique<Node>(Node(T{}));
        Node* newTail = dummyNode.get();
        {
            std::unique_lock<std::mutex> tailLock(tailMutex);
            tail->next = std::move(dummyNode);
            tail->data = val;
            tail = newTail;
        }
        condVar.notify_one();
    }
    Queue(const Queue& other) = delete;
    Queue& operator=(const Queue& other) = delete;
};


void producer(const int num, Queue<int>& prod_cons_queue) {
    std::osyncstream(std::cout) << "Producer " << num << " starting" << std::endl;
    for (int num_prod = 1; num_prod <= nb_prod_per_producer; ++num_prod) {
        int data_produced = num_prod * 10 + num;
        std::osyncstream(std::cout) << "Producer " << num << " starts producing data " << data_produced << std::endl;
        // If we want, we can insert here a sleep_for to simulate the duration of the production.
        std::osyncstream(std::cout) << "Producer " << num << " ends producing data " << data_produced << std::endl;
        std::osyncstream(std::cout) << "Producer " << num << " wants to publish data " << data_produced << std::endl;
        prod_cons_queue.push(data_produced);
        std::osyncstream(std::cout) << "Producer " << num << " has published data " << data_produced << std::endl;
    }
    std::osyncstream(std::cout) << "Producer " << num << " is done producing" << std::endl;
}

void consumer(const int num, Queue<int>& prod_cons_queue, std::latch& the_end_signal) {
    std::osyncstream(std::cout) << "Consumer " << num << " starting" << std::endl;
    while (true) {
        int data = *prod_cons_queue.pop();
        std::osyncstream(std::cout) << "Consumer " << num << " has read data " << data << std::endl;

        std::osyncstream(std::cout) << "Consumer " << num << " is beginning processing data " << data << std::endl;
        std::this_thread::sleep_for(0.1s);
        std::osyncstream(std::cout) << "Consumer " << num << " has finished processing data " << data << std::endl;

        the_end_signal.count_down();
    }
}

int main()
{
    // Variable for producer-consumer.
    Queue<int> prod_cons_queue;

    // This latch is used for exiting the program
    std::latch the_end_signal(nb_producers * nb_prod_per_producer);

    std::vector<std::jthread> threads_prod(nb_producers);
    int num = 0;
    for (auto& t : threads_prod) {
        t = std::jthread(producer, num, std::ref(prod_cons_queue));
        ++num;
    }

    std::vector<std::jthread> threads_cons(nb_consumers);
    num = 0;
    for (auto& t : threads_prod) {
        t = std::jthread(consumer, num, std::ref(prod_cons_queue), std::ref(the_end_signal));
        ++num;
    }

    the_end_signal.wait();

    exit(0); // To force the death of all detached threads()

    return 0;
}
