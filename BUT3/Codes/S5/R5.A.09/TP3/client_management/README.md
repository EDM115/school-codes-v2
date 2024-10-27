# R5.A.09 - TP2 - Lussandre Lederrey

## Build && Run

```bash
docker compose build --no-cache && docker compose up
```

## Test

```bash
curl -X POST "http://localhost:8000/clients/" -H "Content-Type: application/json" -d '{"first_name": "John", "last_name": "Doe", "email": "johndoe@example.com", "orders_count": 5}'
curl -X GET "http://localhost:8000/clients/"
curl -X GET "http://localhost:8000/clients/1"
curl -X PUT "http://localhost:8000/clients/1" -H "Content-Type: application/json" -d '{"first_name": "John", "last_name": "Doe", "email": "john.doe@newdomain.com", "orders_count": 10}'
curl -X DELETE "http://localhost:8000/clients/1"
```
