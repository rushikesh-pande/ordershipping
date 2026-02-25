package com.shipping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "store_pickups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorePickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String storePickupId;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String storeId;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String storeAddress;

    @Column(nullable = false)
    private String pickupCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorePickupStatus status;

    @Column(nullable = false)
    private LocalDateTime readyForPickupAt;

    @Column
    private LocalDateTime pickedUpAt;

    @Column
    private String notifiedVia; // SMS, EMAIL

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (storePickupId == null) {
            storePickupId = "STP-" + System.currentTimeMillis();
        }
    }
}

