// Adapted from code example
// in "Concurrency witrh Modern C++, What every professional C++ programmer should know about concurrency."
//    by Rainer Grimm, Juillet 2021, https://leanpub.com/concurrencywithmodernc#include <vector>
#include <iostream>
#include <vector>
#include <algorithm>
#include <execution>

constexpr int nb_int = 1000000;

int main()
{
	std::vector<int> v(nb_int);
	std::iota(v.begin(), v.end(), 1);
	unsigned long long int sum = 0;
	std::for_each(std::execution::par, v.begin(), v.end(), [&sum](int i) {
		sum += i;
		});
	std::cout << "sum = " << sum << std::endl;
}