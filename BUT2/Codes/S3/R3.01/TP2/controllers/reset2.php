<?php
	require_once(__ROOT__.'/model/SqliteConnection.php');

	$dbc = SqliteConnection::getInstance();
	$query = "DELETE FROM Data";
	$stmt = $dbc -> prepare($query);
	$stmt -> execute();
	$query = "DELETE FROM Activity";
	$stmt = $dbc -> prepare($query);
	$stmt -> execute();

	include __ROOT__."/views/header.html";
	echo '<div class="drac-box drac-w-xl drac-scrollbar-purple drac-rounded-lg drac-p-md">';
	echo "<br><h2 class=\"drac-heading drac-heading-lg drac-text-purple\"> Réinitialisation </h2><br><br><span class=\"drac-text drac-line-height drac-text-white\"> La base de données à bien été réinitialisée (Activity, Data)</span>";
	include __ROOT__."/views/footer.html";
?>
