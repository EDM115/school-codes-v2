<?php
	include __ROOT__."/views/header.html";
?>

<div class="drac-box drac-w-md drac-scrollbar-purple drac-rounded-lg drac-p-md">
<br>
	<h2 class="drac-heading drac-heading-lg drac-text-purple">Accueil</h2>
	<br><br>

	<?php
		$isConnected = true;
		if(session_status() == PHP_SESSION_NONE) {
			session_start();
		}
		if (!isset($_SESSION['user'])) {
			$isConnected = false;
		}
		$userSession = $_SESSION['user'];
		if ($userSession == null || !$userSession) {
			$isConnected = false;
		}

		if ($isConnected) {
			echo '<a class="drac-anchor drac-text drac-text-lg drac-text-green drac-text-yellow--hover drac-mb-sm" href="/disconnect">Déconnexion</a><br>';
		} else {
			echo '<a class="drac-anchor drac-text drac-text-lg drac-text-green drac-text-yellow--hover drac-mb-sm" href="/connect">Connection</a><br>';
		}
	?>

	<a class="drac-anchor drac-text drac-text-lg drac-text-green drac-text-yellow--hover drac-mb-sm" href="/user_add">Ajouter un utilisateur</a>
	<br>
	<a class="drac-anchor drac-text drac-text-lg drac-text-green drac-text-yellow--hover drac-mb-sm" href="/user_edit">Modifier un utilisateur</a>
	<br>
	<a class="drac-anchor drac-text drac-text-lg drac-text-green drac-text-yellow--hover drac-mb-sm" href="/upload">Ajouter un fichier d'activité</a>
	<br>
	<a class="drac-anchor drac-text drac-text-lg drac-text-green drac-text-yellow--hover drac-mb-sm" href="/activities">Afficher les activités</a>
	<br>
	<br>
	<a class="drac-anchor drac-text drac-text-lg drac-text-green drac-text-yellow--hover drac-mb-sm" href="/apropos">À propos</a>
	<br>
	<a class="drac-anchor drac-text drac-text-lg drac-text-green drac-text-red--hover drac-mb-sm" href="/reset">Réinitialiser la BDD</a>
	<br>

<?php
	include __ROOT__."/views/footer.html";
?>
