package com.shipping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gift_wrapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftWrapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String giftWrappingId;

    @Column(nullable = false)
    private String orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GiftWrapType wrapType;

    @Column
    private String giftMessage;

    @Column
    private String cardMessage;

    @Column(nullable = false)
    private Double additionalCost;

    @Column
    private String recipientName;

    @Column(nullable = false)
    private Boolean anonymousSender;

    @PrePersist
    protected void onCreate() {
        if (giftWrappingId == null) {
            giftWrappingId = "GW-" + System.currentTimeMillis();
        }
        if (anonymousSender == null) {
            anonymousSender = false;
        }
    }
}

