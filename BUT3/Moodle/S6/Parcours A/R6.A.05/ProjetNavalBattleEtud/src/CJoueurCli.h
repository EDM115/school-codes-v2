/*
 * 	Cette classe repr�sentante le joueur qui prendra le r�le de client (au sens r�seau) dans le jeu.
 *  Le client joue contre le serveur.
 *	C'est le client qui re�oit la flotille de d�part du serveur.
 */

#ifndef CJOUEURCLI_H
#define CJOUEURCLI_H

#include "BiblioStd.h"
#include "CArmada.h"
#include "CJoueur.h"
#include "CBaseJeu.h"

class CJoueurCli : public CJoueur {

	public:
		CJoueurCli ( CBaseJeu* g );								// Constructeur
		~CJoueurCli();											// Destructeur
		void connectServer ( const string& host, int port );	// Connexion au serveur en passant les param�tres host et port en dur

		CArmada* getArmadaCli();								// Cette m�thode r�cup�re la flotille du client � travers le r�seau.
																// En effet, c'est le serveur qui d�cide de la composition de la flotte et qui envoie cette composition au joueur client en d�but de partie.
};

#endif // CJOUEURCLI_H
