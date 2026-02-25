package com.shipping.service;

import com.shipping.dto.AddressRequest;
import com.shipping.model.CustomerAddress;
import com.shipping.repository.CustomerAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final CustomerAddressRepository addressRepository;

    @Transactional
    public CustomerAddress addAddress(AddressRequest request) {
        log.info("Adding new address for customer: {}", request.getCustomerId());

        CustomerAddress address = new CustomerAddress();
        address.setCustomerId(request.getCustomerId());
        address.setAddressType(request.getAddressType());
        address.setFullName(request.getFullName());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        address.setCountry(request.getCountry());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setAlternatePhone(request.getAlternatePhone());
        address.setLandmark(request.getLandmark());
        address.setIsDefault(request.getIsDefault() != null ? request.getIsDefault() : false);

        // Validate and geocode address
        validateAddress(address);

        CustomerAddress saved = addressRepository.save(address);

        // If this is set as default, unset other defaults
        if (saved.getIsDefault()) {
            unsetOtherDefaults(saved.getCustomerId(), saved.getAddressId());
        }

        log.info("Address added: {}", saved.getAddressId());

        return saved;
    }

    private void validateAddress(CustomerAddress address) {
        // Mock address validation
        // In real system, integrate with Google Maps API or similar
        address.setIsValidated(true);
        address.setValidationNotes("Address validated successfully");
        
        // Mock geocoding
        address.setLatitude(40.7128 + Math.random());
        address.setLongitude(-74.0060 + Math.random());
    }

    private void unsetOtherDefaults(String customerId, String currentAddressId) {
        List<CustomerAddress> addresses = addressRepository.findByCustomerId(customerId);
        for (CustomerAddress addr : addresses) {
            if (!addr.getAddressId().equals(currentAddressId) && addr.getIsDefault()) {
                addr.setIsDefault(false);
                addressRepository.save(addr);
            }
        }
    }

    public List<CustomerAddress> getCustomerAddresses(String customerId) {
        return addressRepository.findByCustomerIdOrderByLastUsedAtDesc(customerId);
    }

    public CustomerAddress getDefaultAddress(String customerId) {
        return addressRepository.findByCustomerIdAndIsDefaultTrue(customerId)
            .orElseThrow(() -> new RuntimeException("No default address found for customer: " + customerId));
    }

    @Transactional
    public void updateLastUsed(String addressId) {
        CustomerAddress address = addressRepository.findByAddressId(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found: " + addressId));
        address.setLastUsedAt(LocalDateTime.now());
        addressRepository.save(address);
    }
}

