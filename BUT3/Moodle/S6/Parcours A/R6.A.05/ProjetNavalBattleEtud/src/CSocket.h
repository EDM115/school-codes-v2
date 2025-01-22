/*
 * 	Classe permettant la gestion d'une communication client/serveur � travers le r�seau.
 * 	Elle est h�rit�e par CClientSocket et CServerSocket.
 */

#ifndef CSOCKET_H
#define CSOCKET_H

#include "BiblioStd.h"

class CSocket {

	private:
		sockaddr_in m_addr;
		int m_sock;

	private:
		bool isValid() const;

	public:
		CSocket();
		~CSocket();

		// Initialisation du serveur
		bool methCreate();
		bool methBind ( const int port );
		bool methListen() const;
		bool methAccept ( CSocket& newSocket ) const;

		// initialisation du client
		bool methConnect ( const string host, const int port );

		// r�ception / transmission des donn�es
		bool methSend ( const string str ) const;
		int methRecv ( string& s ) const;
		string recvLine ();

		void setNonBlocking ( const bool var );
};

#endif // CSOCKET_H
