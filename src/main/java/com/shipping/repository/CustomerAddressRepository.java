package com.shipping.repository;

import com.shipping.model.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

    Optional<CustomerAddress> findByAddressId(String addressId);

    List<CustomerAddress> findByCustomerId(String customerId);

    Optional<CustomerAddress> findByCustomerIdAndIsDefaultTrue(String customerId);

    List<CustomerAddress> findByCustomerIdOrderByLastUsedAtDesc(String customerId);
}

