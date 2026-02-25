package com.shipping.controller;

import com.shipping.dto.LockerPickupRequest;
import com.shipping.dto.StorePickupRequest;
import com.shipping.model.LockerPickup;
import com.shipping.model.StorePickup;
import com.shipping.service.LockerPickupService;
import com.shipping.service.StorePickupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pickup")
@RequiredArgsConstructor
public class PickupController {

    private final LockerPickupService lockerPickupService;
    private final StorePickupService storePickupService;

    @PostMapping("/locker")
    public ResponseEntity<LockerPickup> assignLocker(@Valid @RequestBody LockerPickupRequest request) {
        LockerPickup lockerPickup = lockerPickupService.assignLocker(request);
        return new ResponseEntity<>(lockerPickup, HttpStatus.CREATED);
    }

    @GetMapping("/locker/order/{orderId}")
    public ResponseEntity<LockerPickup> getLockerByOrderId(@PathVariable String orderId) {
        LockerPickup lockerPickup = lockerPickupService.getByOrderId(orderId);
        return ResponseEntity.ok(lockerPickup);
    }

    @PostMapping("/store")
    public ResponseEntity<StorePickup> createStorePickup(@Valid @RequestBody StorePickupRequest request) {
        StorePickup storePickup = storePickupService.createStorePickup(request);
        return new ResponseEntity<>(storePickup, HttpStatus.CREATED);
    }

    @GetMapping("/store/order/{orderId}")
    public ResponseEntity<StorePickup> getStorePickupByOrderId(@PathVariable String orderId) {
        StorePickup storePickup = storePickupService.getByOrderId(orderId);
        return ResponseEntity.ok(storePickup);
    }
}

