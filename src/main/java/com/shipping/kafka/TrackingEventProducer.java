package com.shipping.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Enhancement #1 - Order Tracking
 * Publishes order.shipped and delivery.location.updated events.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TrackingEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishOrderShipped(String orderId, String trackingNumber, String carrier, String estimatedDelivery) {
        String payload = String.format(
            "{\"orderId\":\"%s\",\"trackingNumber\":\"%s\",\"carrier\":\"%s\",\"eta\":\"%s\",\"event\":\"ORDER_SHIPPED\",\"timestamp\":\"%s\"}",
            orderId, trackingNumber, carrier, estimatedDelivery, LocalDateTime.now());
        kafkaTemplate.send("order.shipped", orderId, payload);
        log.info("[TRACKING] Published order.shipped event orderId={} tracking={}", orderId, trackingNumber);
    }

    public void publishDelivered(String orderId) {
        String payload = String.format(
            "{\"orderId\":\"%s\",\"event\":\"ORDER_DELIVERED\",\"timestamp\":\"%s\"}",
            orderId, LocalDateTime.now());
        kafkaTemplate.send("order.delivered", orderId, payload);
        log.info("[TRACKING] Published order.delivered event orderId={}", orderId);
    }

    public void publishLocationUpdate(String orderId, String location, String driverName) {
        String payload = String.format(
            "{\"orderId\":\"%s\",\"location\":\"%s\",\"driver\":\"%s\",\"timestamp\":\"%s\"}",
            orderId, location, driverName, LocalDateTime.now());
        kafkaTemplate.send("delivery.location.updated", orderId, payload);
        log.info("[TRACKING] Published location update orderId={} location={}", orderId, location);
    }
}
