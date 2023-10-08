package com.ienh.cpi.cpi.DTO;

public record StockDTO(int id, String ticker, String name, String type, Double price) {

        public StockDTO updateFromDTO(StockDTO newData) {
                return new StockDTO(
                                this.id,
                                newData.ticker,
                                newData.name,
                                newData.type,
                                newData.price);
        }
}
