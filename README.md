# ordershipping

## Overview
Microservice for: **In shipping address service please add office address option,publish event in kafka topic**

## Tech Stack
- Java 17
- Spring Boot 3.2.2
- Maven
- Kafka (topic: `order.shipped`)

## API Endpoints
| Method | Path | Description |
|--------|------|-------------|
| POST   | /api/v1/shipments | Create |
| GET    | /api/v1/shipments | List all |
| GET    | /api/v1/shipments/{id} | Get by ID |
| PUT    | /api/v1/shipments/{id} | Update |
| DELETE | /api/v1/shipments/{id} | Delete |

## Running
```bash
mvn spring-boot:run
```
Service runs on port **8084**

## Kafka
Topic: `order.shipped`
Events: `SHIPMENT_CREATED`, `SHIPMENT_UPDATED`, `SHIPMENT_DELETED`
