#include <iostream>
#include <string>
int myMain()
{
    char* t = static_cast<char*>(malloc(10));
    char c = t[0];
    // Gardez les lignes 29, 30 et 31 en commentaire pour observer les fuites m�moire.
    //  NB : Dr Memory ne d�tecte la fuite li�e � la string C++ que si vous avez g�n�r� votre
    //       code avec Propri�t� de configuration C/C++ > G�n�ration de code > Biblioth�que runtime 
    //       � /MTd (au lieu de /MDd par d�faut)
    // En les d�commentant, vous pourrez exp�rimenter que Visual Studio fait une analyse de
    // vos acc�s m�moire et qu'il signale une erreur si vous mettez l'option /MTd � la place de /MDd
    // (cf. ci-dessous) :
    // - G�n�rez le code (Propri�t� de configuration C/C++ > G�n�ration de code > 
    //   Biblioth�que runtime est � /MDd par d�faut).
    // - Ex�cutez-le en mode debug : Windows fait un bip, mais il ne se passe 
    //   rien au niveau de la fen�tre qui s'est ouverte.
    // - Arr�tez le d�bogage.
    // - Propri�t� de configuration C/C++ > G�n�ration de code > 
    //   Biblioth�que runtime et mettre /MTd au lieu de /MDd
    // - Ex�cutez votre programme en mode debug : cette fois, vous avez une fen�tre 
    //   "HEAP CORRUPTION detected" qui s'affiche. En cliquant sur "Recommencer", vous 
    //   arrivez � l'endroit de l'erreur.
    //   Vidual Studio vous affiche 2 warnings par rapport aux acc�s glauques qui sont faits sur
    //   le tableau t.
    //   Ces warnings sont obtenus gr�ce aux options s�lectionn�es dans Propri�t� de 
    //   configuration C/C++ > G�n�ration de code > V�rifications de base � l'ex�cution 
    //    qui est � / RTCsu
    //t[10] = 'a';
    //free(t);
    //t[5] = 'b';

    std::string* s = new std::string();
    std::cout << "Hello World!\n";
    return 0;
}
int main()
{
    return myMain();
}