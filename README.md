# Order Shipping Service

## Overview
Microservice for shipping orders to customer addresses with tracking.

## Features
- Ship orders to customer desired addresses
- Generate tracking numbers
- Track shipment status
- Carrier integration
- Estimated delivery dates

## API Endpoints

### Ship Order
**POST** `/api/v1/shipments`

### Get Shipment Status
**GET** `/api/v1/shipments/{shipmentId}`

### Track Shipment
**GET** `/api/v1/shipments/track/{trackingNumber}`

## Technology Stack
- Java 17
- Spring Boot 3.2.2
- PostgreSQL

## Running
```bash
mvn spring-boot:run
```

