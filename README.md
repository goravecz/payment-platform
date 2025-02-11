## Payment Platform (PP)
This is a payment platform that
- allows users to send money to other users via a REST API
- notifies users of incoming payments

### Main Features
- unintentional duplicate payments are prevented
- notifications are sent exactly once
- transactions are saved to a database

### Technologies
- Spring Boot
- PostgreSQL
- Kafka
- Docker
- Maven

### Modules
- **payment-service**: REST API, message deduplication, transaction persistence, balance updates, and notifications
- **notification-service**: notification deduplication

### How to Run
All services required to use the platform run in Docker containers.
The following command will start up both submodules, the database, Kafka and a couple of additional services to help inspect the DB and 
the Kafka topics. (more on these in the Developer notes section below)

`docker-compose up --build`


### How to use
Make a `POST` request to the `/api/v1/payment` endpoint with the following JSON body:

```json
{
  "id": "123c1262-e29b-1213-a851-226614174012",
  "senderId": "550e8400-e29b-41d4-a716-446655440000",
  "receiverId": "550e8400-e29b-41d4-a716-446655440001",
  "amount": 100
}
```
via Swagger UI: http://localhost:8765/swagger-ui.html (example requests are provided)

or
<details>
<summary>cURL (click to expand)</summary>

```
curl -X POST http://localhost:8765/api/v1/payment -H "Content-Type: application/json" -d '{"id": "123c1262-e29b-1213-a851-226614174012", "senderId": "550e8400-e29b-41d4-a716-446655440000", "receiverId": "550e8400-e29b-41d4-a716-446655440001", "amount": 120.00}'
```
</details>

**Note that the `id` of the request acts as an idempotency key, i.e. it should be unique every time a request is sent.)**

### Developer notes
- The cleanup.sh in the root directory can be used to remove all containers and volumes.
- Transaction idempotency is achieved by relying on the `uuid` field of the `Transaction` entity. This field has a `unique` constraint
in the database.
- Notification idempotency is achieved by the new(ish) EOS (exactly-once semantics) of Kafka. Logs around Kafka are set to DEBUG 
to see the details. (Look for `Transition from state READY to IN_TRANSACTION`, 
`Transition from state IN_TRANSACTION to COMMITTING_TRANSACTION`, `Transition from state COMMITTING_TRANSACTION to READY`) 
- Kafka topics can be inspected via Kafdrop at http://localhost:9000
- Changes in the DB can be inspected via pgAdmin at http://localhost:5050 

<details>
<summary>pgAdmin setup (click to expand)</summary>

1. Login: admin@admin.com / admin
2. Click Add New Server
3. Name can be anything
4. Connection tab: Host name: `postgres`, username: `postgres`, password: `password`

</details>

### Future Improvements
- Add tests
- Centralize configurations
- Evolve it into a production ready architecture
