<?php
    require(__ROOT__.'/controllers/Controller.php');
    require(__ROOT__.'/model/Activity.php');
    require(__ROOT__.'/model/ActivityDAO.php');
    require(__ROOT__.'/model/Data.php');
    require(__ROOT__.'/model/DataDAO.php');
    require_once(__ROOT__.'/model/SqliteConnection.php');

    class UploadActivityController extends Controller {
        public function get($request) {
            $this->render('upload_activity_form', []);
        }

        public function post($request) {
            if(session_status() == PHP_SESSION_NONE) {
                session_start();
            }
            if (!isset($_SESSION['user'])) {
                $this->render("login_form", []);
                return;
            }
            $userSession = $_SESSION['user'];
            if ($userSession == null || !$userSession) {
                $this->render("login_form", []);
                return;
            }

            try {
                $b64 = $request['base64'];
                $decoded = base64_decode($b64);
                if ($decoded === false || $decoded === null || $decoded === "") {
                    $this->render("error", ["message" => "Le fichier n'est pas un fichier JSON valide"]);
                }
            } catch (Exception $e) {
                $this->render("error", ["message" => $e->getMessage()]);
            }

            $json = json_decode($decoded, true);
            $activity = $json['activity'];
            $data = $json['data'];
            $date = $activity['date'];
            $description = $activity['description'];
            $dataLength = count($data);
            for ($i = 0; $i < $dataLength; $i++) {
                $time[$i] = $data[$i]['time'];
            }
            for ($i = 0; $i < $dataLength; $i++) {
                $cardio_frequency[$i] = $data[$i]['cardio_frequency'];
            }
            for ($i = 0; $i < $dataLength; $i++) {
                $latitude[$i] = $data[$i]['latitude'];
            }
            for ($i = 0; $i < $dataLength; $i++) {
                $longitude[$i] = $data[$i]['longitude'];
            }
            for ($i = 0; $i < $dataLength; $i++) {
                $altitude[$i] = $data[$i]['altitude'];
            }

            $dbc = SqliteConnection::getInstance();
            $query = "SELECT COUNT(*) FROM Activity";
            $stmt = $dbc->query($query);
            $id = $stmt->fetchColumn();
            $id++;
            
            $date = explode('/', $date);
            $date = $date[2].'-'.$date[1].'-'.$date[0];
            
            $user = $userSession->getAdresseElectronique();
            
            $activity = new Activity();
            try {
                $activity -> init($id, $date, $description, 1, 1, 1, 1, $user);
            } catch (Exception $e) {
                $this->render('error', ['message' => $e->getMessage()]);
                return;
            }

            $activityDAO = ActivityDAO::getInstance();
            try {
                $activityDAO->insert($activity);
            } catch (Exception $e) {
                $this->render('error', ['message' => $e->getMessage()]);
                return;
            }

            $query = "SELECT COUNT(*) FROM Data";
            $stmt = $dbc->query($query);
            $id2 = $stmt->fetchColumn();
            $id2++;

            for ($i = 0; $i < $dataLength; $i++) {
                $data = new Data();
                try {
                    $data -> init($id2, $time[$i], $cardio_frequency[$i], $altitude[$i], $latitude[$i], $longitude[$i], $id);
                } catch (Exception $e) {
                    $this->render('error', ['message' => $e->getMessage()]);
                    return;
                }
                $id2++;

                $dataDAO = DataDAO::getInstance();
                try {
                    $dataDAO->insert($data);
                } catch (Exception $e) {
                    $this->render('error', ['message' => $e->getMessage()]);
                    return;
                }
            }
            
            $stringActivity = "";
            foreach ($json['activity'] as $key => $value) {
                $stringActivity .= $key . " : " . $value . "<br>";
            }
            $stringData = "";
            for ($i = 0; $i < $dataLength; $i++) {
                $stringData .= "Data " . $i . "<br><br>";
                foreach ($json['data'][$i] as $key => $value) {
                    $stringData .= $key . " : " . $value . "<br>";
                }
                $stringData .= "<br>";
            }

            $this->render('activity_uploaded', ['activity' => $stringActivity, 'data' => $stringData]);
        }
    }
?>
