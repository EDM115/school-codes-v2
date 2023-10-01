<?php
    class SqliteConnection {
        private static ?PDO $connection = null;

        private function __construct() { }

        private function __clone() { }

        public static function getInstance(): PDO {
            if (self::$connection === null) {
                try {
                    self::$connection = new PDO('sqlite:'.__ROOT__.'/model/sport_track.db');
                    self::$connection -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                } catch (PDOException $e) {
                    die("Erreur de connexion : " . $e -> getMessage());
                }
            }
            return self::$connection;
        }

        public static function prepare($query) {
            return self::getInstance() -> prepare($query);
        }
    }
?>
