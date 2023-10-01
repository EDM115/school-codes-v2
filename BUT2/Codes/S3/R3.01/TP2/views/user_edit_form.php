<?php
    include __ROOT__."/views/header.html";
?>

<div class="drac-box drac-w-xl drac-scrollbar-purple drac-rounded-lg drac-p-md">
<br>
    <h2 class="drac-heading drac-heading-lg drac-text-purple">Éditer un utilisateur</h2>
    <br>

    <form action="/user_edit" method="post">
        <span class="drac-text drac-line-height drac-text-white">Nom : </span>
            <input type="text" class="drac-input drac-input-white drac-text-white" name="nom" value="<?php echo htmlspecialchars($nom); ?>" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Prénom : </span>
            <input type="text" class="drac-input drac-input-white drac-text-white" name="prenom" value="<?php echo htmlspecialchars($prenom); ?>" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Date de Naissance : </span>
            <input type="date" class="drac-input drac-input-white drac-text-white" name="date_naissance" value="<?php echo htmlspecialchars($date_naissance); ?>" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Sexe : </span> 
            <input type="radio" class="drac-radio drac-radio-orange" name="sexe" value="0" <?php echo ($sexe == 0) ? 'checked' : ''; ?>>
                <span class="drac-text drac-line-height drac-text-white"> Homme </span>
            <input type="radio" class="drac-radio drac-radio-orange" name="sexe" value="1" <?php echo ($sexe == 1) ? 'checked' : ''; ?>>
                <span class="drac-text drac-line-height drac-text-white"> Femme</span>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Taille : </span>
            <input type="number" class="drac-input drac-input-white drac-text-white" name="taille" value="<?php echo htmlspecialchars($taille); ?>" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Poids : </span>
            <input type="number" class="drac-input drac-input-white drac-text-white" name="poids" value="<?php echo htmlspecialchars($poids); ?>" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Adresse électronique : </span>
            <input type="email" class="drac-input drac-input-white drac-text-white" name="adresse_electronique" value="<?php echo htmlspecialchars($adresse_electronique); ?>" required>
        <br><br>
        <span class="drac-text drac-line-height drac-text-white">Mot de passe : </span>
            <input type="password" class="drac-input drac-input-white drac-text-white" name="mot_de_passe" value="<?php echo htmlspecialchars($mot_de_passe); ?>" required>
        <br><br>
        <button type="submit" class="drac-btn drac-bg-animated">Éditer</button>
    </form>

<?php
    include __ROOT__."/views/footer.html";
?>
