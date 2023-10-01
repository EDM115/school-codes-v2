<?php
ini_set('display_errors', 'On');
error_reporting(E_ALL);
define ("__ROOT__",__DIR__);

// Configuration
require_once (__ROOT__.'/config.php');

// ApplicationController
require_once (CONTROLLERS_DIR.'/ApplicationController.php');


// Add routes here
ApplicationController::getInstance()->addRoute('connect', CONTROLLERS_DIR.'/connect.php');

// Process the request
ApplicationController::getInstance()->process();

?>
