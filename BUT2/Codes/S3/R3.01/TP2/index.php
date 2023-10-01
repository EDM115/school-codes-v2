<?php
	ini_set('display_errors', 'On');
	error_reporting(E_ALL);
	define ("__ROOT__",__DIR__);

	require_once (__ROOT__.'/config.php');
	require_once (CONTROLLERS_DIR.'/ApplicationController.php');

	$routes = array();
	$routes[] = "apropos";
	$routes[] = "error";
	$routes[] = "connect";
	$routes[] = "list_activities";
	$routes[] = "login_form";
	$routes[] = "upload_activity_form";
	$routes[] = "upload";
	$routes[] = "user_add_form";
	$routes[] = "user_add";
	$routes[] = "user_added";
	$routes[] = "user_connected";
	$routes[] = "user_disconnected";
	$routes[] = "user_edit_form";
	$routes[] = "user_edit";
	$routes[] = "user_edited";
	$routes[] = "disconnect";
	$routes[] = "activities";
	$routes[] = "reset";
	$routes[] = "reset2";
	$routes[] = "/";

	$path = ApplicationController::getInstance()->request_path();
	if(!in_array($path, $routes)){
		ApplicationController::getInstance()->redirect('/');
	}
	if (in_array($path, ["model/sport_track.db"])) {
		die();
	}

	ApplicationController::getInstance()->addRoute('connect', CONTROLLERS_DIR.'/user_connect.php');
	ApplicationController::getInstance()->addRoute('apropos', CONTROLLERS_DIR.'/apropos.php');
	ApplicationController::getInstance()->addRoute('user_add', CONTROLLERS_DIR.'/user_add.php');
	ApplicationController::getInstance()->addRoute('user_edit', CONTROLLERS_DIR.'/user_edit.php');
	ApplicationController::getInstance()->addRoute('disconnect', CONTROLLERS_DIR.'/disconnect.php');
	ApplicationController::getInstance()->addRoute('upload', CONTROLLERS_DIR.'/upload.php');
	ApplicationController::getInstance()->addRoute('activities', CONTROLLERS_DIR.'/activities.php');
	ApplicationController::getInstance()->addRoute('reset', CONTROLLERS_DIR.'/reset.php');
	ApplicationController::getInstance()->addRoute('reset2', CONTROLLERS_DIR.'/reset2.php');

	ApplicationController::getInstance()->process();

	if(session_status() == PHP_SESSION_NONE && !headers_sent()) {
		session_start();
	}
?>
