package com.shipping.service;

import com.shipping.dto.ShipOrderRequest;
import com.shipping.dto.ShipmentResponse;
import com.shipping.entity.AddressType;
import com.shipping.entity.Shipment;
import com.shipping.entity.ShipmentStatus;
import com.shipping.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {

    private final ShipmentRepository shipmentRepository;

    @Transactional
    public ShipmentResponse shipOrder(ShipOrderRequest request) {
        log.info("Shipping order: {} to {} address", request.getOrderId(), 
                request.getAddressType() != null ? request.getAddressType() : "HOME");

        String shipmentId = generateShipmentId();
        String trackingNumber = generateTrackingNumber();

        // Determine address type
        AddressType addressType = AddressType.HOME;
        if (request.getAddressType() != null && request.getAddressType().equalsIgnoreCase("OFFICE")) {
            addressType = AddressType.OFFICE;
            log.info("Office delivery requested for company: {}", request.getOfficeCompanyName());
        }

        Shipment shipment = Shipment.builder()
                .shipmentId(shipmentId)
                .orderId(request.getOrderId())
                .shippingAddress(request.getShippingAddress())
                .addressType(addressType)
                .officeCompanyName(request.getOfficeCompanyName())
                .officeFloor(request.getOfficeFloor())
                .officeBuildingName(request.getOfficeBuildingName())
                .officeLandmark(request.getOfficeLandmark())
                .city(request.getCity())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .country(request.getCountry() != null ? request.getCountry() : "USA")
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .status(ShipmentStatus.PROCESSING)
                .trackingNumber(trackingNumber)
                .carrier(request.getCarrier() != null ? request.getCarrier() : "Standard Carrier")
                .estimatedDelivery(calculateEstimatedDelivery(addressType))
                .build();

        shipment.setStatus(ShipmentStatus.SHIPPED);
        Shipment saved = shipmentRepository.save(shipment);

        log.info("Order shipped successfully: {} with tracking number: {}", shipmentId, trackingNumber);
        return mapToResponse(saved, "Order shipped successfully");
    }

    public ShipmentResponse getShipmentStatus(String shipmentId) {
        log.info("Fetching shipment status: {}", shipmentId);
        Shipment shipment = shipmentRepository.findByShipmentId(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + shipmentId));
        return mapToResponse(shipment, null);
    }

    public ShipmentResponse trackShipment(String trackingNumber) {
        log.info("Tracking shipment with tracking number: {}", trackingNumber);
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RuntimeException("Tracking number not found: " + trackingNumber));
        return mapToResponse(shipment, null);
    }

    private String generateShipmentId() {
        return "SHIP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String generateTrackingNumber() {
        return "TRK-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }

    private ShipmentResponse mapToResponse(Shipment shipment, String message) {
        return ShipmentResponse.builder()
                .shipmentId(shipment.getShipmentId())
                .orderId(shipment.getOrderId())
                .shippingAddress(shipment.getShippingAddress())
                .city(shipment.getCity())
                .state(shipment.getState())
                .zipCode(shipment.getZipCode())
                .country(shipment.getCountry())
                .customerName(shipment.getCustomerName())
                .customerPhone(shipment.getCustomerPhone())
                .status(shipment.getStatus().name())
                .trackingNumber(shipment.getTrackingNumber())
                .carrier(shipment.getCarrier())
                .estimatedDelivery(shipment.getEstimatedDelivery())
                .actualDelivery(shipment.getActualDelivery())
                .createdAt(shipment.getCreatedAt())
                .message(message)
                .build();
    }

    private LocalDateTime calculateEstimatedDelivery(AddressType addressType) {
        // Office deliveries might take 1 extra day for business hours coordination
        int days = (addressType == AddressType.OFFICE) ? 6 : 5;
        return LocalDateTime.now().plusDays(days);
    }
}
