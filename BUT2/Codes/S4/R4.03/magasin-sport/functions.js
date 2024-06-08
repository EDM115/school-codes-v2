const { MongoClient, Double } = require('mongodb');

const uri = "mongodb://localhost:27017";
let client;

async function connect() {
  if (!client) {
    client = new MongoClient(uri);
    await client.connect();
  }
  return client.db('magasin_sport');
}

module.exports = {
  insertArticle: async function (context, events, done) {
    try {
      const db = await connect();
      const articles = db.collection('articles');

      const article = {
        marque: "Test",
        prix: new Double(Math.random() * 200),
        nom: "Article Test",
        reference: `TEST${Math.floor(Math.random() * 1000000)}`,
        categorie: "senior",
        liste_de_tailles: [
          { taille: 42, quantite: Math.floor(Math.random() * 100) },
          { taille: 43, quantite: Math.floor(Math.random() * 100) }
        ],
        fournisseur: { nom: "Test Supplier", ville: "Test City" },
        identifiant_du_rayon: "rayon01"
      };

      await articles.insertOne(article);
    } catch (error) {
      console.error('Erreur lors de l\'insertion de l\'article:', error);
    }
  },

  insertRayon: async function (context, events, done) {
    try {
      const db = await connect();
      const rayons = db.collection('rayons');

      const rayon = {
        _id: `rayon${Math.floor(Math.random() * 1000000)}`,
        description: "Rayon Test",
        employe_responsable: "Test Employee"
      };

      await rayons.insertOne(rayon);
    } catch (error) {
      console.error('Erreur lors de l\'insertion du rayon:', error);
    }
  },

  readArticle: async function (context, events, done) {
    try {
      const db = await connect();
      const articles = db.collection('articles');

      await articles.findOne({});
    } catch (error) {
      console.error('Erreur lors de la lecture de l\'article:', error);
    }
  },

  readRayon: async function (context, events, done) {
    try {
      const db = await connect();
      const rayons = db.collection('rayons');

      await rayons.findOne({});
    } catch (error) {
      console.error('Erreur lors de la lecture du rayon:', error);
    }
  }
};
