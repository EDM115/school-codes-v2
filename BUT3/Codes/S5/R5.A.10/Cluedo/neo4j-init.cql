// Créer la base de données cluedo si elle n'existe pas (à utiliser ligne par ligne)
// :use system
// CREATE DATABASE cluedo IF NOT EXISTS;
// :use cluedo

// Suppression des anciens noeuds et relations (pour éviter les doublons si on relance le script)
MATCH (n)
DETACH DELETE n;

// Création des 8 pièces
CREATE (salle_serveurs:Piece {nom: "Salle des serveurs"})
CREATE (salle_tp:Piece {nom: "Salle de TP"})
CREATE (amphi:Piece {nom: "Amphithéâtre"})
CREATE (salle_reunion:Piece {nom: "Salle de réunion"})
CREATE (cafeteria:Piece {nom: "Cafétéria"})
CREATE (toilettes:Piece {nom: "Toilettes"})
CREATE (salle_profs:Piece {nom: "Salle des profs"})
CREATE (backrooms:Piece {nom: "Backrooms"})

// Création des 8 armes
CREATE (cable_ethernet:Arme {nom: "Câble ethernet"})
CREATE (souris_rgb:Arme {nom: "Souris RGB"})
CREATE (clavier_mechanique:Arme {nom: "Clavier mécanique"})
CREATE (routeur_defectueux:Arme {nom: "Routeur défectueux"})
CREATE (bouteille_cafe:Arme {nom: "Bouteille de café froid"})
CREATE (chaise_bureau:Arme {nom: "Chaise de bureau"})
CREATE (livre_java:Arme {nom: "Gros livre de Java"})
CREATE (usb_explosif:Arme {nom: "Clé USB explosive"})

// Création des 8 personnages
CREATE (le_prof:Personnage {nom: "Le prof"})
CREATE (etudiant_stress:Personnage {nom: "Étudiant stressé"})
CREATE (etudiant_cafe:Personnage {nom: "Étudiant boosté au café"})
CREATE (technicien_reseau:Personnage {nom: "Technicien réseau"})
CREATE (directeur_iut:Personnage {nom: "Directeur de l'IUT"})
CREATE (etudiant_sommeil:Personnage {nom: "Étudiant privé de sommeil"})
CREATE (secretaire_etude:Personnage {nom: "Secrétaire"})
CREATE (etudiant_redoublant:Personnage {nom: "Étudiant redoublant"})

// Relations entre personnages et pièces où ils traînent souvent
WITH *
MATCH (le_prof:Personnage {nom: "Le prof"}), (salle_profs:Piece {nom: "Salle des profs"})
CREATE (le_prof)-[:FREQUENTE]->(salle_profs);

WITH *
MATCH (etudiant_stress:Personnage {nom: "Étudiant stressé"}), (salle_serveurs:Piece {nom: "Salle des serveurs"})
CREATE (etudiant_stress)-[:FREQUENTE]->(salle_serveurs);

WITH *
MATCH (etudiant_cafe:Personnage {nom: "Étudiant boosté au café"}), (salle_tp:Piece {nom: "Salle de TP"})
CREATE (etudiant_cafe)-[:FREQUENTE]->(salle_tp);

WITH *
MATCH (technicien_reseau:Personnage {nom: "Technicien réseau"}), (backrooms:Piece {nom: "Backrooms"})
CREATE (technicien_reseau)-[:FREQUENTE]->(backrooms);

WITH *
MATCH (directeur_iut:Personnage {nom: "Directeur de l'IUT"}), (salle_reunion:Piece {nom: "Salle de réunion"})
CREATE (directeur_iut)-[:FREQUENTE]->(salle_reunion);

WITH *
MATCH (etudiant_sommeil:Personnage {nom: "Étudiant privé de sommeil"}), (amphi:Piece {nom: "Amphithéâtre"})
CREATE (etudiant_sommeil)-[:FREQUENTE]->(amphi);

WITH *
MATCH (secretaire_etude:Personnage {nom: "Secrétaire"}), (cafeteria:Piece {nom: "Cafétéria"})
CREATE (secretaire_etude)-[:FREQUENTE]->(cafeteria);

