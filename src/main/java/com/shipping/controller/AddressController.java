package com.shipping.controller;

import com.shipping.dto.AddressRequest;
import com.shipping.model.CustomerAddress;
import com.shipping.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<CustomerAddress> addAddress(@Valid @RequestBody AddressRequest request) {
        CustomerAddress address = addressService.addAddress(request);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerAddress>> getCustomerAddresses(@PathVariable String customerId) {
        List<CustomerAddress> addresses = addressService.getCustomerAddresses(customerId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/customer/{customerId}/default")
    public ResponseEntity<CustomerAddress> getDefaultAddress(@PathVariable String customerId) {
        CustomerAddress address = addressService.getDefaultAddress(customerId);
        return ResponseEntity.ok(address);
    }
}

