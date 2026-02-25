package com.shipping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliverySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String scheduleId;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType;

    @Column
    private LocalDateTime scheduledDate;

    @Column
    private String timeSlot; // e.g., "09:00-12:00", "14:00-18:00"

    @Column
    private String specialInstructions;

    @Column(nullable = false)
    private Boolean isFlexible; // Can delivery date be adjusted?

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (scheduleId == null) {
            scheduleId = "SCH-" + System.currentTimeMillis();
        }
        if (isFlexible == null) {
            isFlexible = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

