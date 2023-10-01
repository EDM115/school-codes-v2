<?php
    include __ROOT__."/views/header.html";
?>

<div class="drac-box drac-w-xl drac-scrollbar-purple drac-rounded-lg drac-p-md">
<br>
    <h2 class="drac-heading drac-heading-lg drac-text-purple">Ajouter un utilisateur</h2>
    <br>


    <form action="/user_add" method="post">
        <span class="drac-text drac-line-height drac-text-white">Nom : </span>
            <input class="drac-input drac-input-white drac-text-white" type="text" name="nom" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Prénom : </span>
            <input class="drac-input drac-input-white drac-text-white" type="text" name="prenom" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Date de Naissance : </span>
            <input class="drac-input drac-input-white drac-text-white" type="date" name="date_naissance" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Sexe : </span>
            <input type="radio" class="drac-radio drac-radio-orange" name="sexe" value="0" checked>
                <span class="drac-text drac-line-height drac-text-white"> Homme </span>
            <input type="radio" class="drac-radio drac-radio-orange" name="sexe" value="1">
                <span class="drac-text drac-line-height drac-text-white"> Femme</span>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Taille : </span>
            <input class="drac-input drac-input-white drac-text-white" type="number" name="taille" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Poids : </span>
            <input class="drac-input drac-input-white drac-text-white" type="number" name="poids" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Adresse électronique : </span>
            <input class="drac-input drac-input-white drac-text-white" type="email" name="adresse_electronique" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Mot de passe : </span>
            <input class="drac-input drac-input-white drac-text-white" type="password" name="mot_de_passe" required>
        <br><br>
        <button type="submit" class="drac-btn drac-bg-animated">Créer</button>
    </form>

<?php
    include __ROOT__."/views/footer.html";
?>
