#include "CJoueur.h"

CJoueur::CJoueur ( CBaseJeu* g ) {

	m_pGui = g;
}

/**********************************************/

void CJoueur::attaque ( pair<int,int> tir, CCoups& e ) {

	string str;

   	cout << endl << "Attaque sur l'adversaire : coord. ligne = " << tir.first << "\tcoord. colonne = " << tir.second << endl;

	// on passe le tir en 2 coordonnées (first, second) et on les envoie par réseau à l'adversaire
	// dans une seule chaîne de caractères (les 2 coord. sont séparées par un espace)
	ostringstream strout;
	strout << tir.first << " " << tir.second; // l'espace sépare les 2 coordonnées
	*m_pSocCli << strout.str();			   

    // on récupère le résultat de l'attaque, c'est-à-dire la REPONSE de l'adversaire : "dansLEau" ou "touche"
	*m_pSocCli >> str;

	// si le tir est un plouf alors str = "dansLEau"
	// si le tir est victorieux alors :
	//		- soit str = "nomDuBateau touche"
	//		- soit str = "nomDuBateau coule"
	cout << endl<< "Reponse de l'adversaire suite a l'attaque : " << str << endl;

	// mise à jour de la structure de données qui comptabilise les tirs (dansLEau ou touche ou coule) du joueur
    // dansLEau
    if (str == "dansLEau") e.ajouterTirMap ( "dansLEau", tir );
	// touché ou coulé
    else e.ajouterTirMap ( "touche", tir );
}

/**********************************************/

void CJoueur::attendre() {

	string str;
	*m_pSocCli >> str;
}

/**********************************************/

void CJoueur::ok() {

	*m_pSocCli << " ";
}

/**********************************************/

void CJoueur::attaqueAdverse ( CArmada* f, CCoups* e ) {

	CBateau* theB = NULL;	
	string str;
	int n1;
	int n2;
	bool stop = false;
	int i = 0;

	// réception des données réseau envoyées par l'adversaire
	// il y a 2 coordonnées : n1=tir.first et n2=tir.second qui sont réceptionnées
	// dans une seule chaîne de caractères décodée (2 coord. séparées par un espace)
	*m_pSocCli >> str;
	istringstream iss(str);
	iss >> n1 >> n2; // l'espace sépare les 2 coordonnées

	// les coord. du coup tiré par l'adversaire
    pair<int,int> coup ( n1, n2 );

	cout << endl << "Tir adverse : (" << n1 << ", " << n2 << ")" << endl;

	int num = f->getEffectifTotal();

	cout << "RESULTAT du tir adverse... ";

 	// est-on touché ?
   	while ( (i<num) && (!stop) ) {

		theB = f->getBateau(i);
		// tirAdverse (...) ne renvoie vrai que si la case est touchée pour la première fois !!
		if ( theB->tirAdverse (coup) ) {

			if ( theB->estCoule() ) {

				cout << " bateau " << theB->getNom() << " COULE..." << endl;

				str = theB->getNom() + " coule";
				// REPONSE renvoyée à l'adversaire du type "nomDuBateau coule"
				*m_pSocCli << str;
				stop = true;
			}

			else {
				cout << " bateau " << theB->getNom() << " TOUCHE..." << endl;
				
				str = theB->getNom() + " touche";
				// REPONSE renvoyée à l'adversaire du type "nomDuBateau touche"
				*m_pSocCli << str;
				stop = true;
			}
		}

		i++;
    }

    // Sinon "Plouf"
	if ( !stop ) {
		cout << " PLOUF..." << endl;
		
    	// REPONSE renvoyée à l'adversaire : "dansLEau"
    	*m_pSocCli << "dansLEau";
    	// On ajoute l'info du "plouf" adverse dans l'historique des coups pour le CJoueur
    	e->ajouterTirMap ( "ploufAdverse", coup );
	}

}

