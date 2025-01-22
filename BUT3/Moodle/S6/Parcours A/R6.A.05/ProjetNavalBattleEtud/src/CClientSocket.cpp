#include "CClientSocket.h"

CClientSocket::CClientSocket () {
	// On laisse les attributs indéterminés (voir CSocket)
}

/**********************************************/

CClientSocket::CClientSocket ( string host, int port ) {

	if ( !methCreate() ) {
        cout << "Erreur : impossible de creer la socket Client" << host << endl;
    }

  	if ( !methConnect(host, port) ) {
        cout << "Erreur : pas de connexion au port" << host << endl;
    }
}

/**********************************************/

const CClientSocket& CClientSocket::operator << (const string& s) const {

	if ( !methSend (s) ) {
	  	cout << "Impossible d'ecrire sur la socket..." << endl;
	}

  	return *this;
}

/**********************************************/

const CClientSocket& CClientSocket::operator >> ( string& s ) const {

	if ( !methRecv (s) ) {
      		cout << "Impossible de lire sur la socket..." << endl;
    }

  	return *this;
}

/**********************************************/

string CClientSocket::uneLigne () {
	// méthode héritée
	return ( recvLine () );
}

