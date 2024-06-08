const { MongoClient, Double } = require('mongodb');

async function initDB() {
  const uri = "mongodb://localhost:27017";
  const client = new MongoClient(uri);

  try {
    await client.connect();
    const db = client.db('magasin_sport');

    // Supprimer les collections si elles existent déjà pour éviter les conflits
    try {
      await db.collection('articles').drop();
    } catch (error) {
      console.log('Collection articles non trouvée, création prévue.');
    }
    
    try {
      await db.collection('rayons').drop();
    } catch (error) {
      console.log('Collection rayons non trouvée, création prévue.');
    }

    // Créer les collections avec les validations
    await db.createCollection("articles", {
      validator: {
        $jsonSchema: {
          bsonType: "object",
          required: ["marque", "prix", "nom", "reference", "categorie", "liste_de_tailles", "fournisseur", "identifiant_du_rayon"],
          properties: {
            marque: {
              bsonType: "string",
              minLength: 1,
              description: "doit être une chaîne et est requis"
            },
            prix: {
              bsonType: ["double", "int"],
              minimum: 0,
              description: "doit être un nombre positif et est requis"
            },
            nom: {
              bsonType: "string",
              minLength: 1,
              description: "doit être une chaîne et est requis"
            },
            reference: {
              bsonType: "string",
              minLength: 1,
              description: "doit être une chaîne et est unique et requis"
            },
            categorie: {
              enum: ["enfant", "junior", "senior", "loisir"],
              description: "doit être une valeur parmi les options prédéfinies et est requise"
            },
            liste_de_tailles: {
              bsonType: "array",
              minItems: 1,
              items: {
                bsonType: "object",
                required: ["taille", "quantite"],
                properties: {
                  taille: {
                    oneOf: [
                      {
                        bsonType: "int",
                        minimum: 1,
                        description: "doit être un entier supérieur à 0"
                      },
                      {
                        bsonType: "string",
                        minLength: 1,
                        description: "doit être une chaîne non vide pour des tailles comme 'XL', 'L', etc."
                      }
                    ],
                    description: "peut être un entier représentant la taille numérique de l'article ou une chaîne représentant la taille comme 'XL'"
                  },
                  quantite: {
                    bsonType: "int",
                    minimum: 0,
                    description: "doit être un entier non négatif indiquant la quantité disponible"
                  }
                }
              },
              description: "doit être un tableau de tailles et quantités"
            },
            fournisseur: {
              bsonType: "object",
              required: ["nom", "ville"],
              properties: {
                nom: {
                  bsonType: "string",
                  minLength: 1,
                  description: "doit être une chaîne et est requis"
                },
                ville: {
                  bsonType: "string",
                  minLength: 1,
                  description: "doit être une chaîne et est requis"
                }
              },
              description: "doit être un objet avec des informations sur le fournisseur"
            },
            identifiant_du_rayon: {
              bsonType: "string",
              minLength: 1,
              description: "doit être une chaîne et est requis pour identifier le rayon de l'article"
            }
          }
        }
      },
      validationLevel: "strict",
      validationAction: "error"
    });

    console.log("Collection articles créée");

    await db.createCollection("rayons", {
      validator: {
        $jsonSchema: {
          bsonType: "object",
          required: ["_id", "description", "employe_responsable"],
          properties: {
            _id: {
              bsonType: "string",
              description: "doit être une chaîne et est l'ID du rayon"
            },
            description: {
              bsonType: "string"
            },
            employe_responsable: {
              bsonType: "string"
            }
          }
        }
      }
    });

    console.log("Collection rayons créée");

    // Insérer les rayons
    const rayons = [
      { _id: "rayon01", description: "Chaussures", employe_responsable: "Jean Dupont" },
      { _id: "rayon02", description: "Vêtements", employe_responsable: "Marie Durand" },
      { _id: "rayon03", description: "Équipements", employe_responsable: "Pierre Martin" },
    ];
    await db.collection('rayons').insertMany(rayons);

    console.log("Rayons insérés");

    // Insérer les articles
    const articles = [
      {
        marque: "Nike",
        prix: new Double(120.0),
        nom: "Air Max",
        reference: "NK1234AM",
        categorie: "senior",
        liste_de_tailles: [
          { taille: 42, quantite: 10 },
          { taille: 43, quantite: 5 }
        ],
        fournisseur: { nom: "Sports Goods Co.", ville: "Paris" },
        identifiant_du_rayon: "rayon01"
      },
      {
        marque: "Adidas",
        prix: new Double(100.0),
        nom: "Ultraboost",
        reference: "AD5678UB",
        categorie: "junior",
        liste_de_tailles: [
          { taille: 40, quantite: 8 },
          { taille: 41, quantite: 6 }
        ],
        fournisseur: { nom: "Fitness Supplies Ltd.", ville: "Lyon" },
        identifiant_du_rayon: "rayon01"
      },
      {
        marque: "Puma",
        prix: new Double(90.0),
        nom: "Ignite",
        reference: "PM9101IG",
        categorie: "senior",
        liste_de_tailles: [
          { taille: 44, quantite: 4 },
          { taille: 45, quantite: 3 }
        ],
        fournisseur: { nom: "Athletic Gear Inc.", ville: "Marseille" },
        identifiant_du_rayon: "rayon01"
      },
      {
        marque: "Under Armour",
        prix: new Double(75.0),
        nom: "HOVR",
        reference: "UA1112HV",
        categorie: "loisir",
        liste_de_tailles: [
          { taille: 38, quantite: 7 },
          { taille: 39, quantite: 6 }
        ],
        fournisseur: { nom: "Sports World", ville: "Nice" },
        identifiant_du_rayon: "rayon02"
      },
      {
        marque: "Reebok",
        prix: new Double(85.0),
        nom: "Nano",
        reference: "RB1314NN",
        categorie: "senior",
        liste_de_tailles: [
          { taille: 42, quantite: 5 },
          { taille: 43, quantite: 5 }
        ],
        fournisseur: { nom: "Active Wear Co.", ville: "Bordeaux" },
        identifiant_du_rayon: "rayon02"
      },
      {
        marque: "Asics",
        prix: new Double(95.0),
        nom: "Gel-Kayano",
        reference: "AS1516GK",
        categorie: "enfant",
        liste_de_tailles: [
          { taille: 28, quantite: 10 },
          { taille: 29, quantite: 8 }
        ],
        fournisseur: { nom: "Run Fast Ltd.", ville: "Lille" },
        identifiant_du_rayon: "rayon03"
      },
      {
        marque: "New Balance",
        prix: new Double(110.0),
        nom: "Fresh Foam",
        reference: "NB1718FF",
        categorie: "junior",
        liste_de_tailles: [
          { taille: 40, quantite: 5 },
          { taille: 41, quantite: 5 }
        ],
        fournisseur: { nom: "Urban Sports", ville: "Nantes" },
        identifiant_du_rayon: "rayon03"
      },
      {
        marque: "Nike",
        prix: new Double(130.0),
        nom: "Air Force",
        reference: "NK1920AF",
        categorie: "senior",
        liste_de_tailles: [
          { taille: 42, quantite: 7 },
          { taille: 44, quantite: 3 }
        ],
        fournisseur: { nom: "Sports Goods Co.", ville: "Paris" },
        identifiant_du_rayon: "rayon01"
      },
      {
        marque: "Adidas",
        prix: new Double(110.0),
        nom: "Superstar",
        reference: "AD2122SS",
        categorie: "loisir",
        liste_de_tailles: [
          { taille: 39, quantite: 6 },
          { taille: 40, quantite: 4 }
        ],
        fournisseur: { nom: "Fitness Supplies Ltd.", ville: "Lyon" },
        identifiant_du_rayon: "rayon02"
      },
      {
        marque: "Puma",
        prix: new Double(100.0),
        nom: "Suede Classic",
        reference: "PM2324SC",
        categorie: "senior",
        liste_de_tailles: [
          { taille: 42, quantite: 4 },
          { taille: 43, quantite: 4 }
        ],
        fournisseur: { nom: "Athletic Gear Inc.", ville: "Marseille" },
        identifiant_du_rayon: "rayon01"
      }
    ];

    await db.collection('articles').insertMany(articles);

    console.log("Articles insérés");

    console.log('Base de données initialisée avec succès !');
  } catch (err) {
    console.error('Erreur lors de l\'initialisation de la base de données:', JSON.stringify(err, null, 4));
  } finally {
    await client.close();
  }
}

initDB();
