package com.ienh.cpi.cpi.Models;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ienh.cpi.cpi.DTO.ChartDTO;
import com.ienh.cpi.cpi.DTO.StockDTO;
import com.ienh.cpi.cpi.Objects.TokenInfo;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = true)
    private String type;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    private double price;

    @Column(name = "total_invested", nullable = false)
    private double totalInvested;

    @Column(name = "stock_id", nullable = false)
    private int stockId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate created_At;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDate updated_At;

    // Functions

    // Constructors

    public Transaction() {

    }

    public Transaction(Stock stock, TokenInfo tokenInfo, ChartDTO data) {
        this.ticker = stock.getTicker();
        this.type = data.type() == 0 ? "buy" : "sell";
        this.quantity = data.quantity();
        this.price = data.price();
        this.totalInvested = quantity * price;
        this.stockId = stock.getId();
        this.userId = tokenInfo.getValue().getId();
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalInvested() {
        return totalInvested;
    }

    public void setTotalInvested(double totalInvested) {
        this.totalInvested = totalInvested;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getCreated_At() {
        return created_At;
    }

    public void setCreated_At(LocalDate created_At) {
        this.created_At = created_At;
    }

    public LocalDate getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(LocalDate updated_At) {
        this.updated_At = updated_At;
    }

}
