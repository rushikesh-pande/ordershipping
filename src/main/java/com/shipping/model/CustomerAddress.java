package com.shipping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String addressId;

    @Column(nullable = false)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AddressType addressType;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String addressLine1;

    @Column
    private String addressLine2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private String alternatePhone;

    @Column
    private String landmark;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(nullable = false)
    private Boolean isDefault;

    @Column(nullable = false)
    private Boolean isValidated;

    @Column
    private String validationNotes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime lastUsedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (addressId == null) {
            addressId = "ADDR-" + System.currentTimeMillis();
        }
        if (isDefault == null) {
            isDefault = false;
        }
        if (isValidated == null) {
            isValidated = false;
        }
    }
}

