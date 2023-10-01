<?php
require(__ROOT__.'/controllers/Controller.php');

class MainController extends Controller{

    public function get($request){
        $this->render('main',[]);
    }

}

?>
