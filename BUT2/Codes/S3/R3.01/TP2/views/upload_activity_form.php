<?php
	include __ROOT__."/views/header.html";
?>

<div class="drac-box drac-w-xl drac-scrollbar-purple drac-rounded-lg drac-p-md">
<br>
	<h2 class="drac-heading drac-heading-lg drac-text-purple">Ajouter un fichier d'activité</h2>
	<br><br>

	<form action="/upload" method="post">
		<div id="jsonDiv" variant="subtle" class="drac-box drac-card drac-bg-animated drac-p-sm json-label" onclick="document.getElementById('file').click()">
		<label for="file" class="drac-text drac-line-height drac-text-black json-label">Choisissez un fichier (JSON)</label>
			<input id="file" class="drac-input json-input" type="file" name="file" accept=".json, application/json" required>
		</div>
		<input type="text" autocomplete="off" id="base64" class="base64" name="base64">
		<br><br>
		<button type="submit" class="drac-btn drac-bg-animated">Upload</button>
	</form>

	<script>
		function clearInputFile(f) {
			if (f.value) {
				try {
					f.value = '';
				} catch(err) {
					console.log(err);
				}
			}
		}
		
		let filebutton = document.getElementById("file");
		filebutton.onchange = function checkJson() {
			let file = document.getElementById("file").files[0];
			let reader = new FileReader();
			reader.readAsText(file);
			reader.onload = function() {
				try {
					JSON.parse(reader.result);
				} catch (e) {
					let filename = file.name;
					alert(`Le fichier ${filename} n'est pas un fichier JSON valide`);
					clearInputFile(filebutton);
				}
			}
		}

		filebutton.addEventListener("change", (e) => {
			const file = e.target.files[0];
			const reader = new FileReader();
			reader.onloadend = () => {
				const base64String = reader.result
					.replace("data:", '')
					.replace(/^.+,/, '');
				jsonize(base64String);
			}
			reader.readAsDataURL(file);
		})
		
		let jsonarea = document.getElementById("base64");
		function jsonize(b64) {
			jsonarea.value = b64;
		}

		clearInputFile(filebutton);
	</script>

<?php
	include __ROOT__."/views/footer.html";
?>
