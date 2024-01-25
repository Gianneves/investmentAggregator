package com.gianpneves.investmentaggregator.client.dtos;

import java.util.List;

public record BrapiResponseDTO(List<StockDTO> results) {
}
