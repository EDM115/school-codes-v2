/*!
 * @file R204Map.h
 * @brief Définie les classes R204Pair et R204Map
 * @author F. Merciol
 * @version 0.1
 * @date 13 février 2023
 */
#ifndef _R204Map_hpp_
#define _R204Map_hpp_

#include "R204Iterator.h"
#include "R204Vector.h"
// ########################################
/*!
 * @class R204Pair
 * @brief pair généric associant une clef à une valeur
 */
template <class K, class T>
class R204Pair  {
public:
  /*! La clef */
  K key;
  /*! La valeur */
  T value;
  /*! Pour pouvoir être utilisé dans des tableaux de la classe Map. */
  R204Pair () : key (), value () {}
  /*!
   * Pour pouvoir référencer un association qui n'a pas encore de valeur (exemple opérateur[]).
   * @param key la clef (donc unique) de l'association
   */
  R204Pair (const K &key) : key (key), value () {}
};

// ########################################
/*!
 * @class R204Map
 * @brief classe de liste associatives génériques pour Arduino
 */
template <class K, class T>
class R204Map  {
public:
  /*! abréviation d'écriture */
  typedef R204Pair<K, T> pair;
  /*! abréviation d'écriture */
  typedef R204Iterator<pair> iterator;
  /*! abréviation d'écriture */
  typedef R204ConstIterator<pair> const_iterator;
private:
  /*! ensemble des pairs */
  R204Vector<pair>	_data;
  /*!
   * recherche la pair en fonction de la clef.
   * @return Retourne nullptr en cas d'echecs
   */
  pair			*find (const K &key);
  /*!
   * même méthode appliquée sur des pairs constantes
   * @return Retourne nullptr en cas d'echecs
   */
  const pair		*find (const K &key) const;
public:
  /*!
   * Constructeur d'une table vide, mais préallooué avec une capacité de @p n.
   * @param n nombre d'association réservées.
   */
  inline R204Map (const size_t &n = 0) : _data (n) {}
  /*!
   * Constructeur d'une table par clonage
   * @param m table de référence pour le clonage
   */
  inline R204Map (const R204Map<K, T> &m) : R204Map (0) { *this = m; }
  /*! Destructeur de la table, libérant les resources. */
  inline ~R204Map () {}

  /*!
   * indique si la table est vide
   * @return vrai si la table est vide (même s'il y a eu des réservation de ressources)
   */
  inline bool		empty () const { return _data.empty (); }
  /*!
   * Indique la taille de la table (i.e. le nombre de clefs).
   * @return le nombre de clefs réllement utilisées
   */
  inline size_t		size () const {return _data.size (); }

  /*!
   * recopie tous les éléments de @p m dans la table
   * @param m table de référence pour la copie
   * @return une référence sur this
   */
  inline R204Map<K, T>	&operator= (const R204Map &m);
  /*!
   * permute le contenu de 2 table.
   * C'est utile en particulier pour libérer la mémoire, car l'invocation de clear ne fait que mettre à zéro le nombre de clefs.
   * Pour vraiment lébérer la mémoire, il suffit de permutter avec une variable local qui va être immédiatement détruite.
   * @code
   *   R204Map<String, String> maTable (20);
   *   maTable["Hello"] = "Bonjour";
   *   maTable.swap (R204Map ()); // supprime toutes les ressources
   * @endcode
   * @param m autre table avec laquelle les associations seront échangées.
   */
  inline void		swap (R204Map &m);

  /*! ignore tous les éléments précédent (ne libère pas de mémoire) */
  inline void		clear () { _data.clear (); }
  /*!
   * augment la capacité de la table pour ateindre @p n (qui inclu les éléments visibles et la réserve).
   * @param n nouvelle taille utilisable de la table sans avoir à recourir à une nouvelle demande de mémoire au système
   */
  inline void		reserve (const size_t &n) { reserve (n); }
  /*! libère la mémoire de la réserve pour se limiter aux cellules utilisées */
  inline void		shrink_to_fit () { shrink_to_fit (); }

  /*!
   * Accés à la valeur correspondant à la clef @p key. Si la clef n'existe pas une valeur par défaut est créé.
   * @param key clef de l'élément recherché
   * @return une référence sur la valeur de l'élément qui peut ainsi être modifier
   */
  inline T		&operator[] (const K &key);
  /*!
   * Accés à la valeur correspondant à la clef @p key (provoque une exception si la clef n'est pas présente)
   * @param key clef de l'élément recherché
   * @return une référence sur la valeur de l'élément qui peut ainsi être modifier
   * @throws out_of_range dans le cas où la clef n'est pas présente
   */
  inline T		&at (const K &key);

  /*!
   * indique la présence d'une clef dans la table
   * @param key clef de l'élément recherché
   * @return vrai si la clef est présente
   */
  inline bool		contains (const K &key) const { return find (key) != nullptr; }
  /*!
   * iterateur sur la première clef
   * @return pointeur sur le premier couple clef/valeur
   */
  inline iterator	begin () const { return iterator (&_data[0]); }
  /*!
   * iterateur après la dernière clef pour marqueur de fin de boucle
   * @return pointeur après le dernier couple clef/valeur
   */
  inline iterator	end () const { return iterator (&_data[size ()]); }
  /*!
   * iterateur sur la première clef constante
   * @return pointeur sur le premier couple constant clef/valeur
   */
  inline const_iterator	cbegin () const { return const_iterator (&_data[0]); }
  /*!
   * iterateur après la dernière clef pour marqueur de fin de boucle
   * @return pointeur après le dernier couple constant clef/valeur
   */
  inline const_iterator	cend () const { return const_iterator (&_data[size ()]); }
  /*!
   * parcours les couples de la table avec une fonction passée en paramètre
   * @param f fonction lambda de type : void f (K k, T v)
   */
  template <class F>
  inline void		foreach (F f);
  /*!
   * même méthode appliquée sur une table de constantes
   * @param f fonction lambda de type : void f (const K k, const T v)
   */
  template <class F>
  inline void		foreach (const F f) const;
};

// ########################################
template <class K, class T>
R204Map<K, T> &
R204Map<K, T>::operator= (const R204Map &m) {
  _data = m._data;
  return *this;
}

template <class K, class T>
void
R204Map<K, T>::swap (R204Map &m) {
  R204Vector<R204Pair<K,T> > _data2 (_data);
  _data = m._data;
  m._data = _data2;
}

// ########################################
template <class K, class T>
R204Pair<K,T> *
R204Map<K, T>::find (const K &key) {
  for (size_t i (0); i < _data.size (); ++i)
    if (_data [i].key == key)
      return &_data [i];
  return nullptr;
}

template <class K, class T>
T &
R204Map<K, T>::operator[] (const K &key) {
  R204Pair<K,T> *pair = find (key);
  if (pair)
    return pair->value;
  _data.push_back (R204Pair<K,T> (key));
  return _data [_data.size ()-1].value;
}

template <class K, class T>
T &
R204Map<K, T>::at (const K &key) {
  R204Pair<K,T> *pair = find (key);
  if (pair)
    return pair->value;
  throw std::out_of_range ("R204Map: key not found");
}

// ========================================
template <class K, class T>
template <class F>
inline void
R204Map<K, T>::foreach (F f) {
  for (int i = 0; i < _data._size; ++i)
    f (_data [i].key, _data [i].value);
}

template <class K, class T>
template <class F>
inline void
R204Map<K, T>::foreach (const F f) const {
  for (int i = 0; i < _data._size; ++i)
    f (_data [i].key, _data [i].value);
}

// ########################################
#endif // _R204Map_hpp_