WITH *
MATCH (etudiant_redoublant:Personnage {nom: "Étudiant redoublant"}), (toilettes:Piece {nom: "Toilettes"})
CREATE (etudiant_redoublant)-[:FREQUENTE]->(toilettes);

// Relations entre personnages et armes qu'ils peuvent utiliser
WITH *
MATCH (le_prof:Personnage {nom: "Le prof"}), (livre_java:Arme {nom: "Gros livre de Java"})
CREATE (le_prof)-[:UTILISE]->(livre_java);

WITH *
MATCH (etudiant_stress:Personnage {nom: "Étudiant stressé"}), (usb_explosif:Arme {nom: "Clé USB explosive"})
CREATE (etudiant_stress)-[:UTILISE]->(usb_explosif);

WITH *
MATCH (etudiant_cafe:Personnage {nom: "Étudiant boosté au café"}), (clavier_mechanique:Arme {nom: "Clavier mécanique"})
CREATE (etudiant_cafe)-[:UTILISE]->(clavier_mechanique);

WITH *
MATCH (technicien_reseau:Personnage {nom: "Technicien réseau"}), (routeur_defectueux:Arme {nom: "Routeur défectueux"})
CREATE (technicien_reseau)-[:UTILISE]->(routeur_defectueux);

WITH *
MATCH (directeur_iut:Personnage {nom: "Directeur de l'IUT"}), (chaise_bureau:Arme {nom: "Chaise de bureau"})
CREATE (directeur_iut)-[:UTILISE]->(chaise_bureau);

WITH *
MATCH (etudiant_sommeil:Personnage {nom: "Étudiant privé de sommeil"}), (bouteille_cafe:Arme {nom: "Bouteille de café froid"})
CREATE (etudiant_sommeil)-[:UTILISE]->(bouteille_cafe);

WITH *
MATCH (secretaire_etude:Personnage {nom: "Secrétaire"}), (souris_rgb:Arme {nom: "Souris RGB"})
CREATE (secretaire_etude)-[:UTILISE]->(souris_rgb);

WITH *
MATCH (etudiant_redoublant:Personnage {nom: "Étudiant redoublant"}), (cable_ethernet:Arme {nom: "Câble ethernet"})
CREATE (etudiant_redoublant)-[:UTILISE]->(cable_ethernet);

// Relations de traversée entre les pièces (optionnel)
WITH *
MATCH (salle_serveurs:Piece {nom: "Salle des serveurs"}), (salle_tp:Piece {nom: "Salle de TP"})
CREATE (salle_serveurs)-[:ADJACENT_A]->(salle_tp);

WITH *
MATCH (salle_tp:Piece {nom: "Salle de TP"}), (amphi:Piece {nom: "Amphithéâtre"})
CREATE (salle_tp)-[:ADJACENT_A]->(amphi);

WITH *
MATCH (amphi:Piece {nom: "Amphithéâtre"}), (salle_reunion:Piece {nom: "Salle de réunion"})
CREATE (amphi)-[:ADJACENT_A]->(salle_reunion);

WITH *
MATCH (salle_reunion:Piece {nom: "Salle de réunion"}), (cafeteria:Piece {nom: "Cafétéria"})
CREATE (salle_reunion)-[:ADJACENT_A]->(cafeteria);

WITH *
MATCH (cafeteria:Piece {nom: "Cafétéria"}), (toilettes:Piece {nom: "Toilettes"})
CREATE (cafeteria)-[:ADJACENT_A]->(toilettes);

WITH *
MATCH (toilettes:Piece {nom: "Toilettes"}), (salle_profs:Piece {nom: "Salle des profs"})
CREATE (toilettes)-[:ADJACENT_A]->(salle_profs);

WITH *
MATCH (salle_profs:Piece {nom: "Salle des profs"}), (backrooms:Piece {nom: "Backrooms"})
CREATE (salle_profs)-[:ADJACENT_A]->(backrooms);

WITH *
MATCH (backrooms:Piece {nom: "Backrooms"}), (salle_serveurs:Piece {nom: "Salle des serveurs"})
CREATE (backrooms)-[:ADJACENT_A]->(salle_serveurs);
