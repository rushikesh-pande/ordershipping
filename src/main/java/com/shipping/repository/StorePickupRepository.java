package com.shipping.repository;

import com.shipping.model.StorePickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StorePickupRepository extends JpaRepository<StorePickup, Long> {

    Optional<StorePickup> findByStorePickupId(String storePickupId);

    Optional<StorePickup> findByOrderId(String orderId);

    List<StorePickup> findByCustomerId(String customerId);

    List<StorePickup> findByStoreId(String storeId);
}

