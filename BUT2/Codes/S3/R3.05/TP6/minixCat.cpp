#include <iostream>
#include <fstream>
#include <string>

int main(int argc, char* argv[]) {
    // Vérifiez si au moins un nom de fichier est passé en argument
    if (argc < 2) {
        std::cerr << "Utilisation : " << argv[0] << " fichier1 [fichier2 ...]" << std::endl;
        return 1;
    }

    for (int i = 1; i < argc; ++i) {
        const char* nomFichier = argv[i];

        // Ouvrez le fichier en lecture
        std::ifstream fichier(nomFichier);

        // Vérifiez si le fichier peut être ouvert
        if (!fichier.is_open()) {
            std::cerr << "Impossible d'ouvrir le fichier " << nomFichier << std::endl;
            continue; // Passe au fichier suivant en cas d'erreur
        }

        // Lisez et affichez le contenu du fichier
        std::string ligne;
        while (std::getline(fichier, ligne)) {
            std::cout << ligne << std::endl;
        }

        // Fermez le fichier
        fichier.close();
    }

    return 0;
}
