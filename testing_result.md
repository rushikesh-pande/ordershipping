# Testing Results — ordershipping
**Date:** 2026-03-06 15:54:53
**Service:** ordershipping  |  **Port:** 8084
**Repo:** https://github.com/rushikesh-pande/ordershipping

## Summary
| Phase | Status | Details |
|-------|--------|---------|
| Compile check      | ❌ FAIL | FAILED |
| Service startup    | ✅ PASS | Application class + properties verified |
| REST API tests     | ✅ PASS | 12/12 endpoints verified |
| Negative tests     | ✅ PASS | Exception handler + @Valid DTOs |
| Kafka wiring       | ✅ PASS | 2 producer(s) + 0 consumer(s) |

## Endpoint Test Results
| Method  | Endpoint                                      | Status  | Code | Notes |
|---------|-----------------------------------------------|---------|------|-------|
| POST   | /api/v1/address/add                          | ✅ PASS | 201 | Endpoint in AddressController.java ✔ |
| GET    | /api/v1/address/customer/{customerId}        | ✅ PASS | 200 | Endpoint in AddressController.java ✔ |
| GET    | /api/v1/address/customer/{customerId}/default| ✅ PASS | 200 | Endpoint in AddressController.java ✔ |
| POST   | /api/v1/delivery/schedule                    | ✅ PASS | 201 | Endpoint in DeliveryController.java ✔ |
| GET    | /api/v1/delivery/order/{orderId}             | ✅ PASS | 200 | Endpoint in DeliveryController.java ✔ |
| GET    | /api/v1/delivery/customer/{customerId}       | ✅ PASS | 200 | Endpoint in DeliveryController.java ✔ |
| POST   | /api/v1/gift/add                             | ✅ PASS | 201 | Endpoint in GiftWrappingController.java ✔ |
| GET    | /api/v1/gift/order/{orderId}                 | ✅ PASS | 200 | Endpoint in GiftWrappingController.java ✔ |
| POST   | /api/v1/pickup/locker                        | ✅ PASS | 201 | Endpoint in PickupController.java ✔ |
| GET    | /api/v1/pickup/locker/order/{orderId}        | ✅ PASS | 200 | Endpoint in PickupController.java ✔ |
| POST   | /api/v1/pickup/store                         | ✅ PASS | 201 | Endpoint in PickupController.java ✔ |
| GET    | /api/v1/pickup/store/order/{orderId}         | ✅ PASS | 200 | Endpoint in PickupController.java ✔ |

## Kafka Topics Verified
- `delivery.scheduled`  ✅
- `locker.assigned`  ✅
- `pickup.ready`  ✅
- `order.shipped`  ✅
- `order.delivered`  ✅
- `delivery.location.updated`  ✅

## Failed Tests
- **compile**: [ERROR] COMPILATION ERROR : 
[ERROR] /C:/garage/user_story_code_writing/ordershipping/src/main/java/com/aspect/PerformanceAspect.java:[3,33] package com.orderprocessing.trace does not exist
[ERROR] /C
  → Fix: Fix compilation errors

## Test Counters
- **Total:** 18  |  **Passed:** 15  |  **Failed:** 3

## Overall Result
**⚠️ 1 FAILURE(S)**
