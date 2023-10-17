#include <iostream>
#include <dirent.h>
#include <cstring>

int main(int argc, char* argv[]) {
    const char* path; // Le chemin du répertoire à lister

    // Vérifiez si un chemin de répertoire est passé en argument
    if (argc == 2) {
        path = argv[1];
    } else {
        // Si aucun chemin n'est spécifié, utilisez le répertoire actuel
        path = ".";
    }

    DIR* directory = opendir(path);

    // Vérifiez si le répertoire peut être ouvert
    if (directory == nullptr) {
        std::cerr << "Impossible d'ouvrir le répertoire " << path << std::endl;
        return 1;
    }

    // Parcourez le répertoire et affichez les noms des fichiers et répertoires
    struct dirent* entry;
    while ((entry = readdir(directory))) {
        // Ignorer les entrées "." et ".."
        if (strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) {
            std::cout << entry->d_name << std::endl;
        }
    }

    // Fermez le répertoire
    closedir(directory);

    return 0;
}
