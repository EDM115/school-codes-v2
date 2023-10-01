<?php
    require_once 'SqliteConnection.php';
    require_once 'Data.php';
    require_once 'ActivityDAO.php';
    require_once "CalculDistanceImpl.php";

    class DataDAO {
        private static ?DataDAO $dao = null;

        private function __construct() {}

        public static function getInstance(): DataDAO {
            if (!isset(self::$dao)) {
                self::$dao = new DataDAO();
            }
            return self::$dao;
        }

        public function findAll(): array {
            $dbc = SqliteConnection::getInstance();
            $query = "SELECT * FROM Data";
            $stmt = $dbc -> query($query);
            return $stmt -> fetchALL(PDO::FETCH_CLASS, 'Data');
        }

        public function insert(Data $data): void {
            $dbc = SqliteConnection::getInstance();
        
            // 1. Get the Data object with the lowest id_data for the given une_activite.
            $temp_query = "SELECT * FROM Data WHERE une_activite = :activite ORDER BY id_data ASC LIMIT 1";
            $stmt_temp = $dbc->prepare($temp_query);
            $stmt_temp->bindValue(':activite', $data->getUneActivite(), PDO::PARAM_INT);
            $stmt_temp->execute();
        
            $previous_data = $stmt_temp->fetch(PDO::FETCH_OBJ);
            $calcul = new CalculDistanceImpl();
        
            // 2. If the object exists, retrieve its latitude and longitude
            if ($previous_data) {
                $lat1 = $previous_data->latitude;
                $long1 = $previous_data->longitude;
                $lat2 = $data->getLatitude();
                $long2 = $data->getLongitude();
        
                // Calculate the distance using the provided function
                $distance = $calcul->calculDistance2PointsGPS($lat1, $long1, $lat2, $long2);
                $distance = strval($distance);
        
                // 3. Update the distance in the Activity table
                $update_query = "UPDATE Activity SET distance = :distance WHERE id_activity = :a_id";
                $stmt_update = $dbc->prepare($update_query);
                $stmt_update->bindValue(':distance', $distance, PDO::PARAM_STR);
                $stmt_update->bindValue(':a_id', $data->getUneActivite(), PDO::PARAM_INT);
                $stmt_update->execute();
            }
        
            // Insert the new data record
            $query = "INSERT INTO Data(id_data, heure_debut, freq_card, altitude, latitude, longitude, une_activite) VALUES (:id, :heure, :freq, :alt, :lat, :lon, :a_id)";
            $stmt = $dbc->prepare($query);
        
            $stmt->bindValue(':id', $data->getIdData(), PDO::PARAM_INT);
            $stmt->bindValue(':heure', $data->getHeureDebut(), PDO::PARAM_STR);
            $stmt->bindValue(':freq', $data->getFreqCard(), PDO::PARAM_INT);
            $stmt->bindValue(':alt', $data->getAltitude(), PDO::PARAM_INT);
            $stmt->bindValue(':lat', strval($data->getLatitude()), PDO::PARAM_STR);
            $stmt->bindValue(':lon', strval($data->getLongitude()), PDO::PARAM_STR);
            $stmt->bindValue(':a_id', $data->getUneActivite(), PDO::PARAM_INT);
        
            $stmt->execute();
        }
        

        public function delete(Data $data): void {
            $dbc = SqliteConnection::getInstance();
            $query = "DELETE FROM Data WHERE id_data = :id";
            $stmt = $dbc -> prepare($query);
            
            $stmt -> bindValue(':id', $data -> getIdData(), PDO::PARAM_INT);
            
            $stmt -> execute();
        }

        public function update(Data $data): void {
            $dbc = SqliteConnection::getInstance();
        
            // 1. Get the Data object with the lowest id_data for the given une_activite, excluding the current record.
            $temp_query = "SELECT * FROM Data WHERE une_activite = :activite AND id_data != :id ORDER BY id_data ASC LIMIT 1";
            $stmt_temp = $dbc->prepare($temp_query);
            $stmt_temp->bindValue(':activite', $data->getUneActivite(), PDO::PARAM_INT);
            $stmt_temp->bindValue(':id', $data->getIdData(), PDO::PARAM_INT);
            $stmt_temp->execute();
        
            $previous_data = $stmt_temp->fetch(PDO::FETCH_OBJ);
            $calcul = new CalculDistanceImpl();
        
            // 2. If the object exists, retrieve its latitude and longitude
            if ($previous_data) {
                $lat1 = $previous_data->latitude;
                $long1 = $previous_data->longitude;
                $lat2 = $data->getLatitude();
                $long2 = $data->getLongitude();
        
                // Calculate the distance using the provided function
                $distance = $calcul->calculDistance2PointsGPS($lat1, $long1, $lat2, $long2);
                $distance = strval($distance);
        
                // 3. Update the distance in the Activity table
                $update_activity_query = "UPDATE Activity SET distance = :distance WHERE id_activity = :a_id";
                $stmt_update_activity = $dbc->prepare($update_activity_query);
                $stmt_update_activity->bindValue(':distance', $distance,  PDO::PARAM_STR);
                $stmt_update_activity->bindValue(':a_id', $data->getUneActivite(), PDO::PARAM_INT);
                $stmt_update_activity->execute();
            }
        
            // Update the data record
            $query = "UPDATE Data SET heure_debut = :heure, freq_card = :freq, altitude = :alt, latitude = :lat, longitude = :lon, une_activite = :a_id WHERE id_data = :id";
            $stmt = $dbc->prepare($query);
        
            $stmt->bindValue(':id', $data->getIdData(), PDO::PARAM_INT);
            $stmt->bindValue(':heure', $data->getHeureDebut(), PDO::PARAM_STR);
            $stmt->bindValue(':freq', $data->getFreqCard(), PDO::PARAM_INT);
            $stmt->bindValue(':alt', $data->getAltitude(), PDO::PARAM_INT);
            $stmt->bindValue(':lat', strval($data->getLatitude()), PDO::PARAM_STR);
            $stmt->bindValue(':lon', strval($data->getLongitude()), PDO::PARAM_STR);
            $stmt->bindValue(':a_id', $data->getUneActivite(), PDO::PARAM_INT);
        
            $stmt->execute();
        }
    }
?>
