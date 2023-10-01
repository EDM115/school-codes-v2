<?php
    require(__ROOT__.'/controllers/Controller.php');

    class DisconnectUserController extends Controller {
        public function get($request) {
            if(session_status() == PHP_SESSION_NONE) {
                session_start();
            }
            $doSomething = true;
            if (!isset($_SESSION['user'])) {
                $doSomething = false;
            }
            $userSession = $_SESSION['user'];
            if ($userSession == null || !$userSession) {
                $doSomething = false;
            }
            if ($doSomething) {
                $_SESSION['user'] = null;
            }
            $this->render('user_disconnected', []);
        }
    }
?>
