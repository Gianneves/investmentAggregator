package com.gianpneves.investmentaggregator.service;

import com.gianpneves.investmentaggregator.controller.dtos.CreateStockDTO;
import com.gianpneves.investmentaggregator.entity.Stock;
import com.gianpneves.investmentaggregator.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO createStockDTO) {

        var stock = new Stock(
                createStockDTO.stockId(),
                createStockDTO.description()
        );

        stockRepository.save(stock);
    }
}
