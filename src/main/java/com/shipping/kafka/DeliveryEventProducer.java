package com.shipping.kafka;

import com.shipping.model.DeliverySchedule;
import com.shipping.model.LockerPickup;
import com.shipping.model.StorePickup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String DELIVERY_SCHEDULED_TOPIC = "delivery.scheduled";
    private static final String LOCKER_ASSIGNED_TOPIC = "locker.assigned";
    private static final String PICKUP_READY_TOPIC = "pickup.ready";

    public void publishDeliveryScheduled(DeliverySchedule schedule) {
        try {
            String message = String.format(
                "{\"scheduleId\":\"%s\",\"orderId\":\"%s\",\"customerId\":\"%s\",\"deliveryType\":\"%s\",\"scheduledDate\":\"%s\",\"timeSlot\":\"%s\"}",
                schedule.getScheduleId(), schedule.getOrderId(), schedule.getCustomerId(),
                schedule.getDeliveryType(), schedule.getScheduledDate(), schedule.getTimeSlot()
            );
            kafkaTemplate.send(DELIVERY_SCHEDULED_TOPIC, schedule.getOrderId(), message);
            log.info("Published delivery scheduled event for order: {}", schedule.getOrderId());
        } catch (Exception e) {
            log.error("Error publishing delivery scheduled event: {}", e.getMessage(), e);
        }
    }

    public void publishLockerAssigned(LockerPickup lockerPickup) {
        try {
            String message = String.format(
                "{\"lockerPickupId\":\"%s\",\"orderId\":\"%s\",\"customerId\":\"%s\",\"lockerLocationId\":\"%s\",\"lockerCode\":\"%s\",\"compartmentNumber\":\"%s\",\"expiryTime\":\"%s\"}",
                lockerPickup.getLockerPickupId(), lockerPickup.getOrderId(), lockerPickup.getCustomerId(),
                lockerPickup.getLockerLocationId(), lockerPickup.getLockerCode(), 
                lockerPickup.getCompartmentNumber(), lockerPickup.getExpiryTime()
            );
            kafkaTemplate.send(LOCKER_ASSIGNED_TOPIC, lockerPickup.getOrderId(), message);
            log.info("Published locker assigned event for order: {}", lockerPickup.getOrderId());
        } catch (Exception e) {
            log.error("Error publishing locker assigned event: {}", e.getMessage(), e);
        }
    }

    public void publishPickupReady(StorePickup storePickup) {
        try {
            String message = String.format(
                "{\"storePickupId\":\"%s\",\"orderId\":\"%s\",\"customerId\":\"%s\",\"storeId\":\"%s\",\"storeName\":\"%s\",\"pickupCode\":\"%s\",\"readyForPickupAt\":\"%s\"}",
                storePickup.getStorePickupId(), storePickup.getOrderId(), storePickup.getCustomerId(),
                storePickup.getStoreId(), storePickup.getStoreName(), 
                storePickup.getPickupCode(), storePickup.getReadyForPickupAt()
            );
            kafkaTemplate.send(PICKUP_READY_TOPIC, storePickup.getOrderId(), message);
            log.info("Published pickup ready event for order: {}", storePickup.getOrderId());
        } catch (Exception e) {
            log.error("Error publishing pickup ready event: {}", e.getMessage(), e);
        }
    }
}

