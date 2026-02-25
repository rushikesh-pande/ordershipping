package com.orderprocessing.ordershipping.service;

import com.orderprocessing.ordershipping.model.Shipment;
import com.orderprocessing.ordershipping.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "order.shipped";

    public Shipment create(Shipment shipment) {
        log.info("Creating Shipment: {}", shipment);
        Shipment saved = shipmentRepository.save(shipment);
        kafkaTemplate.send(TOPIC, "SHIPMENT_CREATED", saved.toString());
        log.info("Shipment created with id: {}", saved.getId());
        return saved;
    }

    public List<Shipment> findAll() {
        return shipmentRepository.findAll();
    }

    public Optional<Shipment> findById(Long id) {
        return shipmentRepository.findById(id);
    }

    public Shipment update(Long id, Shipment updated) {
        return shipmentRepository.findById(id).map(existing -> {
            updated.setId(id);
            Shipment saved = shipmentRepository.save(updated);
            kafkaTemplate.send(TOPIC, "SHIPMENT_UPDATED", saved.toString());
            log.info("Shipment updated: {}", saved.getId());
            return saved;
        }).orElseThrow(() -> new RuntimeException("Shipment not found: " + id));
    }

    public void delete(Long id) {
        shipmentRepository.deleteById(id);
        kafkaTemplate.send(TOPIC, "SHIPMENT_DELETED", id.toString());
        log.info("Shipment deleted: {}", id);
    }
}
