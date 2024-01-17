package com.gianpneves.investmentaggregator.repository;

import com.gianpneves.investmentaggregator.entity.BillingAddress;
import com.gianpneves.investmentaggregator.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, String> {
}
