/*
 * 	Classe permettant la gestion d'une communication r�seau du c�t� client.
 * 	Elle d�rive de la classe CSocket.
 */

#ifndef CCLIENTSOCKET_H
#define CCLIENTSOCKET_H

#include "CSocket.h"
#include "BiblioStd.h"

class CClientSocket : public CSocket {

	public:

		CClientSocket();
		CClientSocket ( string host, int port );
		const CClientSocket& operator << ( const string& s ) const;	// envoyer sur le r�seau
		const CClientSocket& operator >> ( string& s ) const;		// r�cup�rer du r�seau
		string uneLigne ();											// lire une ligne de donn�es
};

#endif // CCLIENTSOCKET_H
