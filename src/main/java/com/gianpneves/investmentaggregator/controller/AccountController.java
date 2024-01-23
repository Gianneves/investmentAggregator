package com.gianpneves.investmentaggregator.controller;

import com.gianpneves.investmentaggregator.controller.dtos.AccountStockDTO;
import com.gianpneves.investmentaggregator.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                               @RequestBody AccountStockDTO accountStockDto) {

        accountService.associateStock(accountId, accountStockDto);

        return ResponseEntity.ok().build();
    }
}
