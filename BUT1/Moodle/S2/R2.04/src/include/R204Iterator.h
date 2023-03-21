/*!
 * @file R204Iterator.h
 * @brief Définie les classes R204Iterator et R204ConstIterator
 * @author F. Merciol
 * @version 0.1
 * @date 13 février 2023
 */
#ifndef _R204Iterator_hpp_
#define _R204Iterator_hpp_

// ########################################
/*!
 * @class R204Iterator
 * @brief classe d'itérateurs génériques pour Arduino utilisé par les tableaux (R204Vector) et tables associatives (R204Map).
 */
template <class T>
class R204Iterator {
private:
  /*! pointeur sur l'élément courant */
  T			*ptr;
public:
  /*!
   * Pour pouvoir déclarer un itérateur avant de connaître sa valeur.
   * @return un itérateur inutilisable
   */
  R204Iterator () : ptr (nullptr) {}
  /*!
   * Constructeur d'un itérateur avec un pointeur
   * @param ptr pointeur de référence pour l'initialisation
   */
  R204Iterator (T *ptr) : ptr (ptr) {}
  /*!
   * Constructeur d'un itérateur par clonage
   * @param it itérateur de référence pour le clonage
   */
  R204Iterator (const R204Iterator &it) : ptr (it.ptr) {}

  /*!
   * Recopie le pointeur de @p it
   * @param it itérateur de référence pour la copie
   * @return une référence sur this
   */
  const R204Iterator	&operator= (R204Iterator &it) { ptr = it.ptr; return *this; }

  /*!
   * Compare deux itérateurs (offre la comparaison entre un itérateur et un pointeur grace au constructeur)
   * @param a itérateur ou pointeur
   * @param b itérateur ou pointeur
   * @return vrai si les 2 pointeurs sont égaux.
   */
  friend bool		operator== (const R204Iterator &a, const R204Iterator &b) { return a.ptr == b.ptr; }
  /*!
   * Compare deux itérateurs (offre la comparaison entre un itérateur et un pointeur grace au constructeur)
   * @param a itérateur ou pointeur
   * @param b itérateur ou pointeur
   * @return vrai si les 2 pointeurs sont différent.
   */
  friend bool		operator!= (const R204Iterator &a, const R204Iterator &b) { return a.ptr != b.ptr; }
  /*!
   * Compare deux itérateurs (offre la comparaison entre un itérateur et un pointeur grace au constructeur)
   * @param a itérateur ou pointeur
   * @param b itérateur ou pointeur
   * @return vrai si a est avant b
   */
  friend bool		operator< (const R204Iterator &a, const R204Iterator &b) { return a.ptr < b.ptr; }
  /*!
   * Compare deux itérateurs (offre la comparaison entre un itérateur et un pointeur grace au constructeur)
   * @param a itérateur ou pointeur
   * @param b itérateur ou pointeur
   * @return vrai si a est après b
   */
  friend bool		operator> (const R204Iterator &a, const R204Iterator &b) { return a.ptr > b.ptr; }

  /*!
   * Décalage vers l'avant d'un pointeur
   * @param it l'iterateur concerné
   * @param offset nombre d'éléments qu'il faut survoller pour déplacer le pointeur.
   * @return nouvel itérateur à la nouvelle position
   */
  friend R204Iterator<T> operator+ (R204Iterator<T> it, int offset) { it.ptr += offset;  return it; }
  /*!
   * Décalage vers l'avant d'un pointeur
   * @param offset nombre d'éléments qu'il faut survoller pour déplacer le pointeur.
   * @param it l'iterateur concerné
   * @return nouvel itérateur à la nouvelle position
   */
  friend R204Iterator<T> operator+ (int offset, R204Iterator<T> it) { it.ptr += offset;  return it; }
  /*!
   * Décalage vers l'arrière du pointeur
   * @param it l'iterateur concerné
   * @param offset nombre d'éléments qu'il faut survoller pour déplacer le pointeur.
   * @return nouvel itérateur à la nouvelle position
   */
  friend R204Iterator<T> operator- (R204Iterator<T> it, int offset) { it.ptr -= offset;  return it; }
  /*!
   * Distance entre deux poineturs
   * @param a iterateur (habituellement le dernier)
   * @param b iterateur (habituellement le premier)
   * @return le nombre d'éléments qui sépare les 2 iterateurs. La valeur est positive si a est plus grand que b.
   */
  friend int operator- (R204Iterator<T> a, R204Iterator<T> b) { return a.ptr - b.ptr; }

  /*!
   * pré-incrément. Décale l'itérateur vers l'avant avant de retourner l'itérateur.
   * @return une référence sur this
   */
  R204Iterator		&operator++ () { ++ptr; return *this; }
  /*!
   * post-incrément. Décale l'itérateur vers l'avant mais retourne un itérateur sur la position initiale.
   * @return un nouvel itérateur sur la position initiale.
   */
  R204Iterator		operator++ (int) { R204Iterator result (*this); ++ptr; return result; }
  /*!
   * déréférencement du pointeur de l'itérateur pour accéder à l'élément pointé.
   * @return une référence sur l'élément pointé.
   */
  T			&operator* () const { return *ptr; }
  /*!
   * Déréférencement du pointeur de l'itérateur pour accéder aux champs d'une structure pointé ou aux membres d'un objet pointé.
   * @return champs ou membre pointé.
   */
  T			*operator-> () const { return ptr; }
};

