package com.shipping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentResponse {

    private String shipmentId;
    private String orderId;
    private String shippingAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String customerName;
    private String customerPhone;
    private String status;
    private String trackingNumber;
    private String carrier;
    private LocalDateTime estimatedDelivery;
    private LocalDateTime actualDelivery;
    private LocalDateTime createdAt;
    private String message;
}

