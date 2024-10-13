#!/usr/bin/env python3

import sqlite3
import cherrypy

class VulnerableApp(object):
	def __init__(self):
		self.conn = sqlite3.connect("application.db", check_same_thread=False)

	@cherrypy.expose
	def index(self, **post):
		cursor = self.conn.cursor()
		if cherrypy.request.method == "POST":
			print("string: [" + post["string"] + "]")
			requete = "INSERT INTO vulnerable_tab (txt, who) VALUES('" + post["string"] + "','" + cherrypy.request.remote.ip + "');"
			print("req: [" + requete + "]")
			cursor.executescript(requete)
			self.conn.commit()

		strings = []
		cursor.execute("SELECT txt, who FROM vulnerable_tab");
		for row in cursor.fetchall():
			strings.append(row[0] + " envoye par: " + row[1])

		cursor.close()
		return '''
<html>
	<head>
		<title>Application Python Vulnerable</title>
		<script>document.cookie = "My Bank Details"</script>
	</head>
	<body>
		<h2>Application Python Vulnerable</h2>

		<p>Bonjour, je suis une application vulnérable qui sert a insérer des chaînes dans une base de données SQLite3 !</p>

		<p>Liste des chaines actuellement insérées:
			<ul>
			'''+"\n".join(["<li>" + s + "</li>" for s in strings])+'''
			</ul>
		</p>

		<p> Inserer une chaine:

		<form method="post" onsubmit="return validate()">
			<input type="text" name="string" id="string" value="" />
			<input type="submit" name="submit" value="OK" />
		</form>

		<script>
		function validate() {
			var regex = /^[a-zA-Z0-9]+$/;
			var string = document.getElementById('string').value;
			console.log(regex.test(string));
			if (!regex.test(string)) {
				alert("Veuillez entrer une chaine avec uniquement des lettres et des chiffres");
				return false;
			}
			return true;
		}
		</script>

		</p>
	</body>
</html>
'''

cherrypy.config.update({'server.socket_port': 8081})
cherrypy.quickstart(VulnerableApp())

