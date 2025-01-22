/*
 * 	Classe permettant la gestion d'une communication réseau du côté client.
 * 	Elle dérive de la classe CSocket.
 */

#ifndef CCLIENTSOCKET_H
#define CCLIENTSOCKET_H

#include "CSocket.h"
#include "BiblioStd.h"

class CClientSocket : public CSocket {

	public:

		CClientSocket();
		CClientSocket ( string host, int port );
		const CClientSocket& operator << ( const string& s ) const;	// envoyer sur le réseau
		const CClientSocket& operator >> ( string& s ) const;		// récupérer du réseau
		string uneLigne ();											// lire une ligne de données
};

#endif // CCLIENTSOCKET_H
