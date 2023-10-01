<?php
    include __ROOT__."/views/header.html";
?>

<div class="drac-box drac-w-md drac-scrollbar-purple drac-rounded-lg drac-p-md">
<br>
    <h2 class="drac-heading drac-heading-lg drac-text-purple">Connexion</h2>
    <br>

    <form action="/connect" method="post">
        <span class="drac-text drac-line-height drac-text-white">Adresse électronique : </span>
            <input class="drac-input drac-input-white drac-text-white" type="email" name="adresse_electronique" placeholder="mail@test.com" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Mot de passe : </span>
            <input class="drac-input drac-input-white drac-text-white" type="password" name="mot_de_passe" placeholder="password" required>
        <br><br>
        <button type="submit" class="drac-btn drac-bg-animated">Login</button>
    </form>

<?php
    include __ROOT__."/views/footer.html";
?>
