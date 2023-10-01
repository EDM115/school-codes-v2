<?php
    require(__ROOT__.'/controllers/Controller.php');
    require_once(__ROOT__.'/model/SqliteConnection.php');

    class ListActivityController extends Controller {
        public function get($request) {
            $dbc = SqliteConnection::getInstance();

            $query = "SELECT * FROM activity";
            $stmt = $dbc->prepare($query);
            $stmt->execute();
            $activities = $stmt->fetchAll();

            $query = "SELECT * FROM data";
            $stmt = $dbc->prepare($query);
            $stmt->execute();
            $data = $stmt->fetchAll();

            $this->render('list_activities', ["activities" => $activities, "data" => $data]);
        }
    }
?>
