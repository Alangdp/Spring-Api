package com.ienh.cpi.cpi.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ienh.cpi.cpi.DTO.StockDTO;
import com.ienh.cpi.cpi.Errors.SimpleError;
import com.ienh.cpi.cpi.Models.ErrorResponse;
import com.ienh.cpi.cpi.Models.Stock;
import com.ienh.cpi.cpi.Models.User;
import com.ienh.cpi.cpi.Models.UserToken;
import com.ienh.cpi.cpi.Repository.StockRepository;
import com.ienh.cpi.cpi.Utils.Extras;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("/api/stocks")
public class StockController {
    String errorMessage = "Stock Controller Error";

    @Autowired
    private StockRepository stockRepository;

    // CONTROLLER FUNCTIONS

    public void MakeError(String message, Exception error) throws Exception {
        throw new SimpleError(message, error);
    }

    // ROUTES

    @PostMapping("/")
    public ResponseEntity<?> store(@RequestBody @Validated StockDTO data) {
        try {
            Optional<Stock> existingStock = stockRepository.findByTicker(data.ticker());
            if (existingStock.isPresent()) {
                Stock stockToUpdate = existingStock.get();
                stockToUpdate.setPrice(data.price());
                Stock updatedStock = stockRepository.save(stockToUpdate);
                return ResponseEntity.ok(updatedStock);
            }

            Stock stock = new Stock(data);
            Stock newStock = stockRepository.save(stock);
            return ResponseEntity.ok(newStock);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<?> show(@PathVariable("ticker") String ticker) {
        try {
            Optional<Stock> stock = stockRepository.findByTicker(ticker);
            if (stock.isEmpty()) {
                throw new SimpleError("Stock not found");
            }

            return ResponseEntity.ok(stock);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> show() {
        try {
            List<Stock> stocks = stockRepository.findAll();
            if (stocks.isEmpty()) {
                throw new SimpleError("Stocks not found");
            }

            Map<String, List<Stock>> response = new HashMap<>();
            response.put("stocks", stocks);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{ticker}")
    public ResponseEntity<?> delete(@PathVariable("ticker") String ticker) {
        try {
            System.out.println(ticker);
            stockRepository.findByTicker(ticker).ifPresent(stock -> stockRepository.delete(stock));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody @Validated StockDTO data) {
        try {
            Stock stock = stockRepository.findByTicker(data.ticker()).orElseThrow();
            if (stock == null)
                throw new Exception("Stock not found");

            stock.setTicker(data.ticker());
            stock.setPrice(data.price());
            stock.setImageURL(data.imageURL());
            stock.setName(data.name());
            stock.setType(data.type());

            stock = stockRepository.save(stock);

            Map<String, Stock> response = new HashMap<>();
            response.put("stocks", stock);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
