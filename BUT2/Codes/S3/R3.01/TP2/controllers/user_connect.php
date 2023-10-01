<?php
	require(__ROOT__.'/controllers/Controller.php');
	require(__ROOT__.'/model/User.php');
	require(__ROOT__.'/model/UserDAO.php');

	class ConnectUserController extends Controller {
		public function get($request) {
			$this->render('login_form', []);
		}
		
		public function post($request) {
			$email = $request['adresse_electronique'];
			$password = $request['mot_de_passe'];

			try {
				$existing_user = UserDAO::getInstance()->findByEmail($email);
			} catch(TypeError $e) {
				$this->render('error', ['message' => "Aucun utilisateur avec l'adresse mail " . $email . " n'a été trouvé<br><br>" . $e->getMessage()]);
				return;
			}
			if (!$existing_user) {
				$this->render('error', ['message' => "Aucun utilisateur avec l'adresse mail " . $email . " n'a été trouvé"]);
				return;
			}
			if ($password !== $existing_user->getMotDePasse()) {
				$this->render('error', ['message' => "Le mot de passe est incorrect"]);
				return;
			}

			if(session_status() == PHP_SESSION_NONE) {
				session_start();
			}
			$_SESSION['user'] = $existing_user;
			$this->render('user_connected', ['adresse_electronique' => $email]);
		}
	}
?>
