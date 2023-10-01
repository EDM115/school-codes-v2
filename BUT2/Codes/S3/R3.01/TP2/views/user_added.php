<?php
	include __ROOT__."/views/header.html";
?>

<div class="drac-box drac-w-xl drac-scrollbar-purple drac-rounded-lg drac-p-md">
<br>
	<h2 class="drac-heading drac-heading-lg drac-text-purple">Utilisateur ajouté</h2>
	<br><br>

	<?php
		echo "<span class=\"drac-text drac-line-height drac-text-white\">Nom : </span><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">". $data['nom'] ."</span>";
		echo "<br>";
		echo "<span class=\"drac-text drac-line-height drac-text-white\">Prenom : </span><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">". $data['prenom'] ."</span>";
		echo "<br>";
		echo "<span class=\"drac-text drac-line-height drac-text-white\">Date de naissance : </span><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">". $data['date_naissance'] ."</span>";
		echo "<br>";
		echo "<span class=\"drac-text drac-line-height drac-text-white\">Sexe : </span><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">". $data['sexe'] ."</span>";
		echo "<br>";
		echo "<span class=\"drac-text drac-line-height drac-text-white\">Taille : </span><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">". $data['taille'] ."</span>";
		echo "<br>";
		echo "<span class=\"drac-text drac-line-height drac-text-white\">Poids : </span><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">". $data['poids'] ."</span>";
		echo "<br>";
		echo "<span class=\"drac-text drac-line-height drac-text-white\">Adresse electronique : </span><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">". $data['adresse_electronique'] ."</span>";
		echo "<br>";
		echo "<span class=\"drac-text drac-line-height drac-text-white\">Mot de passe : </span><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">". $data['mot_de_passe'] ."</span>";

		include __ROOT__."/views/footer.html";
?>
