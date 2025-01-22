/*
 * 	Classe permettant la gestion d'une communication réseau du côté serveur.
 * 	Elle dérive de la classe CSocket.
 */

#ifndef CSERVERSOCKET_H
#define CSERVERSOCKET_H

#include "CSocket.h"
#include "CClientSocket.h"
#include "BiblioStd.h"

class CServerSocket : public CSocket {

	public:

		CServerSocket (int port);

		const CServerSocket& operator << ( const string& s ) const;
		const CServerSocket& operator >> ( string& s ) const;

		void accept ( CClientSocket& sock );

};

#endif // CSERVERSOCKET_H
