#ifndef CBATEAU_H
#define CBATEAU_H

#include "BiblioStd.h"

/*
 * Classe représentant un bateau dans un jeu de bataille navale.
 * Il est caractérisé par un nom, une taille, une position et un tableau de booléens indiquant les dégâts subis.
 */
class CBateau {
  private:
    int m_taille;  // Taille du bateau
    string m_nom;  // Nom du bateau
    pair<int, int> m_position;  // Position actuelle du bateau sous forme de paire (x, y)
    bool* m_pDegats;  // Pointeur vers un tableau de booléens indiquant les dégâts subis

  public:
    /*
     * Constructeur par défaut initialisant un bateau sans nom et sans position
     */
    CBateau();
    /*
     * Constructeur de la classe CBateau
     * @param n Nom du bateau
     * @param p Position du bateau sous forme de paire (x, y)
     * @param t Taille du bateau
     */
    CBateau(string n, pair<int, int> p, int t);
    /*
     * Copy-constructor
     * @param other CBateau à copier
     */
    CBateau(const CBateau& other);
    /*
     * Destructeur
     */
    ~CBateau();
    /*
     * Surcharge de l'opérateur d'affectation
     * @param other CBateau à copier
     * @return Référence vers le bateau copié
     */
    CBateau& operator=(const CBateau& other);
    /*
     * Surcharge de l'opérateur d'insertion pour l'affichage du bateau
     * @param os Flux de sortie
     * @param theB Bateau à afficher
     * @return Référence vers le flux de sortie
     */
    friend ostream& operator<<(ostream& os, CBateau& theB);

    /*
     * Retourne l'état de dégât à l'index donné
     * @param i Index de l'état de dégât
     * @return true si le bateau est touché à l'index donné, false sinon
     */
    bool getDegats(int i);
    /*
     * Retourne la taille du bateau
     * @return Taille du bateau
     */
    int getTaille() const;
    /*
     * Retourne le nom du bateau
     * @return Nom du bateau
     */
    string getNom() const;
    /*
     * Retourne la position actuelle du bateau
     * @return Position du bateau sous forme de paire (x, y)
     */
    pair<int, int> getPosition() const;

    /*
     * Définit la position du bateau
     * @param i Nouvelle position x
     * @param j Nouvelle position y
     */
    void setPosition(int i, int j);
    /*
     * Vérifie si le bateau est coulé
     * @return true si le bateau est coulé, false sinon
     */
    bool estCoule();
    /*
     * Traite un tir adverse à la position donnée et retourne si le bateau est touché
     * @param p Position du tir adverse sous forme de paire (x, y)
     * @return true si le bateau est touché, false sinon
     */
    bool tirAdverse(pair<int, int> p);
};

#endif
