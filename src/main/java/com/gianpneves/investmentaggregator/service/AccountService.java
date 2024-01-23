package com.gianpneves.investmentaggregator.service;

import com.gianpneves.investmentaggregator.controller.dtos.AccountStockDTO;
import com.gianpneves.investmentaggregator.entity.AccountStock;
import com.gianpneves.investmentaggregator.entity.AccountStockId;
import com.gianpneves.investmentaggregator.repository.AccountRepository;
import com.gianpneves.investmentaggregator.repository.AccountStockRepository;
import com.gianpneves.investmentaggregator.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private AccountStockRepository accountStockRepository;


    public void associateStock(String accountId, AccountStockDTO accountStockDto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not exists"));

        var stock = stockRepository.findById(accountStockDto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not exists"));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());

        var accountStockEntity = new AccountStock(id, account, stock, accountStockDto.quantity());

        accountStockRepository.save(accountStockEntity);
    }
}
