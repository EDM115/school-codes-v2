#include "CSocket.h"

CSocket::CSocket() {

	m_sock = (-1);
	memset (&m_addr, 0, sizeof(m_addr));
}

/**********************************************/

CSocket::~CSocket() {

	if ( isValid() ) {
		cout << "Destructeur de CSocket, adr = " << this << "\t Fermeture du socket valide : " << m_sock << endl;
		close (m_sock);
	}
}

/**********************************************/

bool CSocket::methCreate() {

	bool ret = true;
  	int on = 1;

	cout << "Create() socket" << endl;

	m_sock = socket ( AF_INET, SOCK_STREAM, 0 );
	if ( ! isValid() ) {
		cout << "Erreur : Socket non valide" << endl;
		ret = false;
	}

	if ( ret ) {

  		if ( setsockopt ( m_sock, SOL_SOCKET, SO_REUSEADDR, (const char* ) &on, sizeof (on) ) == -1 ) {
			cout << "Erreur : options socket non valides" << endl;
			ret = false;
		}
	}

	if (ret) cout << "Create() socket OK" << endl;

	return ret;
}

/**********************************************/

bool CSocket::methBind (const int port) {

	bool ret = true;

	cout << "Bind() socket" << endl;

	if ( ! isValid() ) {
		cout << "Erreur : Socket non valide" << endl;
		ret = false;
	}

	if ( ret ) {
		m_addr.sin_family = AF_INET;
		m_addr.sin_addr.s_addr = INADDR_ANY;
		m_addr.sin_port = htons (port);

		int bindReturn = bind ( m_sock, (struct sockaddr *) &m_addr, sizeof(m_addr ) );

  		if ( bindReturn == -1 ) {
			cout << "Erreur : bind non valide" << endl;
			ret = false;
		}
	}

	if (ret) cout << "Bind() socket OK" << endl;

	return ret;
}

/**********************************************/

bool CSocket::methListen () const {

	bool ret = true;

	cout << "Listen() socket" << endl;

	if ( ! isValid() ) {
		cout << "Erreur : Socket non valide" << endl;
		ret = false;
	}

	if ( ret ) {

		int listenReturn = listen ( m_sock, MAXCONNECTIONS );

		if ( listenReturn == -1 ) {
			cout << "Erreur : listen non valide" << endl;
			ret = false;
		}

		else if ( listenReturn == 0 ) cout << "listen : reussi..." << endl;
	}

	if (ret) cout << "Listen() socket OK" << endl;

	return ret;
}

/**********************************************/

bool CSocket::methAccept ( CSocket& newSocket ) const {

	bool ret = true;

	cout << "... en attente du client ..." << endl;

	int addrLength = sizeof (m_addr);

  	newSocket.m_sock = accept (m_sock, (sockaddr *) &m_addr, (socklen_t *) &addrLength);

 	if (newSocket.m_sock <= 0) {
		cout << "Erreur : socket non accepte" << endl;
		ret = false;
	}

	return ret;
}

/**********************************************/

bool CSocket::methSend ( const string s ) const {

	bool ret = true;

	int status = send (m_sock, s.c_str(), s.size(), MSG_NOSIGNAL);

	if (status == -1) {
		cout << "Erreur : socket pas envoye" << endl;		
		ret = false;
	}

	return ret;
}

/**********************************************/

int CSocket::methRecv ( string& s ) const {

	int ret;

	char buf [MAXRECV + 1];

	s = "";	// mise à zéro de la chaîne
	memset (buf, 0, MAXRECV + 1);

	//recv renvoie le nombre d'octets reçus
	ret = recv (m_sock, buf, MAXRECV, 0);

	if (ret == -1) {
      		cout << "Erreur : status == -1   errno == " << errno << "  in CSocket::methRecv \n";
      		ret = 0;
  	}
	else s = buf;

	return ret;
}

/**********************************************/

string CSocket::recvLine () {

	string ret;
	bool stop = false;

	while ( !stop ) {

		char r;
		
		switch ( recv (m_sock, &r, 1, 0 ) ) {

			case 0 : 
				ret = "";
				stop = true;
				break;
			case -1 :
				if ( errno != EAGAIN ) ret = "";
				stop = true;
				break;
		}
		
		if ( !stop ) {
			ret += r;
			if ( r == '\n' ) stop = true;
		}
	}

	return ret;
}


/**********************************************/

bool CSocket::methConnect ( const string host, const int port ) {

	bool ret = true;
	int status;

	if ( ! isValid() ) {
		cout << "Erreur : Socket non valide" << endl;
		ret = false;
	}

	if ( ret ) {
		m_addr.sin_family = AF_INET;
		m_addr.sin_port = htons (port);

		status = inet_pton (AF_INET, host.c_str(), &m_addr.sin_addr);

		if (errno == EAFNOSUPPORT) {
			cout << "Erreur : EAFNOSUPPORT" << endl;
			ret = false;
		}

		else {
			status = connect (m_sock, (sockaddr *) &m_addr, sizeof(m_addr));

  			if (status == 0) ret = true;
  			else {
				cout << "Erreur : status error" << endl;
				ret = false;
			}
		}
	}

	return ret;
}

/**********************************************/

void CSocket::setNonBlocking (const bool b) {

	int opts;

  	opts = fcntl (m_sock, F_GETFL);

  	if (opts >= 0) {

		if ( b ) opts = (opts | O_NONBLOCK);
  		else opts = (opts & ~O_NONBLOCK);
  		fcntl(m_sock, F_SETFL,opts);
	}

	else cout << "Erreur : CSocket::setNonBlocking(...)" << endl;
}

/**********************************************/

bool CSocket::isValid() const {

	return (m_sock != -1); 
}
