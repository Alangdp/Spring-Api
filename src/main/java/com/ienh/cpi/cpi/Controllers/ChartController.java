package com.ienh.cpi.cpi.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ienh.cpi.cpi.DTO.ChartDTO;
import com.ienh.cpi.cpi.DTO.TransactionDTO;
import com.ienh.cpi.cpi.Models.ErrorResponse;
import com.ienh.cpi.cpi.Models.Stock;
import com.ienh.cpi.cpi.Models.Transaction;
import com.ienh.cpi.cpi.Models.User;
import com.ienh.cpi.cpi.Models.UserChart;
import com.ienh.cpi.cpi.Models.UserToken;
import com.ienh.cpi.cpi.Objects.TokenInfo;
import com.ienh.cpi.cpi.Repository.ChartRepository;
import com.ienh.cpi.cpi.Repository.StockRepository;
import com.ienh.cpi.cpi.Repository.TokenRepository;
import com.ienh.cpi.cpi.Repository.TransactionRepository;
import com.ienh.cpi.cpi.Repository.UserRepository;

@RestController
@RequestMapping("/api/charts")
public class ChartController {

    // Variables
    private String errorMessage = "Chart Controller Error";

    @Autowired
    private ChartRepository chartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Functions

    public Transaction buyStock(ChartDTO data) {
        if (data.ticker().isEmpty()) {
            throw new IllegalArgumentException("Ticker is empty");
        }

        if (data.quantity() <= 0) {
            throw new IllegalArgumentException("Quantity is invalid");
        }

        if (data.price() <= 0) {
            throw new IllegalArgumentException("Price is invalid");
        }

        TokenInfo tokenInfo = validToken(data);
        Stock stock = validStock(data).get();

        Transaction transaction = new Transaction(stock, tokenInfo, data);

        transactionRepository.save(transaction);

        return transaction;
    }

    public double getAveragePrice(ChartDTO data) {
        TokenInfo tokenInfo = validToken(data);
        User user = tokenInfo.getValue();

        Double averagePrice = transactionRepository.calculateAveragePrice(user.getId(), data.ticker());

        return averagePrice;
    }

    public List<Transaction> getTransactions(TokenInfo tokenInfo) {
        User user = tokenInfo.getValue();

        List<Transaction> transactions = transactionRepository.findTransactionsByUserId(user.getId());

        return transactions;
    }

    public TokenInfo validToken(ChartDTO data) {
        String token = data.token();
        UserToken user = tokenRepository.findByToken(token);
        if (user == null) {
            throw new IllegalArgumentException("Token is invalid");
        }
        TokenInfo tokenInfo = new TokenInfo(true, user.getUser());

        return tokenInfo;
    }

    public TokenInfo validToken(TransactionDTO data) {
        String token = data.token();
        UserToken user = tokenRepository.findByToken(token);
        if (user == null) {
            throw new IllegalArgumentException("Token is invalid");
        }
        TokenInfo tokenInfo = new TokenInfo(true, user.getUser());

        return tokenInfo;
    }

    public Optional<Stock> validStock(ChartDTO data) {
        String ticker = data.ticker();
        Optional<Stock> stock = stockRepository.findByTicker(ticker);
        if (!stock.isPresent()) {
            throw new IllegalArgumentException("Stock not exists");
        }
        return stock;
    }

    public List<UserChart> getUserCharts(ChartDTO data, TokenInfo tokenInfo) {
        User user = tokenInfo.getValue();

        List<Object[]> chartInfo = transactionRepository.getPortfolioSummary(user.getId());
        List<UserChart> userCharts = new ArrayList<>();
        for (Object[] info : chartInfo) {
            Stock stockInfo = stockRepository.findByTicker(info[1].toString()).get();
            userCharts.add(new UserChart(info, tokenInfo, data, stockInfo));
        }

        if (chartInfo.isEmpty())
            throw new IllegalArgumentException("Chart is empty");

        return userCharts;
    }

    // store
    @PostMapping
    public ResponseEntity<?> store(@RequestBody @Validated ChartDTO data) {
        try {
            return ResponseEntity.ok(buyStock(data));
        } catch (Exception e) {
            System.out.println(e);
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Average Price
    @PostMapping("/average-price")
    public ResponseEntity<?> averagePrice(@RequestBody @Validated ChartDTO data) {
        try {
            return ResponseEntity.ok(getAveragePrice(data));
        } catch (Exception e) {
            System.out.println(e);
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // chart-info
    @PostMapping("/chart-info")
    public ResponseEntity<?> chartInfo(@RequestBody @Validated ChartDTO data) {
        try {
            TokenInfo tokenInfo = validToken(data);

            return ResponseEntity.ok(getUserCharts(data, tokenInfo));
        } catch (Exception e) {
            System.out.println(e);
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> index(@RequestBody @Validated TransactionDTO data) {
        try {
            TokenInfo tokenInfo = validToken(data);

            Transaction[] transactions = getTransactions(tokenInfo).toArray(new Transaction[0]);
            Set<String> uniqueTickers = new HashSet<>();

            for (Transaction transaction : transactions) {
                uniqueTickers.add(transaction.getTicker());
            }

            Map<String, Object> response = new HashMap<>();
            response.put("transactions", transactions);
            response.put("uniqueTickers", uniqueTickers);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Read

    // Update

    // Delete
}
