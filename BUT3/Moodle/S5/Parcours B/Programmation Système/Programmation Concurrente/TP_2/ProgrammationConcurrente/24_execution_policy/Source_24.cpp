// Adapted from code example
// in "Concurrency witrh Modern C++, What every professional C++ programmer should know about concurrency."
//    by Rainer Grimm, Juillet 2021, https://leanpub.com/concurrencywithmodernc#include <vector>
#include <iostream>
#include <vector>
#include <algorithm>
#include <execution>

int main()
{
    std::vector<int> v = {1, 2, 3, 4, 5, 6, 7, 8, 9};

 	// standard sequential sort
 	std::sort(v.begin(), v.end());

	 // sequential execution
 	std::sort(std::execution::seq, v.begin(), v.end());

 	// permitting parallel execution
 	std::sort(std::execution::par, v.begin(), v.end());

 	// permitting parallel and vectorised execution
 	std::sort(std::execution::par_unseq, v.begin(), v.end());
}