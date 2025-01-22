/*
 * 	Cette classe repr�sentante le joueur qui prendra le r�le de serveur (au sens r�seau) dans le jeu.
 *  Le serveur joue contre UN SEUL client.
 *	Le serveur envoie, en d�but de partie, la flottille (armada) au client.
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
    		
		CJoueurServ ( CBaseJeu* g );				// Constructeur : m_pGui initialis� � g et m_pSocServ � NULL
        
		void openSocket ( int port );				// Ouvrir une socket
        	
		void attenteClient();						// Attend la cr�ation du client
        
		CArmada* getArmadaFromFile();				// Lecture des donn�es � partir du fichier "data.ini" qui d�crit la flottille du client et du serveur. Ajout de chacun des bateaux dans l'armada. Construit dynamiquement l'armada et renvoie un pointeur sur cette armada.
     
		int envoisArmadaVersClient ( CArmada& f );	// Envoie au client de l'armada (nom des bateaux et taille) sous la forme d'une cha�ne de caract�res.
		
		~CJoueurServ();
		
};

#endif // CJOUEURSERV_H
