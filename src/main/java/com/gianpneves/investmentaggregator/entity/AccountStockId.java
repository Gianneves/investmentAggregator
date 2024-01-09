package com.gianpneves.investmentaggregator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
public class AccountStockId {

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "stock_id")
    private String stockId;

    public AccountStockId() {

    }
    public AccountStockId(UUID accountId, String stockId) {
        this.accountId = accountId;
        this.stockId = stockId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}
