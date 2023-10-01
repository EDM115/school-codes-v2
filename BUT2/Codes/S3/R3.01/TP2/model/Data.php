<?php
	require_once "Activity.php";

	class Data {
		private int $id_data;
		private string $heure_debut;
		private int $freq_card;
		private int $altitude;
		private float $latitude;
		private float $longitude;
		private int $une_activite;

		public function __construct() { }

		public function init($id_data, $heure_debut, $freq_card, $altitude, $latitude, $longitude, $une_activite) {
			if ($id_data == null || $id_data < 0) {
				throw new Exception("Data.init() : Id invalide");
			}
			$this -> id_data = $id_data;
			if ($heure_debut == null || !preg_match('/^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$/', $heure_debut)) {
				throw new Exception("Data.init() : Heure de début invalide");
			}		
			$this -> heure_debut = $heure_debut;
			if ($freq_card == null || $freq_card < 0 || $freq_card > 250) {
				throw new Exception("Data.init() : Fréquence cardiaque invalide");
			}
			$this -> freq_card = $freq_card;
			if ($altitude == null || $altitude < -100 || $altitude > 10000) {
				throw new Exception("Data.init() : Altitude invalide");
			}
			$this -> altitude = $altitude;
			if ($latitude == null || $latitude < -90 || $latitude > 90) {
				throw new Exception("Data.init() : Latitude invalide");
			}
			$this -> latitude = $latitude;
			if ($longitude == null || $longitude < -180 || $longitude > 180) {
				throw new Exception("Data.init() : Longitude invalide");
			}
			$this -> longitude = $longitude;
			if ($une_activite == null || $une_activite < 0) {
				throw new Exception("Data.init() : Activité invalide");
			}
			$this -> une_activite = $une_activite;
		}

		public function getIdData(): int {
			return $this -> id_data;
		}

		public function getHeureDebut(): string {
			return $this -> heure_debut;
		}

		public function getFreqCard(): int {
			return $this -> freq_card;
		}

		public function getAltitude(): int {
			return $this -> altitude;
		}

		public function getLatitude(): float {
			return $this -> latitude;
		}

		public function getLongitude(): float {
			return $this -> longitude;
		}

		public function getUneActivite(): int {
			return $this -> une_activite;
		}

		public function __toString(): string {
			return $this -> id_data . " - " . $this -> heure_debut . " - " . $this -> freq_card . " - " . $this -> altitude . " - " . $this -> latitude . " - " . $this -> longitude . " - " . $this -> une_activite;
		}
	}
?>
