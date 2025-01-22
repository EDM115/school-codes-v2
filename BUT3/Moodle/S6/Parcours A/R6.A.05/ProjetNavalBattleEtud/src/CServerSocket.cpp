#include "CServerSocket.h"

CServerSocket::CServerSocket ( int port ) {

	if ( !methCreate() ) {
		cout << "Erreur : impossible de creer la socket Serveur" << endl;
  	}

  	if ( !methBind (port) ) {
		cout << "Erreur : pas de connexion au port" << endl;
  	}

  	if ( !methListen () ) {
		cout << "Erreur : pas d'ecoute sur la socket" << endl;
	}
}

/**********************************************/

const CServerSocket& CServerSocket::operator << ( const string& s ) const {

  	if ( !methSend (s) ) {
	      cout << "Impossible d'ecrire sur la socket..." << endl;
  	}

  	return *this;
}

/**********************************************/

const CServerSocket& CServerSocket::operator >> ( string& s ) const {

	if ( !methRecv (s) ) {
      		cout << "Impossible de lire sur la socket..." << endl;
	}

  	return *this;
}

/**********************************************/

void CServerSocket::accept ( CClientSocket& sock ) {

	if ( !methAccept (sock) ) {
		cout << "Refus d'accepter la socket client..." << endl;
	}
}

