package com.shipping.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorePickupRequest {

    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotBlank(message = "Store ID is required")
    private String storeId;

    @NotBlank(message = "Notification method is required")
    private String notificationMethod; // SMS, EMAIL, BOTH
}

