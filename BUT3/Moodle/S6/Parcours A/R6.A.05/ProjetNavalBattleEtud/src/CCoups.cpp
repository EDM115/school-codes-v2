#include "CCoups.h"

CCoups::CCoups() {

	// Une collection (vide à ce stade)
	vector<pair<int,int> > tmp;

	// Affectation (par recopie !) de la collection "tmp" dans chaque collection associée à une clé
	m_donnees["dansLEau"] = tmp;
	m_donnees["touche"] = tmp;
	m_donnees["ploufAdverse"] = tmp;
}

/**********************************************/

int CCoups::getCoupsBut() {

	return m_donnees["touche"].size();
}

/**********************************************/

bool CCoups::tirDansMap (const string& s, pair<int,int>& p) {

	bool ret;

	vector<pair<int,int> >::iterator begin = m_donnees[s].begin();
	vector<pair<int,int> >::iterator end = m_donnees[s].end();

	if ( find(begin, end, p) == end ) ret = false;
	else ret = true;

	return ret;
}

/**********************************************/

void CCoups::ajouterTirMap (const string& s, pair<int,int>& p) {
	
	m_donnees[s].push_back(p);
}

/**********************************************/

int CCoups::getTaille (const string& s) {
	
	return m_donnees[s].size();
}

/**********************************************/

pair<int,int> CCoups::getPair ( const string& s, int i ) {
	
	return m_donnees[s][i];
}

