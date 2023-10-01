<?php
	include __ROOT__."/views/header.html";
?>

<div class="drac-box drac-w-xl drac-scrollbar-purple drac-rounded-lg drac-p-md">
<br>
	<h2 class="drac-heading drac-heading-lg drac-text-purple">Fichier d'activité ajouté</h2>
<br><br>

<?php
	echo "<span class=\"drac-text drac-line-height drac-text-white\">Activité : </span><br><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">". $activity ."</span>";
	echo "<br>";
	echo "<span class=\"drac-text drac-line-height drac-text-white\">Data : </span><br><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">". $data ."</span>";

	include __ROOT__."/views/footer.html";
?>
