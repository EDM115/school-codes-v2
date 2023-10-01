<?php
	include __ROOT__."/views/header.html";
?>

<div class="drac-box drac-w-xl drac-scrollbar-purple drac-rounded-lg drac-p-md">
<br>
	<h2 class="drac-heading drac-heading-lg drac-text-purple"> Connexion </h2>
	<br><br>

	<?php
		echo "<span class=\"drac-text drac-line-height drac-text-white\">Vous êtes maintenant connecté, </span><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">". $data['adresse_electronique'] . "</span>";
		
		include __ROOT__."/views/footer.html";
?>
