# TP4

## Ce que j'ai réussi à faire
- Spécification de l'API REST
- Modification du TP1 (classes Activity et Data avec des annotations Jakarta)
- Documentation de l'API REST avec Swagger
- Validation des données d'entrée avec Hibernate

## Tests

- **POST /activities** : Création d'une activité invalide
```bash
curl -X POST http://localhost:8080/activities/ -H "Content-Type: application/json" -d '{"date": "2024-05-06", "description": "Test", "distance": 3000, "freqMin": -1, "freqMax": 250, "data": [{"time": "19:00:00", "cardioFrequency": 8669, "latitude": 45.764043, "longitude": 4.835659, "altitude": 200}]}'
```

```json
freqMax: doit être inférieur ou égal à 200; freqMin: doit être supérieur ou égal à 0
```
