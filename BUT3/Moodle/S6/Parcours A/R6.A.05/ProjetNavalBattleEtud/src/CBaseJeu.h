/*
 * 	Cette classe définit un ensemble de fonctionnalités que l'interface graphique (CGui) devra implémenter.
 * 	Elle est ABSTRAITE et doit donc être héritée.
 */

#ifndef CBASEJEU_H
#define CBASEJEU_H

#include "BiblioStd.h"

class CBaseJeu {

	public:
		virtual bool positionnerBateaux () = 0;								// méthode abstraite voir classe CGui
		virtual pair<int,int> choisirAttaque () = 0;						// méthode abstraite voir classe CGui
		virtual void afficheGagne() = 0;									// méthode abstraite voir classe CGui
		virtual void affichePerdu() = 0;									// méthode abstraite voir classe CGui

	private:
		virtual void remplirDeuxGrilles ( ostream& os ) = 0;				// méthode abstraite voir classe CGui
		virtual void afficherLaGrille ( ostream& os, string jouOuAdv ) = 0;	// méthode abstraite voir classe CGui
};

#endif // CBASEJEU_H
