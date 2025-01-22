/*
 * 	Classe g�n�rique qui d�finit les actions possibles � la fois pour un client et pour un serveur.
 * 	Elle est d�riv�e par CJoueurCli et CJoueurServ.
 */

#ifndef CJOUEUR_H
#define CJOUEUR_H

#include "CClientSocket.h"
#include "CArmada.h"
#include "CCoups.h"
#include "CBaseJeu.h"

class CJoueur {

	protected:
		CClientSocket* m_pSocCli;						// Pointeur sur la socket client
		CBaseJeu* m_pGui;								// Pointeur sur l'interface graphique du joueur

	public:
		CJoueur ( CBaseJeu* pGui );						// Constructeur : pointeur sur l'interface utilisateur en param�tre
        
		void attaque ( pair<int,int> tir , CCoups& e );	// Lancement d'une attaque vers l'adversaire. En REPONSE, l'adversaire renvoie "touche", "coule" ou "plouf" ce qui permet de mettre � jour la structure CCoups du joueur avec la cl� "touche" ou "dansLEau".
		        
		void attendre();								// Attendre la r�ponse client ou serveur
        
		void ok();										// Client pr�t
      	
		void attaqueAdverse ( CArmada* f, CCoups* e );	// Lorsque l'adversaire tire sur la grille joueur, il faut lui REPONDRE en envoyant � l'adversaire une information parmi "touche", "coule" ou "plouf". Si l'adversaire fait "plouf", on note �galement cette information dans la structure qui m�morise les tirs, avec la cl� "ploufAdverse". Les param�tres "f" et "e" sont donc ceux du joueur (et pas de l'adversaire).
};

#endif // CJOUEUR_H
