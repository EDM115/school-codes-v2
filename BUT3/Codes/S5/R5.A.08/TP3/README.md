## R5.A.08 - TP3 Microservices

Les accès au backend sont sécurisés par une authentification basique.  
Certains endpoints sont protégés et nécessitent une connexion.  

## Build & Run
```bash
cd api-gateway && mvn clean package
cd ../ms-location && mvn clean package
cd ../ms-user && mvn clean package
cd .. && docker compose build --no-cache && docker compose up
```

## Endpoints
API Gateway :
- http://localhost:8080/  
*redirige vers les autres services* :
- http://localhost:8080/ms-user/users
- http://localhost:8080/ms-location/locations

**Requiert une connexion (`admin`, `azertyuiop`)** :
- http://localhost:8081/users
- http://localhost:8081/h2-console
- http://localhost:8082/locations
- http://localhost:8082/h2-console

H2 props :
- Driver Class : `org.h2.Driver`
- JDBC URL : `jdbc:h2:mem:testdb`
- User Name : `sa`
- Password : empty

## Tests
```bash
curl -X POST http://localhost:8081/users \
-u admin:azertyuiop \
-H "Content-Type: application/json" \
-d '{
  "firstName": "Alice",
  "lastName": "Smith",
  "email": "alice.smith@example.com",
  "password": "password123"
}'

curl -X POST http://localhost:8080/ms-user/users \
-u admin:azertyuiop \
-H "Content-Type: application/json" \
-d '{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "password123"
}'

curl -X GET http://localhost:8081/users \
-u admin:azertyuiop

curl -X GET http://localhost:8080/ms-user/users \
-u admin:azertyuiop

curl -X POST http://localhost:8082/locations \
-u admin:azertyuiop \
-H "Content-Type: application/json" \
-d '{
  "latitude": "51.5074",
  "longitude": "-0.1278",
  "timestamp": "2024-10-04T12:00:00",
  "userId": 1
}'

curl -X POST http://localhost:8080/ms-location/locations \
-u admin:azertyuiop \
-H "Content-Type: application/json" \
-d '{
  "latitude": "40.7128",
  "longitude": "-74.0060",
  "timestamp": "2024-10-04T12:00:00",
  "userId": 1
}'

curl -X GET http://localhost:8082/locations \
-u admin:azertyuiop

curl -X GET http://localhost:8080/ms-location/locations \
-u admin:azertyuiop
```

### H2 Users :
```sql
INSERT INTO users (first_name, last_name, email, password)
VALUES ('John', 'Doe', 'john.doe@example.com', 'password123');

SELECT * FROM users;
```

### H2 Location :
```sql
INSERT INTO location (latitude, longitude, timestamp, user_id)
VALUES ('40.7128', '-74.0060', NOW(), 1);

SELECT * FROM location;
```
