#ifndef CARMADA_H
#define CARMADA_H

#include "BiblioStd.h"
#include "CBateau.h"

/*
 * Classe représentant une armada de bateaux dans un jeu de bataille navale.
 * Elle est caractérisée par une liste de bateaux.
 */
class CArmada {
  private:
    vector<CBateau> m_listeBateaux;  // Liste des bateaux de l'armada

  public:
    /*
     * Ajoute un bateau à l'armada
     * @param unBat Référence vers le bateau à ajouter
     */
    void ajouterBateau(CBateau& unBat);
    /*
     * Retourne le bateau à l'index donné
     * @param i Index du bateau à retourner
     * @return Pointeur vers le bateau à l'index donné
     */
    CBateau* getBateau(int i);
    /*
     * Retourne le nombre de bateaux dans l'armada
     * @return Nombre de bateaux dans l'armada
     */
    int getEffectifTotal();
    /*
     * Retourne le nombre de cases occupées par l'armada
     * @return Nombre de cases occupées par l'armada
     */
    int getNbreTotCases();
    /*
     * Retourne le nombre de bateaux pas encore coulés
     * @return Nombre de bateaux pas encore coulés
     */
    int getEffectif();
    /*
     * Récupère l'armada depuis le fichier flotille.txt et l'ajoute à m_listeBateaux
     */
    void getArmadaFromFile();
    /*
     * Place aléatoirement les bateaux de l'armada sur la grille
     * @return Vrai si les bateaux ont été placés, faux sinon
     */
    bool placerAleatoirement();
};

#endif
