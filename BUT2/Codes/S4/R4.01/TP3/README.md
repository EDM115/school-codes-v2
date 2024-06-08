# TP3

## Ce que j'ai réussi à faire
- Créer un micro-service spring boot
- Accéder à `/activities` affiche le contenu du JSON avec les activités
- Accéder à `/activities/{k}` affiche le contenu des activités qui contiennent `k` en description
- Envoyer une requête POST à `/activities` avec un JSON d'activité pour l'ajouter à la liste

## Ce que je n'ai pas réussi à faire
- Ajouter l'activité dans le JSON durant une requête POST à `/activities`

## Tests

- **GET /activities**
```bash
curl http://localhost:8080/activities/
```

```json
[{"date":"03/01/2023","description":"IUT  RU","distance":770,"freqMin":98,"freqMax":103,"data":[{"time":"13:00:00","cardioFrequency":99,"latitude":47.644794,"longitude":-2.776605,"altitude":18.0},{"time":"13:00:05","cardioFrequency":100,"latitude":47.64687,"longitude":-2.778911,"altitude":18.0},{"time":"13:00:10","cardioFrequency":102,"latitude":47.6462,"longitude":-2.78022,"altitude":18.0}]},{"date":"2024-04-04","description":"Morning Run in Paris","distance":5000,"freqMin":110,"freqMax":160,"data":[{"time":"2024-04-04T12:00:00","cardioFrequency":120,"latitude":48.8566,"longitude":2.3522,"altitude":35.0}]},{"date":"2024-04-05","description":"Morning Run in Marseille","distance":5000,"freqMin":110,"freqMax":160,"data":[{"time":"2024-04-05T12:00:00","cardioFrequency":120,"latitude":48.8566,"longitude":2.3522,"altitude":35.0}]}]
```

- **GET /activities/{k}**
```bash
curl http://localhost:8080/activities/iut
```

```json
[{"date":"03/01/2023","description":"IUT  RU","distance":770,"freqMin":98,"freqMax":103,"data":[{"time":"13:00:00","cardioFrequency":99,"latitude":47.644794,"longitude":-2.776605,"altitude":18.0},{"time":"13:00:05","cardioFrequency":100,"latitude":47.64687,"longitude":-2.778911,"altitude":18.0},{"time":"13:00:10","cardioFrequency":102,"latitude":47.6462,"longitude":-2.78022,"altitude":18.0}]}]
```

- **POST /activities**
```bash
curl -X POST http://localhost:8080/activities/ -H "Content-Type: application/json" -d '{"date": "2024-04-10", "description": "Jogging matinal à Lyon", "distance": 3000, "freqMin": 95, "freqMax": 140, "data": [{"time": "2024-04-10T19:00:00", "cardioFrequency": 115, "latitude": 45.764043, "longitude": 4.835659, "altitude": 200}]}'
```

```json
{"date":"2024-04-10","description":"Jogging matinal à Lyon","distance":3000,"freqMin":95,"freqMax":140,"data":[{"time":"2024-04-10T19:00:00","cardioFrequency":115,"latitude":45.76404,"longitude":4.835659,"altitude":200.0}]}
```
