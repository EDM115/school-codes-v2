<?php
    require(__ROOT__.'/controllers/Controller.php');
    require(__ROOT__.'/model/User.php');
    require(__ROOT__.'/model/UserDAO.php');

    class EditUserController extends Controller {
        public function get($request) {
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

            $nom = $userSession->getNom();
            $prenom = $userSession->getPrenom();
            $date_naissance = $userSession->getDateNaissance();
            $sexe = $userSession->getSexe();
            $taille = $userSession->getTaille();
            $poids = $userSession->getPoids();
            $adresse_electronique = $userSession->getAdresseElectronique();
            $mot_de_passe = $userSession->getMotDePasse();

            $this->render('user_edit_form', ['nom' => $nom, 'prenom' => $prenom, 'date_naissance' => $date_naissance, 'sexe' => $sexe, 'taille' => $taille, 'poids' => $poids, 'adresse_electronique' => $adresse_electronique, 'mot_de_passe' => $mot_de_passe]);
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

            $nom = $request['nom'];
            $prenom = $request['prenom'];
            $date_naissance = $request['date_naissance'];
            $sexe = $request['sexe'];
            $taille = $request['taille'];
            $poids = $request['poids'];
            $adresse_electronique = $request['adresse_electronique'];
            $mot_de_passe = $request['mot_de_passe'];

            $user = new User();
            try {
                $user -> init($adresse_electronique, $nom, $prenom, $date_naissance, $sexe, $taille, $poids, $mot_de_passe);
            } catch (Exception $e) {
                $this->render('error', ['message' => $e->getMessage()]);
                return;
            }

            $userdao = UserDAO::getInstance();
            try {
                $userdao -> update($user);
            } catch (Exception $e) {
                $this->render('error', ['message' => $e->getMessage()]);
                return;
            }

            $this->render('user_edited', ['nom' => $nom, 'prenom' => $prenom, 'date_naissance' => $date_naissance, 'sexe' => $sexe, 'taille' => $taille, 'poids' => $poids, 'adresse_electronique' => $adresse_electronique, 'mot_de_passe' => $mot_de_passe]);
        }
    }
?>
