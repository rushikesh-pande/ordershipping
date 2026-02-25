package com.shipping.service;

import com.shipping.dto.StorePickupRequest;
import com.shipping.kafka.DeliveryEventProducer;
import com.shipping.model.StorePickup;
import com.shipping.model.StorePickupStatus;
import com.shipping.repository.StorePickupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorePickupService {

    private final StorePickupRepository storePickupRepository;
    private final DeliveryEventProducer eventProducer;

    @Transactional
    public StorePickup createStorePickup(StorePickupRequest request) {
        log.info("Creating store pickup for order: {} at store: {}", 
            request.getOrderId(), request.getStoreId());

        String pickupCode = String.format("SP%06d", new Random().nextInt(999999));

        StorePickup storePickup = new StorePickup();
        storePickup.setOrderId(request.getOrderId());
        storePickup.setCustomerId(request.getCustomerId());
        storePickup.setStoreId(request.getStoreId());
        storePickup.setStoreName(getStoreName(request.getStoreId()));
        storePickup.setStoreAddress(getStoreAddress(request.getStoreId()));
        storePickup.setPickupCode(pickupCode);
        storePickup.setStatus(StorePickupStatus.PROCESSING);
        storePickup.setReadyForPickupAt(LocalDateTime.now().plusHours(2)); // Ready in 2 hours
        storePickup.setNotifiedVia(request.getNotificationMethod());

        StorePickup saved = storePickupRepository.save(storePickup);

        // Publish event
        eventProducer.publishPickupReady(saved);

        log.info("Store pickup created: {} with code: {}", saved.getStorePickupId(), pickupCode);

        return saved;
    }

    private String getStoreName(String storeId) {
        return "Store-" + storeId;
    }

    private String getStoreAddress(String storeId) {
        return "Store Address for " + storeId + ", Shopping Mall, City";
    }

    public StorePickup getByOrderId(String orderId) {
        return storePickupRepository.findByOrderId(orderId)
            .orElseThrow(() -> new RuntimeException("Store pickup not found for order: " + orderId));
    }
}

