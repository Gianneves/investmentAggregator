package com.gianpneves.investmentaggregator.repository;

import com.gianpneves.investmentaggregator.entity.AccountStock;
import com.gianpneves.investmentaggregator.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
