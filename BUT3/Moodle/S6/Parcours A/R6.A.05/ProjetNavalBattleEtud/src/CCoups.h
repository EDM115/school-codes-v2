/*
 *	Cette classe mémorise tous les tirs à la fois du joueur et de l'adversaire :
 *      	- tout ce que le joueur a mis dans l'eau dans la grille adverse (les tirs ratés du joueur : "dansLEau")
 *       	- tout ce que le joueur a touché comme bateaux adverses (les tirs victorieux du joueur : "touche")
 *       	- tout ce que l'adversaire a mis dans l'eau dans la grille du joueur (les tirs ratés de l'adversaire : "ploufAdverse")
 *
 *	On se rappelle que les tirs victorieux de l'adversaire sont stockés dans le tableau m_degats de chaque bateau du joueur.
 */

#ifndef CCOUPS_H
#define CCOUPS_H

#include "BiblioStd.h"

class CCoups {

	private:
	
		/* La structure de données est de type map (équivalent à HashTable en Java).
		 * Notre "map" (m_donnees), est un tableau où chaque case du tableau est accessible par une clé de type string.
		 * Par convention, on dispose de 3 clés : "dansLEau", "touche" et "ploufAdverse".
		 * UNE case du tableau contient une collection (vector) de coordonnées de type pair(horiz, vert).
		 * Ces coordonnées représentent les tirs.
		 * Pour récupérer les collections de tirs, il suffit d'utiliser la clé :
		 *	m_donnees["dansLEau"] : renvoie la collection (vector) des tirs (coord.) que le joueur a mis dans l'eau dans la grille adverse
		 *	m_donnees["touche"] : renvoie la collection (vector) des tirs (coord.) victorieux du joueur dans la grille adverse
		 * 	m_donnees["ploufAdverse"] : renvoie la collection (vector) des tirs (coord.) que l'adversaire a mis dans l'eau dans la grille du joueur
		 * Note importante : pour connaître les dégâts sur les bateaux du joueur, consulter le tableau m_degats de chaque
		 * bateau du joueur.
		 */
		map < string, vector < pair < int,int> > > m_donnees;

	public:
		CCoups();													// construction par défaut : des collections vides
        
		int getCoupsBut();											// renvoie le nombre de tirs victorieux du joueur (tirs "touche")
        
		bool tirDansMap ( const string& s, pair<int,int>& p );		// renvoie un booléen à vrai si le tir passé en paramètre et associé à une clé est dans la structure m_donnees
        
		void ajouterTirMap ( const string& s, pair<int,int>& p );	// ajoute un nouveau tir "p" dans la collection associée à la clé "s" passée en paramètre
       
		int getTaille ( const string& s );							// renvoie la taille de la collection (vector) associée à la clé "s"
        
		pair<int,int> getPair ( const string& s, int index );		// renvoie le tir stocké à l'indice "index" dans la collection associée à la clé "s"

};

#endif // CCOUPS_H
