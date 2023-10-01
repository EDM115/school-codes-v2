<?php
    require_once('SqliteConnection.php');
    require_once('UserDAO.php');
    require_once('ActivityDAO.php');
    require_once('DataDAO.php');
    require_once('User.php');
    require_once('Activity.php');
    require_once('Data.php');

    echo "<br>--- Test de connexion SQLite ---<br>";

    try {
        $connection = SqliteConnection::getInstance();
    } catch (PDOException $e) {
        die("Erreur de connexion : " . $e -> getMessage());
    }

    echo "<br>--- Nettoyage des précédentes données ---<br>";

    $dbc = SqliteConnection::getInstance();
    $query = "DELETE FROM Data";
    $stmt = $dbc -> prepare($query);
    $stmt -> execute();
    $query = "DELETE FROM Activity";
    $stmt = $dbc -> prepare($query);
    $stmt -> execute();
    $query = "DELETE FROM User";
    $stmt = $dbc -> prepare($query);
    $stmt -> execute();

    echo "<br>--- Insertion de données ---<br>";

    $userDAO = UserDAO::getInstance();
    $activityDAO = ActivityDAO::getInstance();
    $dataDAO = DataDAO::getInstance();

    $user = new User();
    $user -> init('test1@example.com', 'Dupont', 'Jean', "1990-12-01", 1, 180, 70, '#Password123');
    $userDAO -> insert($user);

    $activity = new Activity();
    $activity -> init(1, "2023-09-10", 'Running', 60, 80, 120, 100, 'test1@example.com');
    $activityDAO -> insert($activity);

    $data = new Data();
    $data -> init(1, '10:00:00', 80, 500, 48.8566, 2.3522, 1);
    $dataDAO -> insert($data);

    $data2 = new Data();
    $data2 -> init(2, '10:01:00', 90, 500, 48.8567, 2.3523, 1);
    $dataDAO -> insert($data2);

    echo "<br>--- Insertion terminée ! ---<br>";

    echo "<br>--- Récupération et affichage des données ---<br>";

    $users = $userDAO -> findAll();
    foreach ($users as $u) {
        echo $u . "<br>";
    }

    $activities = $activityDAO -> findAll();
    foreach ($activities as $a) {
        echo $a . "<br>";
    }

    $datas = $dataDAO -> findAll();
    foreach ($datas as $d) {
        echo $d . "<br>";
    }

    echo "<br>--- Fin du script ---<br>";
?>
