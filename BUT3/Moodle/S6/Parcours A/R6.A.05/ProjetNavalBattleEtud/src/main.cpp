#include "CJoueurServ.h"
#include "CJoueurCli.h"
#include "CArmada.h"
#include "CGui.h"
#include "BiblioStd.h"

// Simple signature de la fonction dialogueChoix à déclarer ici
int dialogueChoix ();

int main() {

	CGui* pGuiServ = NULL;
	CGui* pGuiCli = NULL;
	bool okPlcmt;

	// Nombre de cases de bateaux adverses touchées par des tirs.
	// Si ce nombre = nbre total de cases de la flottille alors la flottille adverse est coulée.
	int nbToucheAdverse = 0;

	// Pour démarrer : choix client ou serveur
	int choix = dialogueChoix ();
	
	// Initialisation aléatoire
	srand(time(NULL));

	switch (choix) {
		
		// choix serveur
		case 0: {
			cout << "Mode serveur demarre..." << endl;

			// Construction de l'interface utilisateur côté serveur
			pGuiServ = new CGui();

			// Création du joueur de type serveur
			CJoueurServ jouServ ( pGuiServ );

			// Ouvrir la socket sur le port PORTCOM
			// Le serveur se met à l'écoute d'une connexion client sur ce port
			// Si cela se passe mal, une exception logic_error est lancée (et pas capturée...)
			jouServ.openSocket( PORTCOM );

			// Attendre le client
			// Le message "... en attente du client" doit apparaître
			jouServ.attenteClient();

			// Récupérer les données du jeu à partir du fichier "flotille.txt" qui se trouve dans /ws.
			// L'armada est construite dynamiquement : on crée un pointeur sur cette armada.
			CArmada* pArmServ  = new CArmada();
			pArmServ->getArmadaFromFile();

			// Envoyer les données du jeu au client.
			// L'armada "pArmServ" est envoyée par réseau au client.
			jouServ.envoisArmadaVersClient ( *pArmServ );

			// Le serveur attend que le client ait positionné ses bateaux.
			// attendre() est méthode de CJoueur.
			jouServ.attendre();
			
			// Structure de données des tirs pour le serveur créée.
			CCoups* pCoupsServ = new CCoups();

			// Mise à jour de la GUI du serveur.
			// A noter : les structures pointées par pArmServ et pCoupsServ seront détruites dans le destructeur de CGui.
			pGuiServ->setArmadaCoups ( pArmServ, pCoupsServ );
			
			// Finalement, le serveur place ses bateaux sur sa grille.
			okPlcmt = pGuiServ->positionnerBateaux();
			if (okPlcmt) cout << endl << "Placement armada reussi..." << endl << endl;
			else cout << endl << "*** PLACEMENT armada IMPOSSIBLE ! ***" << endl << endl;			

			/*** DEBUT DU JEU ***/
			// ancienne version du while(...) plus nécessaire, la sortie se faisant avec 2 break
			// while ( (pArmServ->getEffectif() > 0) && (pArmServ->getNbreTotCases() != nbToucheAdverse) && okPlcmt ) {
			while ( okPlcmt ) {

				// C'est le serveur qui tire en premier
				pair<int,int> coup = pGuiServ->choisirAttaque ();
				
				// envoie d'un tir au client par réseau
				// ET récupération de sa réponse (coulé, touché ou plouf) pour mettre à jour pCoupsServ
				jouServ.attaque ( coup, *pCoupsServ );
				
				// Afficher les deux grilles pour le serveur
				cout << *pGuiServ;

				// mise à jour de nbToucheAdverse
				
				nbToucheAdverse = pCoupsServ->getCoupsBut();
				// Si toutes les cases de l'adversaire sont touchées alors le serveur a gagné	
				if (pArmServ->getNbreTotCases() == nbToucheAdverse) {
					// le serveur a gagné
					cout << "LE SERVEUR" << endl;
					pGuiServ->afficheGagne();
					break;
				}

				// ATTENTE d'une attaque client par réseau
				// Dès que le client tire, on lui envoie une réponse : coulé, touché ou plouf
				jouServ.attaqueAdverse (pArmServ, pCoupsServ);
				
				// suite à l'attaque, le serveur peut perdre
				// Si il ne reste plus aucun bateau alors le serveur a perdu								              
				if (pArmServ->getEffectif() == 0) {
					// le serveur a perdu
					cout << "LE SERVEUR" << endl;
					pGuiServ->affichePerdu();
					break;
				}
                
				// Afficher les deux grilles pour le serveur
				cout << *pGuiServ;
			}

			// Obligatoire sinon "case 1" exécuté
			break;
		}

		// choix client
		case 1: {

			cout << "Mode client demarre..." << endl;

			// Construction de l'interface utilisateur côté client
			pGuiCli = new CGui();

			// Se connecter au serveur sur le port PORTCOM
			CJoueurCli jouCli ( pGuiCli );
			// Si cela se passe mal, une exception logic_error est lancée (et pas capturée...)
			jouCli.connectServer ( "localhost", PORTCOM );	// "localhost" ou "127.0.0.1"

			cout << "Connexion au serveur Ok" << endl;

			// Récupérer les données du jeu.
			// C'est le serveur qui crée l'armada client et le lui envoie par réseau.
			CArmada* pArmCli  = jouCli.getArmadaCli();
			
			// Structure de données des tirs pour le client créée.
			CCoups* pCoupsCli = new CCoups();

			// Mise à jour de la GUI du client.
			// A noter : les structures pointées par pArmCli et pCoupsCli seront détruites dans le destructeur de CGui.
			pGuiCli->setArmadaCoups ( pArmCli, pCoupsCli );
			
			// Le client place ses bateaux sur sa grille.
			okPlcmt = pGuiCli->positionnerBateaux();
			if (okPlcmt) {
				cout << endl << "Placement armada reussi..." << endl << endl;
				// Le client informe le serveur qu'il a placé ses bateaux
				jouCli.ok();
			}				
			else cout << endl << "*** PLACEMENT armada IMPOSSIBLE ! ***" << endl << endl;						
			

			/*** DEBUT DE PARTIE (pour le client) ***/
			// ancienne version du while(...) plus nécessaire, la sortie se faisant avec 2 break
			// while ( (pArmCli->getEffectif() > 0) && ( pArmCli->getNbreTotCases() != nbToucheAdverse ) && okPlcmt ) {
			while ( okPlcmt ) {

				// ATTENTE d'une attaque du serveur par réseau
				// Dès que le serveur tire, on lui envoie une réponse : coulé, touché ou plouf
				jouCli.attaqueAdverse (pArmCli, pCoupsCli);

				// Afficher les deux grilles pour le client
				cout << *pGuiCli;

				// suite à l'attaque, le client peut perdre
				// Si il ne reste plus aucun bateau alors le client a perdu			               
				if (pArmCli->getEffectif() == 0) {
					// le client a perdu
					cout << "LE CLIENT" << endl;
					pGuiCli->affichePerdu();
					break;
				}

				// au client à choisir un tir
				pair<int,int> coup = pGuiCli->choisirAttaque ();

				// envoie d'un tir au serveur par le réseau
				// ET récupération de sa réponse (coulé, touché ou plouf) pour mettre à jour pCoupsCli
				jouCli.attaque ( coup, *pCoupsCli );
				
				// Afficher les deux grilles pour le client
				cout << *pGuiCli;
			
				// mise à jour de nbToucheAdverse
				nbToucheAdverse = pCoupsCli->getCoupsBut();
				
				// Si toutes les cases de l'adversaire sont touchées alors le client a gagné
				if (pArmCli->getNbreTotCases() == nbToucheAdverse) {
					// le client a gagné
					cout << "LE CLIENT" << endl;
					pGuiCli->afficheGagne();
					break;
				}
			}

			// Obligatoire
			break;
		}

	}

	// Destruction des zones dynamiques
	if ( pGuiServ != NULL ) delete pGuiServ;
	if ( pGuiCli != NULL ) delete pGuiCli;

	return 0;
}

/**********************************************/

// Simple dialogue avec l'utilisateur : renvoie 0 pour un choix serveur, 1 pour un choix client
int dialogueChoix () {

	string str;
	int ret;

	cout << "Demarrer le client ou le serveur : tapez 0 pour le serveur / 1 pour le client" << endl;
	cout << "!! Le serveur doit etre demarre avant le client..." << endl;

	getline ( cin, str, '\n' );
	istringstream tmp (str);
	tmp >> ret;

	return ( ret );	
}

