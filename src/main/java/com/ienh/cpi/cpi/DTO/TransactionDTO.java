package com.ienh.cpi.cpi.DTO;

public record TransactionDTO(String token, String ticker, int quantity, double price, String createdAt,
        String updatedAt) {

}
