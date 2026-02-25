package com.shipping.model;

public enum DeliveryType {
    STANDARD,           // Regular delivery (3-5 days)
    EXPRESS,            // Express delivery (1-2 days)
    SAME_DAY,           // Same day delivery
    NEXT_DAY,           // Next day delivery
    SCHEDULED,          // Customer chooses date/time
    LOCKER_PICKUP,      // Pickup from locker
    STORE_PICKUP,       // Pickup from store
    GREEN_DELIVERY      // Eco-friendly delivery
}

