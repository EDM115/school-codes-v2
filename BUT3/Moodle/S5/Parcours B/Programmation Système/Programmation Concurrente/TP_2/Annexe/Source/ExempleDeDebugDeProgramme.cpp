//
// Programme pour illustrer l'activit� de debogage
//

#include <vector>
#include <iostream>

const int QUARANTE_ET_UN = 42;

void uneGrosseProcedure()
{
}

int unCalcul()
{
    int resultat = QUARANTE_ET_UN - 42;
    return resultat;
}

int main()
{
    std::vector<int> v = { 1, 2, 3, 3 };
    v[v.size()] = 4;

    int a = 5;
    int b = 5;
    int& c = a;
    uneGrosseProcedure();
    a = unCalcul();
    std::cout << "b % c = " << b % c;
    return 0;
}