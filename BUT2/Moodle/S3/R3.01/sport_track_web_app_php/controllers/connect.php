<?php
require(__ROOT__.'/controllers/Controller.php');

class ConnectController extends Controller{

    public function get($request){
        $this->render('connect_form',[]);
    }

    public function post($request){
        $this->render('connect_info',['firstname' => $request['firstname'], 'lastname' => $request['lastname']]);
    }
}

?>
