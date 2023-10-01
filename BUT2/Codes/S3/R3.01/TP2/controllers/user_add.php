<?php
    require(__ROOT__.'/controllers/Controller.php');
    require(__ROOT__.'/model/User.php');
    require(__ROOT__.'/model/UserDAO.php');

    class AddUserController extends Controller {
        public function get($request) {
            $this->render('user_add_form', []);
        }

        public function post($request) {
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
                $userdao -> insert($user);
            } catch (Exception $e) {
                $this->render('error', ['message' => $e->getMessage()]);
                return;
            }

            $this->render('user_added', ['nom' => $nom, 'prenom' => $prenom, 'date_naissance' => $date_naissance, 'sexe' => $sexe, 'taille' => $taille, 'poids' => $poids, 'adresse_electronique' => $adresse_electronique, 'mot_de_passe' => $mot_de_passe]);
        }
    }
?>
