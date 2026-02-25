package com.orderprocessing.ordershipping.repository;

import com.orderprocessing.ordershipping.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByStatus(String status);
}
