<?php
    require(__ROOT__.'/controllers/Controller.php');

    class AProposController extends Controller {
        public function get($request) {
            $this->render('apropos', []);
        }
    }
?>
