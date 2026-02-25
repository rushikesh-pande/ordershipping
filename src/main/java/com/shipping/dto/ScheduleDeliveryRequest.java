package com.shipping.dto;

import com.shipping.model.DeliveryType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDeliveryRequest {

    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Delivery type is required")
    private DeliveryType deliveryType;

    private LocalDateTime scheduledDate;

    private String timeSlot; // "09:00-12:00", "14:00-18:00", "18:00-21:00"

    private String specialInstructions;

    private Boolean isFlexible;
}

