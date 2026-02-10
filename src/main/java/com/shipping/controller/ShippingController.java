package com.shipping.controller;

import com.shipping.dto.ShipOrderRequest;
import com.shipping.dto.ShipmentResponse;
import com.shipping.service.ShippingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
@Slf4j
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping
    public ResponseEntity<ShipmentResponse> shipOrder(@Valid @RequestBody ShipOrderRequest request) {
        log.info("Received ship order request for order: {}", request.getOrderId());
        ShipmentResponse response = shippingService.shipOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<ShipmentResponse> getShipmentStatus(@PathVariable String shipmentId) {
        log.info("Received get shipment status request: {}", shipmentId);
        ShipmentResponse response = shippingService.getShipmentStatus(shipmentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/track/{trackingNumber}")
    public ResponseEntity<ShipmentResponse> trackShipment(@PathVariable String trackingNumber) {
        log.info("Received track shipment request: {}", trackingNumber);
        ShipmentResponse response = shippingService.trackShipment(trackingNumber);
        return ResponseEntity.ok(response);
    }
}

