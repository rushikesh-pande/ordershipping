package com.shipping.dto;

import lombok.*;

/**
 * Enhancement #1 - Tracking update DTO used in shipping service.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TrackingUpdateDto {
    private String orderId;
    private String trackingNumber;
    private String carrier;
    private String location;
    private String estimatedDelivery;
    private String driverName;
}
