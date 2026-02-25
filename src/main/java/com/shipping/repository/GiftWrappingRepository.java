package com.shipping.repository;

import com.shipping.model.GiftWrapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GiftWrappingRepository extends JpaRepository<GiftWrapping, Long> {

    Optional<GiftWrapping> findByGiftWrappingId(String giftWrappingId);

    Optional<GiftWrapping> findByOrderId(String orderId);
}

