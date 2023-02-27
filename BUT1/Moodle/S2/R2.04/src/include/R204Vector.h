/*!
 * @file R204Vector.h
 * @brief Définie la classe R204Vector
 * @author F. Merciol
 * @version 0.1
 * @date 13 février 2023
 */
#ifndef _R204Vector_hpp_
#define _R204Vector_hpp_

#include "R204Iterator.h"
// ========================================
/*!
 * @class R204Vector
 * @brief gère des tableaux dynamiques génériques pour Arduino
 */
template <class T>
class R204Vector  {
private:
  /*! taille réelle */
  size_t		_size;
  /*! taille préréservée */
  size_t		_capacity;
  /*! pointeur sur le premier éléments du tableau contenant les données (ou pointeur null) */
  T			*_data;

  /*! réserve au mopins une place suplémentaire */
  inline void		grow ();
  /*!
   * recopie les données dans un nouveau tableau de taille @p n
   * @param n (n doit être au moins égale à la taille réelle)
   */
  inline void		save (size_t n);
public:
  /*! abréviation d'écriture */
  typedef R204Iterator<T> iterator;
  /*! abréviation d'écriture */
  typedef R204ConstIterator<T> const_iterator;

  /*!
   * constructeur d'un vecteur de taille n initialisé avec le constructeur par défaut du type généric
   * @param n nombre d'éléments à la créetion.
   */
  inline R204Vector (const size_t &n = 0);
  /*!
   * constructeur d'un vecteur par clonage
   * @param v tableau de référence pour le clonage
   */
  inline R204Vector (const R204Vector<T> &v);
  /*! destructeur de vecteur libérant le tableau */
  inline ~R204Vector ();

  /*!
   * indique si le vecteur est vide
   * @return vrai si le tableau est vide (même si la capacité n'est pas nul)
  */
  inline bool		empty () const { return !_size;}
  /*!
   * indique la taille du tableau
   * @return le nombre d'éléments réllement utilisés
   */
  inline size_t		size () const { return _size; }

  /*!
   * recopie tous les éléments de @p v dans le vecteur
   * @param v tableau de référence pour le clonage
   * @return une référence sur this
   */
  inline R204Vector<T>	&operator= (const R204Vector<T> &v);
  /*!
   * permute le contenu de 2 tableaux.
   * C'est utile en particulier pour libérer la mémoire, car l'invocation de clear ne fait que mettre à zéro la taiile.
   * Pour vraiment lébérer la mémoire, il suffit de permutter avec une variable local qui va être immédiatement détruite.
   * @code
   *   R204Vector<int> monVecteur (20);
   *   monVecteur.push_back (2);
   *   monVecteur.swap (R204Vector ()); // supprime tous (réserve également)
   * @endcode
   * @param v autre tableau avec lequel les éléments seront échangés.
   */
  inline void		swap (R204Vector<T> &v);

  /*! ignore tous les éléments précédent (ne libère pas de mémoire) */
  inline void		clear () { _size = 0; }
  /*!
   *  fixe une nouvelle taille de vecteur.
   * Si @p n est inférieur à la taille actuelle, les éléments à partir de n seront masqués.
   * Si @p n est suppérieur à la taille actuelle, les éléments suppérieur qui ont été masqué réalparaient. Si @p n est supérieur à la réserve, de nouveau éléments sont initialisé avec e constructeur par défaut du type généric et les éléments inférieur sont recopiés dans un nouveau tableau.
   * @param n nouvelle taille
   */
  inline void		resize (const size_t &n) { reserve (n); size = n; }
  /*!
   * augment la capacité du tableau pour ateindre @p n (qui inclu les éléments visibles et la réserve).
   * @param n nouvelle taille utilisable du tableau sans avoir à recourir à une nouvelle demande de mémoire au système
   */
  inline void		reserve (const size_t &n);
  /*! libère la mémoire de la réserve pour se limiter aux cellules utilisées */
  inline void		shrink_to_fit ();

  /*!
   * accés directe au n-ième éléments (résultat non garanti en cas de débordement)
   * @param n index de l'élément recherché
   * @return une référence sur la valeur de l'élément qui peut ainsi être modifier
   * ne réalise aucun controle et ne retourne pas d'exception (comportement imprévisible en cas d'accès au-delà du dernier éléménts)
   */
  inline T		&operator[] (const size_t &n) const { return _data [n]; }
  /*!
   * accés directe au n-ième éléments (provoque une exception en cas de débordement)
   * @param n index de l'élément recherché
   * @return une référence sur la valeur de l'élément qui peut ainsi être modifier
   * @throws out_of_range dans le cas où l'index est suppérieur à la taille du tableau
   */
  inline T		&at (const size_t &n);

  /*!
   * place un élément en fin de tableau.
   * Si nécéssaire augmente la taille du tableau si plus aucune cellule n'est disponible en réserve.
   * @param val valeur à recopié en fin de tableau
   */
  inline void		push_back (const T &val);
  /*!
   * renvoie une référence sur le dernier élément
   * @return la référence sur le dernier élément.
   * ne réalise aucun controle et ne retourne pas d'exception (comportement imprévisible en cas de tableau vide)
   */
  inline T		&back () { return  _data [_size-1]; }
  /*!
   * même méthode appliquée sur un tableau de constantes
   * @return la référence constante sur le dernier élément.
   * ne réalise aucun controle et ne retourne pas d'exception (comportement imprévisible en cas de tableau vide)
   */
  inline const T	&back () const { return  _data [_size-1]; }
  /*!
   * supprime le dernier élément
   * ne réalise aucun controle et ne retourne pas d'exception (comportement imprévisible en cas de tableau vide)
  */
  inline void		pop_back () { --_size; }

