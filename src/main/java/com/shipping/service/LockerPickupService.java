package com.shipping.service;

import com.shipping.dto.LockerPickupRequest;
import com.shipping.kafka.DeliveryEventProducer;
import com.shipping.model.LockerPickup;
import com.shipping.model.LockerStatus;
import com.shipping.repository.LockerPickupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class LockerPickupService {

    private final LockerPickupRepository lockerPickupRepository;
    private final DeliveryEventProducer eventProducer;

    @Transactional
    public LockerPickup assignLocker(LockerPickupRequest request) {
        log.info("Assigning locker for order: {}", request.getOrderId());

        // Generate locker code (6-digit)
        String lockerCode = String.format("%06d", new Random().nextInt(999999));

        LockerPickup lockerPickup = new LockerPickup();
        lockerPickup.setOrderId(request.getOrderId());
        lockerPickup.setCustomerId(request.getCustomerId());
        lockerPickup.setLockerLocationId(request.getLockerLocationId());
        lockerPickup.setLockerAddress(getLockerAddress(request.getLockerLocationId()));
        lockerPickup.setLockerCode(lockerCode);
        lockerPickup.setCompartmentNumber(generateCompartmentNumber());
        lockerPickup.setStatus(LockerStatus.ASSIGNED);
        lockerPickup.setAssignedAt(LocalDateTime.now());

        LockerPickup saved = lockerPickupRepository.save(lockerPickup);

        // Publish event
        eventProducer.publishLockerAssigned(saved);

        log.info("Locker assigned: {} with code: {}", saved.getLockerPickupId(), lockerCode);

        return saved;
    }

    private String getLockerAddress(String locationId) {
        // Mock locker address retrieval
        return "Locker Location: " + locationId + ", Main Street, City Center";
    }

    private String generateCompartmentNumber() {
        return "C-" + (new Random().nextInt(50) + 1);
    }

    public LockerPickup getByOrderId(String orderId) {
        return lockerPickupRepository.findByOrderId(orderId)
            .orElseThrow(() -> new RuntimeException("Locker pickup not found for order: " + orderId));
    }
}

