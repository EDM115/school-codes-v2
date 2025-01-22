#ifndef BIBLIOSTD_H
#define BIBLIOSTD_H

#include <iostream>
#include <sstream>
#include <fstream>
#include <string>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <map>
#include <algorithm>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <cstring>
#include <arpa/inet.h>
#include <errno.h>
#include <fcntl.h>
#include <stdexcept>

/*** CONSTANTES ***/
// Taille de la grille
#define TAILLE_GRILLE 11
// Paramètres réseau
#define MAXHOSTNAME 200
#define MAXCONNECTIONS 5
#define MAXRECV 500
#define PORTCOM 8000
#define MAXESSAIS 1000

using namespace std;

#endif
