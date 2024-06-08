import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [articles, setArticles] = useState([]);
  const [newArticle, setNewArticle] = useState({
    marque: '', prix: 0, nom: '', reference: '', categorie: '', liste_de_tailles: [], fournisseur: { nom: '', ville: '' }, identifiant_du_rayon: ''
  });
  const [errors, setErrors] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:3001/articles')
      .then(response => {
        setArticles(response.data);
      });
  }, []);

  const validateForm = () => {
    const { marque, prix, nom, reference, categorie, liste_de_tailles, fournisseur, identifiant_du_rayon } = newArticle;
    if (!marque || !nom || !reference || !fournisseur.nom || !fournisseur.ville || !identifiant_du_rayon) {
      return false;
    }
    if (prix <= 0) {
      return false;
    }
    if (!categorie) {
      return false;
    }
    if (liste_de_tailles.length === 0 || liste_de_tailles.some(taille => (typeof taille.taille === 'number' ? (taille.taille <= 0) : taille.taille.trim() === '') || !taille.quantite || taille.quantite < 0)) {
      return false;
    }
    return true;
  };

  const handleTailleChange = (e, index) => {
    const newTaille = [...newArticle.liste_de_tailles];
    const value = e.target.value;
    if (!isNaN(value) && parseInt(value) > 0) {
      newTaille[index].taille = parseInt(value);
    } else if (value.trim() !== '') {
      newTaille[index].taille = value;
    }
    setNewArticle({ ...newArticle, liste_de_tailles: newTaille });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    axios.post('http://localhost:3001/articles', newArticle)
      .then(response => {
        setArticles([...articles, response.data]);
        setNewArticle({ marque: '', prix: 0, nom: '', reference: '', categorie: '', liste_de_tailles: [], fournisseur: { nom: '', ville: '' }, identifiant_du_rayon: '' });
        setErrors(null);
      })
      .catch(error => {
        setErrors(error.response.data);
      });
  };

  return (
    <div className="App">
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'row', alignContent: 'center', flexWrap: 'wrap' }}>
        <img src="/magasin_sport.png" alt="magasin_sport" width="100px" height="100px"/><br></br>
        <h1 style={{ marginLeft: '20px' }}>Magasin de sport</h1>
      </div>
      <form onSubmit={handleSubmit}>
        <label>Marque</label>
        <input type="text" value={newArticle.marque} onChange={e => setNewArticle({ ...newArticle, marque: e.target.value })} className={!newArticle.marque && errors ? "input-error" : ""} />
        
        <label>Prix</label>
        <input type="number" value={newArticle.prix} onChange={e => setNewArticle(prevArticle => ({ ...prevArticle, prix: e.target.value }))} className={newArticle.prix <= 0 && errors ? "input-error" : ""} />
        
        <label>Nom</label>
        <input type="text" value={newArticle.nom} onChange={e => setNewArticle({ ...newArticle, nom: e.target.value })} className={!newArticle.nom && errors ? "input-error" : ""} />
        
        <label>Référence</label>
        <input type="text" value={newArticle.reference} onChange={e => setNewArticle({ ...newArticle, reference: e.target.value })} className={!newArticle.reference && errors ? "input-error" : ""} />
        
        <label>Catégorie</label>
        <select value={newArticle.categorie} onChange={e => setNewArticle({ ...newArticle, categorie: e.target.value })} className={!newArticle.categorie && errors ? "input-error" : ""}>
          <option value="">Choisir une catégorie</option>
          <option value="enfant">Enfant</option>
          <option value="junior">Junior</option>
          <option value="senior">Senior</option>
          <option value="loisir">Loisir</option>
        </select>
        
        <div>
          <h3>Tailles</h3>
          {newArticle.liste_de_tailles.map((taille, index) => (
            <div key={index} className="taille-container">
              <label>Taille {index + 1}</label>
              <input type="text" value={taille.taille} onChange={(e) => handleTailleChange(e, index)} className={(typeof taille.taille === 'number' ? (taille.taille <= 0) : taille.taille.trim() === '') && errors ? "input-error" : ""} placeholder="Taille (ex : 28, 46, XL)" />
              
              <label>Quantité</label>
              <input type="number" value={taille.quantite} min="0" onChange={e => {
                const newTaille = [...newArticle.liste_de_tailles];
                newTaille[index].quantite = parseInt(e.target.value);
                setNewArticle({ ...newArticle, liste_de_tailles: newTaille });
              }} className={taille.quantite < 0 && errors ? "input-error" : ""} placeholder="Quantité" />
              
              <button type="button" onClick={() => {
                const newTaille = newArticle.liste_de_tailles.filter((_, i) => i !== index);
                setNewArticle({ ...newArticle, liste_de_tailles: newTaille });
              }}>Supprimer</button>
            </div>
          ))}
          <button type="button" onClick={() => setNewArticle({ ...newArticle, liste_de_tailles: [...newArticle.liste_de_tailles, { taille: '', quantite: '' }] })}>Ajouter une taille</button>
        </div>
        
        <label>Fournisseur Nom</label>
        <input type="text" value={newArticle.fournisseur.nom} onChange={e => setNewArticle({ ...newArticle, fournisseur: { ...newArticle.fournisseur, nom: e.target.value } })} className={!newArticle.fournisseur.nom && errors ? "input-error" : ""} />
        
        <label>Fournisseur Ville</label>
        <input type="text" value={newArticle.fournisseur.ville} onChange={e => setNewArticle({ ...newArticle, fournisseur: { ...newArticle.fournisseur, ville: e.target.value } })} className={!newArticle.fournisseur.ville && errors ? "input-error" : ""} />
        
        <label>ID du Rayon</label>
        <input type="text" value={newArticle.identifiant_du_rayon} onChange={e => setNewArticle({ ...newArticle, identifiant_du_rayon: e.target.value })} className={!newArticle.identifiant_du_rayon && errors ? "input-error" : ""} />
        
        <button type="submit" disabled={!validateForm()}>Ajouter l'article</button>
      </form>
      {errors && <div className="error">{JSON.stringify(errors)}</div>}
      
      <table>
        <thead>
          <tr>
            <th>Marque</th>
            <th>Prix</th>
            <th>Nom</th>
            <th>Référence</th>
            <th>Catégorie</th>
            <th>Tailles</th>
            <th>Fournisseur</th>
            <th>ID du Rayon</th>
          </tr>
        </thead>
        <tbody>
          {articles.map(article => (
            <tr key={article._id}>
              <td>{article.marque}</td>
              <td>{article.prix}</td>
              <td>{article.nom}</td>
              <td>{article.reference}</td>
              <td>{article.categorie}</td>
              <td>{article.liste_de_tailles.map(taille => `${taille.taille} (${taille.quantite})`).join(', ')}</td>
              <td>{article.fournisseur.nom} ({article.fournisseur.ville})</td>
              <td>{article.identifiant_du_rayon}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App;