  /*!
   * supprime la fin du tableau à partir de l'élément pos
   * @param pos position de l'élément à supprimer
   */
  inline void		erase (const iterator &pos);
  /*!
   * supprime les éléments du tableau entre @p first et @p last
   * @param first position du premier élément à supprimer
   * @param last position suivant le dernier éléménts à supprimer
   */
  inline void		erase (iterator first, const iterator &last);

  /*!
   * iterateur sur le premier élément
   * @return pointeur sur le premier élément du tableau
   */
  inline iterator	begin () { return iterator (&_data[0]); }
  /*!
   * iterateur sur le dernier élément pour marqueur de fin de boucle
   * @return pointeur suivant le dernier élément du tableau
   */
  inline iterator	end () { return iterator (&_data[size ()]); }
  /*!
   * même méthode appliquée sur tableau de constantes
   * @return pointeur sur le premier élément du tableau constant
   */
  inline const_iterator	cbegin () const { return const_iterator (&_data[0]); }
  /*!
   * même méthode appliquée sur un tableau de constantes
   * @return pointeur suivant le dernier élément du tableau constant
   */
  inline const_iterator	cend () const { return const_iterator (&_data[size ()]); }
  /*!
   * parcours du tableau avec une fonction passée en paramètre
   * @param f fonction lambda de type : void f (T v)
   */
  template <class F>
  inline void		foreach (F f);
  /*!
   * même méthode appliquée sur un tableau de constantes
   * @param f fonction lambda de type : void f (const T &v)
   */
  template <class F>
  inline void		foreach (const F f) const;
};

// ========================================
template <class T>
inline
R204Vector<T>::R204Vector (const size_t &n) :
  _size (0),
  _capacity (0),
  _data (nullptr) {
  reserve (n);
}

template <class T>
inline
R204Vector<T>::R204Vector (const R204Vector<T> &v) :
  R204Vector (0) {
  *this = v;
}

template <class T>
inline
R204Vector<T>::~R204Vector () {
  if (!_capacity)
    return;
  delete[] _data;
  _data = nullptr;
  _size = _capacity = 0;
}

// ========================================
template <class T>
inline R204Vector<T> &
R204Vector<T>::operator= (const R204Vector<T> &v) {
  reserve (v._size);
  _size = v._size;
  for (int i = 0; i < _size; ++i)
    _data [i] = v._data [i];
  return *this;
}

template <class T>
inline void
R204Vector<T>::swap (R204Vector<T> &v) {
  int _size2 (_size), _capacity2 (_capacity);
  T *_data2 (_data);
  _size = v._size;
  _capacity = v._capacity;
  _data = v._data;
  v._data = _data2;
  v._size = _size2;
  v._capacity = _capacity2;
}

// ========================================
template <class T>
inline void
R204Vector<T>::reserve (const size_t &n) {
  if (_capacity >= n)
    return;
  save (n);
}

template <class T>
inline void
R204Vector<T>::save (size_t n) {
  if (n < _size)
    n = _size;
  T *tmp = new T[n];
  if (_data) {
    for (int i = 0; i < _size; ++i)
      tmp [i] = _data [i];
    delete[] _data;
  }
  _data = tmp;
  _capacity = n;
}

template <class T>
inline void
R204Vector<T>::shrink_to_fit () {
  if (_capacity == _size)
    return;
  if (!_size) {
    delete[] _data;
    _capacity = 0;
    return;
  }
  save (_size);
}

// ========================================
template <class T>
T &
R204Vector<T>::at (const size_t &n) {
  if (n >= _size)
    throw std::out_of_range ("out of range "+std::to_string (n)+" >= "+std::to_string (_size));
  return _data [n];
}

// ========================================
template <class T>
void
R204Vector<T>::push_back (const T &val) {
  grow ();
  _data [_size] = val;
  ++_size;
}

template <class T>
void
R204Vector<T>::grow () {
  if (_capacity > _size)
    return;
  if (_capacity < 2) {
    reserve (8);
    return;
  }
  reserve (_capacity + _capacity/2);
}

// ========================================
template <class T>
inline void
R204Vector<T>::erase (const iterator &pos) {
  erase (pos, end ());
}

template <class T>
inline void
R204Vector<T>::erase (iterator first, const iterator &last) {
  int offset = last-first;
  for (iterator next = first+offset; next < end (); ++first, ++next)
    *first = *next;
  _size -= offset;
}

// ========================================
template <class T>
template <class F>
inline void
R204Vector<T>::foreach (F f) {
  for (int i = 0; i < _size; ++i)
    f (_data [i]);
}

template <class T>
template <class F>
inline void
R204Vector<T>::foreach (const F f) const {
  for (int i = 0; i < _size; ++i)
    f (_data [i]);
}

// ========================================
#endif // _R204Vector_hpp_
