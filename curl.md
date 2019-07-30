## Testing REST API with curl

### Get meal
- curl -v http://localhost:8080/topjava/rest/meals/100007

### Get all meals
- curl -v http://localhost:8080/topjava/rest/meals

### Delete meal
- curl -X DELETE http://localhost:8080/topjava/rest/meals/100007

### Create meal
- curl -d "{\"id\": null,\"dateTime\": \"2015-05-31T13:20:00\",\"description\": \"Обед\",\"calories\": 1000}" -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/meals

### Update meal
- curl -d "{\"id\": null,\"dateTime\": \"2015-05-31T13:20:00\",\"description\": \"Обед\",\"calories\": 1000}" -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/meals/100007

### Get filtered meal
- curl -v http://localhost:8080/topjava/rest/meals/filtered
