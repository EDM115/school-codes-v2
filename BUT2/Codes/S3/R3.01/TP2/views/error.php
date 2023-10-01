<?php
	include __ROOT__."/views/header.html";
?>

<br>
<h2 class="drac-heading drac-heading-lg drac-text-purple">Erreur</h2>
<br><br>

<?php
	echo '<div class="drac-box drac-w-xl drac-scrollbar-purple drac-rounded-lg drac-p-md">';
	echo '<span class="drac-text drac-line-height drac-text-white">Error = <span class="drac-text drac-text-semibold drac-line-height drac-text-purple">' . $data['message'] . '</span></span>';

	include __ROOT__."/views/footer.html";
?>
