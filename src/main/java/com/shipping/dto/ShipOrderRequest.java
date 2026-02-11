package com.shipping.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipOrderRequest {

    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    private String addressType; // "HOME" or "OFFICE"

    // Office-specific fields
    private String officeCompanyName;
    private String officeFloor;
    private String officeBuildingName;
    private String officeLandmark;

    private String city;
    private String state;
    private String zipCode;
    private String country;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Customer phone is required")
    private String customerPhone;

    private String carrier;
}
