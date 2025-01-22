#include "CJoueurCli.h"

CJoueurCli :: CJoueurCli ( CBaseJeu* g ) : CJoueur(g) {
	// H�rit� de CJoueur	
	m_pSocCli = NULL;
}

/**********************************************/

void CJoueurCli :: connectServer (const string& host, int port ) {

	// Construction du socket client h�rit� de CJoueur
	m_pSocCli = new CClientSocket ( host, port );

	cout << "Client connecte au serveur..." << endl;
}

/**********************************************/

CArmada* CJoueurCli::getArmadaCli () {

	CArmada* f;
	string recu;

	// attendre que le joueur client soit pr�t
	ok();
	recu = m_pSocCli->uneLigne ();
	cout << "Armada recue du serveur..." << endl;

	istringstream iss(recu);
    int nbTotBat;
	iss >> nbTotBat;

	cout << "nbTotBat = " <<  nbTotBat << endl;

	// Cr�ation dynamique de la flotille
	f = new CArmada ();

	for ( int i=0; i<nbTotBat; i++ ) {

		recu = m_pSocCli->uneLigne ();
		cout << endl << "Une ligne recue : " << recu << endl;

		// s�paration des donn�es pour cr�er la flotte
		// on d�coupe la chaine suivant le :
		string nom;
		string taille;
		int itaille;
		istringstream iss(recu);
		getline( iss, nom, ':' );
		getline( iss, taille, ':' );

		istringstream staille(taille);
		staille >> itaille;

		cout << "Nom du bateau = " << nom << "\ttaille = " << itaille << endl;

		// On rajoute les bateaux dans la flotte de d�part du client.
		// La position horizontale commence par d�faut en (0, 0).
		// Ils devront ensuite �tre positionn�s correctement sur la grille.
		CBateau b (nom, pair<int,int>(0,0), itaille);
		f->ajouterBateau(b);
    }

    cout << endl << "Armada client constituee" << endl;

    return f;
}

/**********************************************/

CJoueurCli::~CJoueurCli() {

	cout << "Destructeur de CJoueurCli, adr = " << this << endl;

	if ( m_pSocCli != NULL ) {
		delete m_pSocCli;
		m_pSocCli = NULL;
	}
}


