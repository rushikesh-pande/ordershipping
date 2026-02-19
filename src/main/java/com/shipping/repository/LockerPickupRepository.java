package com.shipping.repository;

import com.shipping.model.LockerPickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LockerPickupRepository extends JpaRepository<LockerPickup, Long> {

    Optional<LockerPickup> findByLockerPickupId(String lockerPickupId);

    Optional<LockerPickup> findByOrderId(String orderId);

    List<LockerPickup> findByCustomerId(String customerId);

    List<LockerPickup> findByLockerLocationId(String lockerLocationId);
}

