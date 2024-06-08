const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
app.use(bodyParser.json());
app.use(cors());

mongoose.connect('mongodb://localhost:27017/magasin_sport');

const Article = mongoose.model('Article', new mongoose.Schema({
  marque: String,
  prix: Number,
  nom: String,
  reference: String,
  categorie: String,
  "liste_de_tailles": [{
    taille: { type: mongoose.Schema.Types.Mixed, required: true },
    quantite: Number
  }],
  fournisseur: { nom: String, ville: String },
  "identifiant_du_rayon": String
}));

app.get('/articles', async (req, res) => {
  const articles = await Article.find();
  res.send(articles);
});

app.post('/articles', async (req, res) => {
  try {
    const article = new Article(req.body);
    await article.save();
    res.send(article);
  } catch (error) {
    console.error(JSON.stringify(error, null, 2));
    res.status(400).send(error);
  }
});

app.listen(3001, () => {
  console.log('Server is running on port 3001');
});
