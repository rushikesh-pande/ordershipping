package com.shipping.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockerPickupRequest {

    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotBlank(message = "Locker location ID is required")
    private String lockerLocationId;

    private String preferredCompartmentSize; // SMALL, MEDIUM, LARGE
}

