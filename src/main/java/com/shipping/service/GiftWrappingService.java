package com.shipping.service;

import com.shipping.dto.GiftWrappingRequest;
import com.shipping.model.GiftWrapType;
import com.shipping.model.GiftWrapping;
import com.shipping.repository.GiftWrappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GiftWrappingService {

    private final GiftWrappingRepository giftWrappingRepository;

    @Transactional
    public GiftWrapping addGiftWrapping(GiftWrappingRequest request) {
        log.info("Adding gift wrapping for order: {}", request.getOrderId());

        GiftWrapping giftWrapping = new GiftWrapping();
        giftWrapping.setOrderId(request.getOrderId());
        giftWrapping.setWrapType(request.getWrapType());
        giftWrapping.setGiftMessage(request.getGiftMessage());
        giftWrapping.setCardMessage(request.getCardMessage());
        giftWrapping.setRecipientName(request.getRecipientName());
        giftWrapping.setAnonymousSender(request.getAnonymousSender() != null ? request.getAnonymousSender() : false);
        giftWrapping.setAdditionalCost(calculateGiftWrappingCost(request.getWrapType()));

        GiftWrapping saved = giftWrappingRepository.save(giftWrapping);

        log.info("Gift wrapping added: {} with cost: {}", saved.getGiftWrappingId(), saved.getAdditionalCost());

        return saved;
    }

    private Double calculateGiftWrappingCost(GiftWrapType wrapType) {
        return switch (wrapType) {
            case STANDARD -> 2.99;
            case PREMIUM -> 5.99;
            case FESTIVE -> 4.99;
            case BIRTHDAY -> 4.99;
            case ANNIVERSARY -> 5.99;
            case CUSTOM -> 9.99;
            case ECO_FRIENDLY -> 6.99;
        };
    }

    public GiftWrapping getByOrderId(String orderId) {
        return giftWrappingRepository.findByOrderId(orderId)
            .orElseThrow(() -> new RuntimeException("Gift wrapping not found for order: " + orderId));
    }
}

