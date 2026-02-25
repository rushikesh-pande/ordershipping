package com.orderprocessing.ordershipping.controller;

import com.orderprocessing.ordershipping.model.Shipment;
import com.orderprocessing.ordershipping.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
@Slf4j
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<Shipment> create(@RequestBody Shipment shipment) {
        log.info("POST /api/v1/shipments");
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(shipmentService.create(shipment));
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAll() {
        return ResponseEntity.ok(shipmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getById(@PathVariable Long id) {
        return shipmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipment> update(@PathVariable Long id,
                                                @RequestBody Shipment shipment) {
        return ResponseEntity.ok(shipmentService.update(id, shipment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        shipmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
