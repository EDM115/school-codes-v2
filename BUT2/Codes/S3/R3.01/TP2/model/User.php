<?php
	class User {
		private string $adresse_electronique;
		private string $nom;
		private string $prenom;
		private string $date_naissance;
		private int $sexe;
		private int $taille;
		private int $poids;
		private string $mot_de_passe;

		public function __construct() { }

		public function init($adresse_electronique, $nom, $prenom, $date_naissance, $sexe, $taille, $poids, $mot_de_passe) {
			if (strlen($adresse_electronique) < 5 || !filter_var($adresse_electronique, FILTER_VALIDATE_EMAIL) || $adresse_electronique == null) {
				throw new Exception("User.init() : Adresse électronique invalide");
			}
			$this -> adresse_electronique = $adresse_electronique;
			if ($nom == null || strlen($nom) > 40) {
				throw new Exception("User.init() : Nom invalide");
			}
			$this -> nom = $nom;
			if ($prenom == null || strlen($prenom) > 40) {
				throw new Exception("User.init() : Prénom invalide");
			}
			$this -> prenom = $prenom;
			if ($date_naissance == null || !preg_match('/^([0-9]{4})-([0-9]{2})-([0-9]{2})$/', $date_naissance) || $date_naissance < '1900-01-01' || $date_naissance > date("Y-m-d")) {
				throw new Exception("User.init() : Date de naissance invalide");
			}
			$this -> date_naissance = $date_naissance;
			if ($sexe == null || $sexe < 0 || $sexe > 1) {
				throw new Exception("User.init() : Sexe invalide");
			}
			$this -> sexe = $sexe;
			if ($taille == null || $taille < 10 || $taille > 250) {
				throw new Exception("User.init() : Taille invalide");
			}
			$this -> taille = $taille;
			if ($poids == null || $poids < 1 || $poids > 500) {
				throw new Exception("User.init() : Poids invalide");
			}
			$this -> poids = $poids;
			if ($mot_de_passe == null || strlen($mot_de_passe) < 8 || !preg_match('/[A-Z]/', $mot_de_passe) ||  !preg_match('/[a-z]/', $mot_de_passe) || !preg_match('/[0-9]/', $mot_de_passe)) {
				throw new Exception("User.init() : Mot de passe invalide");
			}
			$this -> mot_de_passe = $mot_de_passe;
		}

		public function getAdresseElectronique(): string {
			return $this -> adresse_electronique;
		}

		public function getNom(): string {
			return $this -> nom;
		}

		public function getPrenom(): string {
			return $this -> prenom;
		}

		public function getDateNaissance(): string {
			return $this -> date_naissance;
		}

		public function getSexe(): int {
			return $this -> sexe;
		}

		public function getTaille(): int {
			return $this -> taille;
		}

		public function getPoids(): int {
			return $this -> poids;
		}

		public function getMotDePasse(): string {
			return $this -> mot_de_passe;
		}

		public function __toString(): string {
			return $this -> nom . " " . $this -> prenom . " (" . $this -> adresse_electronique . " " . $this -> date_naissance . " " . $this -> sexe . " " . $this -> taille . " " . $this -> poids . " " . $this -> mot_de_passe . ")";
		}
	}
?>
