<?php
    require_once 'SqliteConnection.php';
    require_once 'User.php';

    class UserDAO {
        private static ?UserDAO $dao = null;

        private function __construct() {}

        public static function getInstance(): UserDAO {
            if (!isset(self::$dao)) {
                self::$dao = new UserDAO();
            }
            return self::$dao;
        }

        public function findAll(): array {
            $dbc = SqliteConnection::getInstance();
            $query = "SELECT * FROM User";
            $stmt = $dbc -> query($query);
            return $stmt -> fetchALL(PDO::FETCH_CLASS, 'User');
        }

        public function findByEmail(string $email): ?User {
            $dbc = SqliteConnection::getInstance();
            $query = "SELECT * FROM User WHERE adresse_electronique = :adresse";
            $stmt = $dbc->prepare($query);
        
            $stmt->bindValue(':adresse', $email, PDO::PARAM_STR);
            $stmt->execute();
        
            // Set the fetch mode to populate an instance of the 'User' class
            $stmt->setFetchMode(PDO::FETCH_CLASS, 'User');
        
            // Fetch the user and return. If no user is found, this will return null.
            return $stmt->fetch();
        }    

        public function insert(User $user): void {
            $dbc = SqliteConnection::getInstance();
            $query = "INSERT INTO User(adresse_electronique, nom, prenom, date_naissance, sexe, taille, poids, mot_de_passe) VALUES (:adresse, :nom, :prenom, :date_naissance, :sexe, :taille, :poids, :mot_de_passe)";
            $stmt = $dbc -> prepare($query);

            $stmt -> bindValue(':adresse', $user -> getAdresseElectronique(), PDO::PARAM_STR);
            $stmt -> bindValue(':nom', $user -> getNom(), PDO::PARAM_STR);
            $stmt -> bindValue(':prenom', $user -> getPrenom(), PDO::PARAM_STR);
            $stmt -> bindValue(':date_naissance', $user -> getDateNaissance(), PDO::PARAM_STR);
            $stmt -> bindValue(':sexe', $user -> getSexe(), PDO::PARAM_INT);
            $stmt -> bindValue(':taille', $user -> getTaille(), PDO::PARAM_INT);
            $stmt -> bindValue(':poids', $user -> getPoids(), PDO::PARAM_INT);
            $stmt -> bindValue(':mot_de_passe', $user -> getMotDePasse(), PDO::PARAM_STR);

            $stmt -> execute();
        }

        public function delete(User $user): void {
            $dbc = SqliteConnection::getInstance();
            $query = "DELETE FROM User WHERE adresse_electronique = :adresse";
            $stmt = $dbc -> prepare($query);

            $stmt -> bindValue(':adresse', $user -> getAdresseElectronique(), PDO::PARAM_STR);

            $stmt -> execute();
        }

        public function update(User $user): void {
            $dbc = SqliteConnection::getInstance();
            $query = "UPDATE User SET nom = :nom, prenom = :prenom, date_naissance = :date_naissance, sexe = :sexe, taille = :taille, poids = :poids, mot_de_passe = :mot_de_passe WHERE adresse_electronique = :adresse";
            $stmt = $dbc -> prepare($query);

            $stmt -> bindValue(':adresse', $user -> getAdresseElectronique(), PDO::PARAM_STR);
            $stmt -> bindValue(':nom', $user -> getNom(), PDO::PARAM_STR);
            $stmt -> bindValue(':prenom', $user -> getPrenom(), PDO::PARAM_STR);
            $stmt -> bindValue(':date_naissance', $user -> getDateNaissance(), PDO::PARAM_STR);
            $stmt -> bindValue(':sexe', $user -> getSexe(), PDO::PARAM_INT);
            $stmt -> bindValue(':taille', $user -> getTaille(), PDO::PARAM_INT);
            $stmt -> bindValue(':poids', $user -> getPoids(), PDO::PARAM_INT);
            $stmt -> bindValue(':mot_de_passe', $user -> getMotDePasse(), PDO::PARAM_STR);

            $stmt -> execute();
        }
    }
?>
