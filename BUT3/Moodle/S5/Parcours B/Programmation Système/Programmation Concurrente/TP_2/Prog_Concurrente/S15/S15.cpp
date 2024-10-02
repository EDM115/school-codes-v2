// Adapted from fullTimePartTimeWorkers.cpp
// in "Concurrency witrh Modern C++, What every professional C++ programmer should know about concurrency."
//    by Rainer Grimm, Juillet 2021, https://leanpub.com/concurrencywithmodernc

#include <iostream>
#include <barrier>
#include <mutex>
#include <string>
#include <thread>

// This example (adapted from the book here above) uses a non-const global variable,
// which must be avoided.
std::barrier workDone(6);

void synchronizedOut(const std::string& s) noexcept {
    static std::mutex coutMutex;
    std::lock_guard lock(coutMutex);
    std::cout << s;
}

class FullTimeWorker {
public:
    explicit FullTimeWorker(std::string const& n) : name(n) { };

    void operator() () const {
        synchronizedOut(name + ": " + "Morning work done!\n");
        workDone.arrive_and_wait();  // Wait until morning work is done
        synchronizedOut(name + ": " + "Afternoon work done!\n");
        workDone.arrive_and_wait();  // Wait until afternoon work is done

    }
private:
    std::string name;
};

class PartTimeWorker {
public:
    explicit PartTimeWorker(std::string const& n) : name(n) { };

    void operator() () const {
        synchronizedOut(name + ": " + "Morning work done!\n");
        workDone.arrive_and_drop();  // Wait until morning work is done
    }
private:
    std::string name;
};

int main() {

    std::cout << '\n';

    FullTimeWorker herb("  Herb");
    std::thread herbWork(herb);

    FullTimeWorker scott("    Scott");
    std::thread scottWork(scott);

    FullTimeWorker bjarne("      Bjarne");
    std::thread bjarneWork(bjarne);

    PartTimeWorker andrei("        Andrei");
    std::thread andreiWork(andrei);

    PartTimeWorker andrew("          Andrew");
    std::thread andrewWork(andrew);

    PartTimeWorker david("            David");
    std::thread davidWork(david);

    herbWork.join();
    scottWork.join();
    bjarneWork.join();
    andreiWork.join();
    andrewWork.join();
    davidWork.join();

}