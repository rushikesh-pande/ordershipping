package com.shipping.controller;

import com.shipping.dto.GiftWrappingRequest;
import com.shipping.model.GiftWrapping;
import com.shipping.service.GiftWrappingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gift")
@RequiredArgsConstructor
public class GiftWrappingController {

    private final GiftWrappingService giftWrappingService;

    @PostMapping("/add")
    public ResponseEntity<GiftWrapping> addGiftWrapping(@Valid @RequestBody GiftWrappingRequest request) {
        GiftWrapping giftWrapping = giftWrappingService.addGiftWrapping(request);
        return new ResponseEntity<>(giftWrapping, HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<GiftWrapping> getGiftWrappingByOrderId(@PathVariable String orderId) {
        GiftWrapping giftWrapping = giftWrappingService.getByOrderId(orderId);
        return ResponseEntity.ok(giftWrapping);
    }
}

