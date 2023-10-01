<?php
    require_once 'SqliteConnection.php';
    require_once 'Activity.php';
    require_once 'UserDAO.php';

    class ActivityDAO {
        private static ?ActivityDAO $dao = null;

        private function __construct() {}

        public static function getInstance(): ActivityDAO {
            if (!isset(self::$dao)) {
                self::$dao = new ActivityDAO();
            }
            return self::$dao;
        }

        public function findAll(): array {
            $dbc = SqliteConnection::getInstance();
            $query = "SELECT * FROM Activity";
            $stmt = $dbc -> query($query);
            return $stmt -> fetchALL(PDO::FETCH_CLASS, 'Activity');
        }

        public function findById(int $id): ?Activity {
            $dbc = SqliteConnection::getInstance();
            $query = "SELECT * FROM Activity WHERE id_activity = :id";
            $stmt = $dbc->prepare($query);
            
            $stmt->bindValue(':id', $id, PDO::PARAM_INT);
            $stmt->setFetchMode(PDO::FETCH_CLASS, 'Activity');
            $stmt->execute();
        
            $activity = $stmt->fetch();
        
            if ($activity) {
                $userDAO = UserDAO::getInstance();
                $user = $userDAO->findByEmail($activity->getUnUser());
                $activity->setUnUser($user);
                return $activity;
            }
        
            return null;
        }    

        public function insert(Activity $activity): void {
            $dbc = SqliteConnection::getInstance();
            $query = "INSERT INTO Activity(id_activity, date, description, duree, distance, freq_card_min, freq_card_max, freq_card_moy, un_user) VALUES (:id, :date, :desc, :duree, :distance, :f_min, :f_max, :f_moy, :u_email)";
            $stmt = $dbc -> prepare($query);

            $stmt -> bindValue(':id', $activity -> getIdActivity(), PDO::PARAM_INT);
            $stmt -> bindValue(':date', $activity -> getDate(), PDO::PARAM_STR);
            $stmt -> bindValue(':desc', $activity -> getDescription(), PDO::PARAM_STR);
            $stmt -> bindValue(':duree', $activity -> getDuree(), PDO::PARAM_INT);
            $stmt -> bindValue(':distance', $activity -> getDistance(), PDO::PARAM_INT);
            $stmt -> bindValue(':f_min', $activity -> getFreqCardMin(), PDO::PARAM_INT);
            $stmt -> bindValue(':f_max', $activity -> getFreqCardMax(), PDO::PARAM_INT);
            $stmt -> bindValue(':f_moy', $activity -> getFreqCardMoy(), PDO::PARAM_INT);
            $stmt -> bindValue(':u_email', $activity -> getUnUser(), PDO::PARAM_STR);

            $stmt -> execute();
        }

        public function delete(Activity $activity): void {
            $dbc = SqliteConnection::getInstance();
            $query = "DELETE FROM Activity WHERE id_activity = :id";
            $stmt = $dbc -> prepare($query);
            
            $stmt -> bindValue(':id', $activity -> getIdActivity(), PDO::PARAM_INT);
            
            $stmt -> execute();
        }

        public function update(Activity $activity): void {
            $dbc = SqliteConnection::getInstance();
            $query = "UPDATE Activity SET date = :date, description = :desc, duree = :duree, distance = :distance, freq_card_min = :f_min, freq_card_max = :f_max, freq_card_moy = :f_moy, un_user = :u_email WHERE id_activity = :id";
            $stmt = $dbc -> prepare($query);

            $stmt -> bindValue(':id', $activity -> getIdActivity(), PDO::PARAM_INT);
            $stmt -> bindValue(':date', $activity -> getDate(), PDO::PARAM_STR);
            $stmt -> bindValue(':desc', $activity -> getDescription(), PDO::PARAM_STR);
            $stmt -> bindValue(':duree', $activity -> getDuree(), PDO::PARAM_INT);
            $stmt -> bindValue(':distance', $activity -> getDistance(), PDO::PARAM_INT);
            $stmt -> bindValue(':f_min', $activity -> getFreqCardMin(), PDO::PARAM_INT);
            $stmt -> bindValue(':f_max', $activity -> getFreqCardMax(), PDO::PARAM_INT);
            $stmt -> bindValue(':f_moy', $activity -> getFreqCardMoy(), PDO::PARAM_INT);
            $stmt -> bindValue(':u_email', $activity -> getUnUser(), PDO::PARAM_STR);

            $stmt -> execute();
        }
    }
?>
