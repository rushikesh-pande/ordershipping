package com.shipping.repository;

import com.shipping.model.DeliverySchedule;
import com.shipping.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryScheduleRepository extends JpaRepository<DeliverySchedule, Long> {

    Optional<DeliverySchedule> findByScheduleId(String scheduleId);

    Optional<DeliverySchedule> findByOrderId(String orderId);

    List<DeliverySchedule> findByCustomerId(String customerId);

    List<DeliverySchedule> findByStatus(DeliveryStatus status);
}

