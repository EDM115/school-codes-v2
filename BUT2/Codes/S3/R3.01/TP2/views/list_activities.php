<?php
	include __ROOT__."/views/header.html";
?>

<div class="drac-box drac-w-full drac-scrollbar-purple drac-rounded-lg drac-p-md">
<br>
	<h2 class="drac-heading drac-heading-lg drac-text-purple">Liste des activités</h2>
	<br><br>
	<h3 class="drac-heading drac-heading-md drac-text-cyan">Activités</h3>
	<br><br>

	<table class="drac-table drac-table-striped">
		<thead>
			<tr>
				<th class="drac-text drac-text-white">ID</th>
				<th class="drac-text drac-text-white">Date</th>
				<th class="drac-text drac-text-white" style="max-width: 200px">Description</th>
				<th class="drac-text drac-text-white">Durée</th>
				<th class="drac-text drac-text-white">Distance</th>
				<th class="drac-text drac-text-white">Fréquence cardiaque minimale</th>
				<th class="drac-text drac-text-white">Fréquence cardiaque maximale</th>
				<th class="drac-text drac-text-white">Fréquence cardiaque moyenne</th>
			</tr>
		</thead>
		<tbody>
			<?php
				$activitiesLen = count($activities);
				if ($activitiesLen == 0) {
					echo "<tr>";
					echo "<td class=\"drac-text drac-text-white\" colspan=\"8\">Aucune activité</td>";
					echo "</tr>";
				}
				for ($i = 0; $i < $activitiesLen; $i++) {
					echo "<tr>";
					echo "<td class=\"drac-text drac-text-white\">". $activities[$i]["id_activity"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\">". $activities[$i]["date"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\" style=\"max-width: 200px\">". $activities[$i]["description"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\">". $activities[$i]["duree"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\">". $activities[$i]["distance"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\">". $activities[$i]["freq_card_min"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\">". $activities[$i]["freq_card_max"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\">". $activities[$i]["freq_card_moy"] ."</td>";
					echo "</tr>";
				}
			?>
		</tbody>
	</table>

	<br><br>
	<h3 class="drac-heading drac-heading-md drac-text-cyan">Données</h3>
	<br><br>

	<table class="drac-table drac-table-striped">
		<thead>
			<tr>
				<th class="drac-text drac-text-white">Heure de début</th>
				<th class="drac-text drac-text-white">Fréquence cardiaque</th>
				<th class="drac-text drac-text-white">Altitude</th>
				<th class="drac-text drac-text-white">Latitude</th>
				<th class="drac-text drac-text-white">Longitude</th>
				<th class="drac-text drac-text-white">Activité</th>
			</tr>
		</thead>
		<tbody>
			<?php
				$dataLen = count($data);
				if ($dataLen == 0) {
					echo "<tr>";
					echo "<td class=\"drac-text drac-text-white\" colspan=\"6\">Aucune donnée</td>";
					echo "</tr>";
				}
				for ($i = 0; $i < $dataLen; $i++) {
					echo "<tr>";
					echo "<td class=\"drac-text drac-text-white\">". $data[$i]["heure_debut"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\">". $data[$i]["freq_card"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\">". $data[$i]["altitude"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\">". $data[$i]["latitude"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\">". $data[$i]["longitude"] ."</td>";
					echo "<td class=\"drac-text drac-text-white\">". $data[$i]["une_activite"] ."</td>";
					echo "</tr>";
				}
			?>
		</tbody>
	</table>

<?php
	include __ROOT__."/views/footer.html";
?>
