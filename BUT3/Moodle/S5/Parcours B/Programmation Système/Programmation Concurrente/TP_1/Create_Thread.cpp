// Exemple de création de threads en C++
#include <iostream>
#include <thread>
using namespace std;

// Fonction
void foo(int Z)
{
    for (int i = 0; i < Z; i++) {
        cout << "Thread utilisant un pointeur sur fonction callable\n"
    }
}

// A callable object
class thread_obj {
public:
    void operator()(int x)
    {
        for (int i = 0; i < x; i++)
            cout << "Thread utilisant un Objet Fonction callable\n"
    }
};

// Définition d'une Classe
class Base {
public:
    // Fonction membre non static
    void foo()
    {
        cout << "Thread utilisant une fonction membre non static callable\n"
             << endl;
    }
    // fonction membre static
    static void foo1()
    {
        cout << "Thread utilisant une fonction membre static callable\n"
             << endl;
    }
};

// Main Code
int main()
{
    cout << "Threads 1 et 2 et 3 "
            "Independants"
         << endl;

    // Creation par un pointeur sur une Fonction callable
    thread th1(foo, 3);

	// Creation par un Object Fonction callable
    thread th2(thread_obj(), 3);

    // Expression Lambda 
    auto f = [](int x) {
        for (int i = 0; i < x; i++)
            cout << "Thread using lambda"
                    " expression as callable\n";
    };

	// Creation par une Expression Lambda
    thread th3(f, 3);

    // Objet de Classe <Base>
    Base b;
  
    thread th4(&Base::foo, &b);

    thread th5(&Base::foo1);

    // Attente de la fin des threads
    // Attente de la fin de th1
    th1.join();

	// Attente de la fin de th2
    th2.join();

	// Attente de la fin de th3
    th3.join();

	// Attente de la fin de th4
    th4.join();

	// Attente de la fin de th5
    th5.join();

    return 0;
}
