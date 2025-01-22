/*
 * 	Cette classe représentante le joueur qui prendra le rôle de serveur (au sens réseau) dans le jeu.
 *  Le serveur joue contre UN SEUL client.
 *	Le serveur envoie, en début de partie, la flottille (armada) au client.
 */

#ifndef CJOUEURSERV_H
#define CJOUEURSERV_H

#include "BiblioStd.h"
#include "CSocket.h"
#include "CArmada.h"
#include "CJoueur.h"
#include "CServerSocket.h"

class CJoueurServ : public CJoueur {

	private:
		CServerSocket* m_pSocServ;

	public:
    		
		CJoueurServ ( CBaseJeu* g );				// Constructeur : m_pGui initialisé à g et m_pSocServ à NULL
        
		void openSocket ( int port );				// Ouvrir une socket
        	
		void attenteClient();						// Attend la création du client
        
		CArmada* getArmadaFromFile();				// Lecture des données à partir du fichier "data.ini" qui décrit la flottille du client et du serveur. Ajout de chacun des bateaux dans l'armada. Construit dynamiquement l'armada et renvoie un pointeur sur cette armada.
     
		int envoisArmadaVersClient ( CArmada& f );	// Envoie au client de l'armada (nom des bateaux et taille) sous la forme d'une chaîne de caractères.
		
		~CJoueurServ();
		
};

#endif // CJOUEURSERV_H
