package com.ienh.cpi.cpi.DTO;

public record ChartDTO(
        int id,
        String ticker,
        double quantity,
        double price,
        int type,
        String token) {
}
