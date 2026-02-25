package com.shipping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "locker_pickups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockerPickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String lockerPickupId;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String lockerLocationId;

    @Column(nullable = false)
    private String lockerAddress;

    @Column(nullable = false)
    private String lockerCode; // Code to open locker

    @Column
    private String compartmentNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LockerStatus status;

    @Column(nullable = false)
    private LocalDateTime assignedAt;

    @Column
    private LocalDateTime pickedUpAt;

    @Column(nullable = false)
    private LocalDateTime expiryTime; // When locker code expires

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (lockerPickupId == null) {
            lockerPickupId = "LKR-" + System.currentTimeMillis();
        }
        if (expiryTime == null) {
            expiryTime = LocalDateTime.now().plusDays(3); // 3 days to pickup
        }
    }
}

