<?php
	require_once "User.php";

	class Activity {
		private int $id_activity;
		private string $date;
		private ?string $description;
		private int $duree;
		private float $distance;
		private int $freq_card_min;
		private int $freq_card_max;
		private int $freq_card_moy;
		private string $un_user;

		public function __construct() { }

		public function init($id_activity, $date, $description, $duree, $freq_card_min, $freq_card_max, $freq_card_moy, $un_user) {
			if ($id_activity == null || $id_activity < 0) {
				throw new Exception("Activity.init() : Id invalide");
			}
			$this -> id_activity = $id_activity;
			if ($date == null || !preg_match('/^([0-9]{4})-([0-9]{2})-([0-9]{2})$/', $date) || $date < '1900-01-01' || $date > date("Y-m-d")) {
				throw new Exception("Activity.init() : Date invalide");
			}
			$this -> date = $date;
			if ($description == null) {
				throw new Exception("Activity.init() : Description invalide");
			}
			$this -> description = $description;
			if ($duree == null || $duree < 0 || $duree > 1440) {
				throw new Exception("Activity.init() : Durée invalide");
			}
			$this -> duree = $duree;
			$this -> distance = 0;
			if ($freq_card_min == null || $freq_card_min < 0 || $freq_card_min > 250) {
				throw new Exception("Activity.init() : Fréquence cardiaque minimale invalide");
			}
			$this -> freq_card_min = $freq_card_min;
			if ($freq_card_max == null || $freq_card_max < 0 || $freq_card_max > 250) {
				throw new Exception("Activity.init() : Fréquence cardiaque maximale invalide");
			}
			$this -> freq_card_max = $freq_card_max;
			if ($freq_card_moy == null || $freq_card_moy < 0 || $freq_card_moy > 250) {
				throw new Exception("Activity.init() : Fréquence cardiaque moyenne invalide");
			}
			$this -> freq_card_moy = $freq_card_moy;
			if (strlen($un_user) < 5 || !filter_var($un_user, FILTER_VALIDATE_EMAIL) || $un_user == null) {
				throw new Exception("Activity.init() : Utilisateur invalide");
			}
			$this -> un_user = $un_user;
		}

		public function getIdActivity(): int {
			return $this -> id_activity;
		}

		public function getDate(): string {
			return $this -> date;
		}

		public function getDescription(): ?string {
			return $this -> description;
		}

		public function getDuree(): ?int {
			return $this -> duree;
		}

		public function getDistance(): ?float {
			return $this -> distance;
		}

		public function getFreqCardMin(): ?int {
			return $this -> freq_card_min;
		}

		public function getFreqCardMax(): ?int {
			return $this -> freq_card_max;
		}

		public function getFreqCardMoy(): ?int {
			return $this -> freq_card_moy;
		}

		public function getUnUser(): string {
			return $this -> un_user;
		}

		public function __toString(): string {
			return $this -> id_activity . " - " . $this -> date . " - " . $this -> description . " - " . $this -> duree . " - " . $this -> distance . " - " . $this -> freq_card_min . " - " . $this -> freq_card_max . " - " . $this -> freq_card_moy . " - " . $this -> un_user;
		}
	}
?>
