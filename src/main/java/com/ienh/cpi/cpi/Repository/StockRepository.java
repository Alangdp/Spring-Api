package com.ienh.cpi.cpi.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ienh.cpi.cpi.Models.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByTicker(String ticker);

}
