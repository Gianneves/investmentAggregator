package com.gianpneves.investmentaggregator.controller;

import com.gianpneves.investmentaggregator.controller.dtos.CreateStockDTO;
import com.gianpneves.investmentaggregator.controller.dtos.CreateUserDTO;
import com.gianpneves.investmentaggregator.entity.User;
import com.gianpneves.investmentaggregator.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDTO createStockDTO) {

        stockService.createStock(createStockDTO);
        return ResponseEntity.ok().build();
    }
}
