#ifndef CGUI_H
#define CGUI_H

#include "BiblioStd.h"
#include "CArmada.h"
#include "CBaseJeu.h"
#include "CCoups.h"

/*
 * Classe représentant l'interface graphique du jeu de bataille navale.
  * Elle est caractérisée par deux grilles de jeu, une pour les bateaux du joueur et une pour les bateaux de l'adversaire.
  * Elle est également caractérisée par un pointeur vers l'armada du joueur et un pointeur vers les coups joués par le joueur.
  * Elle hérite de la classe CBaseJeu.
 */
class CGui : public CBaseJeu {
  private:
    char m_grilleJou[TAILLE_GRILLE - 1][TAILLE_GRILLE - 1];  // Grille des bateaux du joueur
    char m_grilleAdv[TAILLE_GRILLE - 1][TAILLE_GRILLE - 1];  // Grille des coups joués par le joueur
    CArmada* m_pArmada;  // Pointeur vers l'armada du joueur
    CCoups* m_pCoups;  // Pointeur vers les coups joués par le joueur

  public:
    /*
     * Constructeur par défaut de la classe CGui.
     */
    CGui();
    /*
     * Constructeur de la classe CGui.
     * @param pArmada Pointeur vers l'armada du joueur
     * @param pCoups Pointeur vers les coups joués par le joueur
     */
    CGui(CArmada* pArmada, CCoups* pCoups);
    /*
     * Constructeur de copie de la classe CGui.
     * @param other Référence constante vers l'objet à copier
     */
    CGui(const CGui& other);
    /*
     * Destructeur de la classe CGui.
     */
    virtual ~CGui();
    /*
     * Surcharge de l'opérateur d'affectation.
     * @param other Référence constante vers l'objet à copier
     * @return Référence vers l'objet copié
     */
    CGui& operator=(const CGui& other);
    /*
     * Surcharge de l'opérateur de flux de sortie.
     * @param os Référence vers le flux de sortie
     * @param theG Référence constante vers l'objet à afficher
     * @return Référence vers le flux de sortie
     */
    friend ostream& operator<<(ostream& os, CGui& theG);

    /*
     * Mise à jour des attributs.
     * @param pArmada Pointeur vers l'armada du joueur
     * @param pCoups Pointeur vers les coups joués par le joueur
     */
    void setArmadaCoups(CArmada* pArmada, CCoups* pCoups);
    /*
     * Positionne les bateaux du joueur sur la grille.
     * @return Vrai si les bateaux ont été positionnés, faux sinon
     */
    bool positionnerBateaux();
    /*
     * Saisie des coordonnées de l'attaque.
     * @return Coordonnées de l'attaque, sous forme de paire (x, y)
     */
    pair<int, int> choisirAttaque();
    /*
     * Affiche que le joueur a gagné.
     */
    void afficheGagne();
    /*
     * Affiche que le joueur a perdu.
     */
    void affichePerdu();
    /*
     * Remplis les grilles des 2 joueurs.
     * La grille du joueur possède '-' pour les cases vides, 'B' pour les bateaux, 'X' pour les coups réussis par l'adversaire et 'O' pour les coups ratés.
     * La grille de l'adversaire possède '-' pour les cases vides, 'X' pour les coups réussis par le joueur et 'O' pour les coups ratés.
     * @param os Référence vers le flux de sortie
     */
    void remplirDeuxGrilles(ostream& os);
    /*
     * Affiche la grille du joueur ou de l'adversaire.
     * @param os Référence vers le flux de sortie
     * @param jouOuAdv Chaîne de caractères indiquant si la grille à afficher est celle du joueur ou de l'adversaire ("joueur" ou "adversaire")
     */
    void afficherLaGrille(ostream& os, string jouOuAdv);
};

#endif
