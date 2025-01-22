#include "CJoueurServ.h"

CJoueurServ::CJoueurServ ( CBaseJeu* g ) : CJoueur(g) {
	m_pSocServ = NULL;
}

/**********************************************/

void CJoueurServ::openSocket ( int port ) {

	m_pSocServ = new CServerSocket ( port );
	cout << "Ouverture socket serveur : reussi..." << endl;		
}

/**********************************************/

void CJoueurServ::attenteClient() {

	m_pSocCli = new CClientSocket();
    m_pSocServ->accept ( *m_pSocCli );
    cout << "Client connecte" << endl;
}

/**********************************************/

int CJoueurServ::envoisArmadaVersClient ( CArmada& f ) {

	CBateau* unBat;

	// on attend que le client soit prêt à recevoir
	attendre();

	// il faut envoyer la flotte au client
	string aEnvoyer;

	// on envoie le nombre de lignes à envoyer
	ostringstream oss;    
	oss << f.getEffectifTotal();
	aEnvoyer = oss.str();
	aEnvoyer = aEnvoyer + '\n';
	*m_pSocCli << aEnvoyer;

	// on boucle sur tous les bateaux
	for ( int i = 0; i<f.getEffectifTotal(); i++ ) {
		
		unBat = f.getBateau ( i );
		ostringstream oss;
		// on envoie le nom du bateau et sa taille
		oss << unBat->getTaille();
		aEnvoyer = unBat->getNom() + ":"+ oss.str();
		cout << "Bateau envoye au client : " << aEnvoyer << endl;
		aEnvoyer = aEnvoyer + '\n';
		*m_pSocCli << aEnvoyer;
	}

	cout << "Toute l'armada est envoyee au client" << endl;

	return 0;
}

/**********************************************/

CJoueurServ::~CJoueurServ() {

	cout << "Destructeur de CJoueurServ, adr = " << this << endl;
	if ( m_pSocServ != NULL ) delete m_pSocServ;

	if ( m_pSocCli != NULL ) {
		delete m_pSocCli;
		m_pSocCli = NULL;
	}
}