// ########################################
/*!
 * @class R204ConstIterator
 * @brief classe d'itérateurs génériques pour Arduino utilisé pour des constantes vers des tableaux (R204Vector) et des tables associatives (R204Map).
 */
template <class T>
class R204ConstIterator {
private:
  /*! pointeur sur l'élément constant courant */
  const T			*ptr;
public:
  /*!
   * Pour pouvoir déclarer un itérateur avant de connaître sa valeur.
   * @return un itérateur inutilisable
   */
  R204ConstIterator () : ptr (nullptr) {}
  /*!
   * Constructeur d'un itérateur avec un pointeur vers une constante
   * @param ptr pointeur de référence pour l'initialisation
   */
  R204ConstIterator (const T *ptr) : ptr (ptr) {}
  /*!
   * Constructeur d'un itérateur par clonage
   * @param it itérateur de référence pour le clonage
   */
  R204ConstIterator (const R204ConstIterator &it) : ptr (it.ptr) {}
  /*!
   * Constructeur d'un itérateur par clonage
   * @param it itérateur de référence pour le clonage
   */
  R204ConstIterator (const R204Iterator<T> &it) : ptr (it.ptr) {}

  /*!
   * Compare deux itérateurs (offre la comparaison entre un itérateur et un pointeur grace au constructeur)
   * @param a itérateur ou pointeur
   * @param b itérateur ou pointeur
   * @return vrai si les 2 pointeurs sont égaux.
   */
  friend bool		operator== (const R204ConstIterator &a, const R204ConstIterator &b) { return a.ptr == b.ptr; }
  /*!
   * Compare deux itérateurs (offre la comparaison entre un itérateur et un pointeur grace au constructeur)
   * @param a itérateur ou pointeur
   * @param b itérateur ou pointeur
   * @return vrai si les 2 pointeurs sont différent.
   */
  friend bool		operator!= (const R204ConstIterator &a, const R204ConstIterator &b) { return a.ptr != b.ptr; }
  /*!
   * Compare deux itérateurs (offre la comparaison entre un itérateur et un pointeur grace au constructeur)
   * @param a itérateur ou pointeur
   * @param b itérateur ou pointeur
   * @return vrai si a est avant b
   */
  friend bool		operator< (const R204ConstIterator &a, const R204ConstIterator &b) { return a.ptr < b.ptr; }
  /*!
   * Compare deux itérateurs (offre la comparaison entre un itérateur et un pointeur grace au constructeur)
   * @param a itérateur ou pointeur
   * @param b itérateur ou pointeur
   * @return vrai si a est après b
   */
  friend bool		operator> (const R204ConstIterator &a, const R204ConstIterator &b) { return a.ptr > b.ptr; }

  /*!
   * Décalage vers l'avant d'un pointeur
   * @param it l'iterateur concerné
   * @param offset nombre d'éléments qu'il faut survoller pour déplacer le pointeur.
   * @return nouvel itérateur à la nouvelle position
   */
  friend R204ConstIterator<T> operator+ (R204ConstIterator<T> it, int offset) { it.ptr += offset;  return it; }
  /*!
   * Décalage vers l'avant d'un pointeur
   * @param offset nombre d'éléments qu'il faut survoller pour déplacer le pointeur.
   * @param it l'iterateur concerné
   * @return nouvel itérateur à la nouvelle position
   */
  friend R204ConstIterator<T> operator+ (int offset, R204ConstIterator<T> it) { it.ptr += offset;  return it; }
  /*!
   * Décalage vers l'arrière du pointeur
   * @param it l'iterateur concerné
   * @param offset nombre d'éléments qu'il faut survoller pour déplacer le pointeur.
   * @return nouvel itérateur à la nouvelle position
   */
  friend R204ConstIterator<T> operator- (R204ConstIterator<T> it, int offset) { it.ptr -= offset;  return it; }
  /*!
   * Distance entre deux poineturs
   * @param a iterateur (habituellement le dernier)
   * @param b iterateur (habituellement le premier)
   * @return le nombre d'éléments qui sépare les 2 iterateurs. La valeur est positive si a est plus grand que b.
   */
  friend int operator- (R204ConstIterator<T> a, R204ConstIterator<T> b) { return a.ptr - b.ptr; }

  /*!
   * pré-incrément. Décale l'itérateur vers l'avant avant de retourner l'itérateur.
   * @return une référence sur this
   */
  R204ConstIterator		&operator++ () { ++ptr; return *this; }
  /*!
   * post-incrément. Décale l'itérateur vers l'avant mais retourne un itérateur sur la position initiale.
   * @return un nouvel itérateur sur la position initiale.
   */
  R204ConstIterator		operator++ (int) { R204ConstIterator result (*this); ++ptr; return result; }
  /*!
   * déréférencement du pointeur de l'itérateur pour accéder à l'élément pointé.
   * @return une référence sur l'élément pointé.
   */
  const T			&operator* () const { return *ptr; }
  /*!
   * Déréférencement du pointeur de l'itérateur pour accéder aux champs d'une structure pointé ou aux membres d'un objet pointé.
   * @return champs ou membre pointé.
   */
  const T			*operator-> () const { return ptr; }
};

// ########################################
#endif // _R204Iterator_hpp_
