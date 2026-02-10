package com.shipping.repository;

import com.shipping.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByShipmentId(String shipmentId);
    List<Shipment> findByOrderId(String orderId);
    Optional<Shipment> findByTrackingNumber(String trackingNumber);
}

