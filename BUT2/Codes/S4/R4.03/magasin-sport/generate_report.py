import json
import pandas as pd
import matplotlib.pyplot as plt

# Charger le fichier de rapport Artillery
with open('load-test-report.json') as f:
    data = json.load(f)

# Analyser les données
requests = []
timestamps = []

for result in data['intermediate']:
    if 'counters' in result:
        total_requests = (result['counters'].get('vusers.created', 0) - 
                          result['counters'].get('vusers.failed', 0))
        requests.append(total_requests)
        timestamps.append(result['lastMetricAt'])

# Convertir en DataFrame
df = pd.DataFrame({'timestamp': timestamps, 'requests': requests})
df['timestamp'] = pd.to_datetime(df['timestamp'], unit='ms')
df.set_index('timestamp', inplace=True)

# Statistiques
stats = df.describe()

# Générer le graphique
plt.figure(figsize=(10, 5))
plt.plot(df.index, df['requests'], marker='o')
plt.title('Montée en Charge de la Base de Données')
plt.xlabel('Temps')
plt.ylabel('Nombre de Requêtes')
plt.grid(True)
plt.savefig('load_test_graph.png')
plt.show()

# Enregistrer les statistiques
stats.to_csv('load_test_stats.csv')

print("Rapport généré :")
print(stats)
