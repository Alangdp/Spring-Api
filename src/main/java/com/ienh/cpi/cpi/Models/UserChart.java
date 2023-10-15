package com.ienh.cpi.cpi.Models;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ienh.cpi.cpi.DTO.ChartDTO;
import com.ienh.cpi.cpi.Objects.TokenInfo;
import com.ienh.cpi.cpi.Repository.ChartRepository;

import jakarta.persistence.*;

@Entity
@Table(name = "users_charts")
public class UserChart {

    // Variables

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String ticker;

    @Column(name = "stock_id", nullable = false)
    private int stockId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    private double medianPrice;

    @Column(nullable = true)
    private double actualPrice;

    @Column(name = "total_invested", nullable = false)
    private double totalInvested;

    @Column(name = "total_actual", nullable = false)
    private double totalActual;

    @Column(name = "profit_prejudice", nullable = false)
    private double profitPrejudice;

    @Column(name = "profit_prejudice_percent", nullable = false)
    private double profitPrejudicePercent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate created_At;

    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDate updated_At;

    // Functions

    // Constructors

    public UserChart() {
    }

    public UserChart(Object[] info, TokenInfo tokenInfo, ChartDTO data, Stock stock) {

        this.ticker = info[1].toString();
        this.stockId = (int) info[0];
        this.userId = tokenInfo.getValue().getId();
        this.quantity = (double) info[3];
        this.medianPrice = (double) info[2];
        this.actualPrice = stock.getPrice();
        this.totalInvested = (double) info[4];
        this.totalActual = this.quantity * this.actualPrice;
        this.profitPrejudice = this.totalActual - this.totalInvested;
        this.profitPrejudicePercent = (this.totalActual - this.totalInvested) / 100;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getMedianPrice() {
        return medianPrice;
    }

    public void setMedianPrice(double medianPrice) {
        this.medianPrice = medianPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public double getTotalInvested() {
        return totalInvested;
    }

    public void setTotalInvested(double totalInvested) {
        this.totalInvested = totalInvested;
    }

    public double getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(double totalActual) {
        this.totalActual = totalActual;
    }

    public double getProfitPrejudice() {
        return profitPrejudice;
    }

    public void setProfitPrejudice(double profitPrejudice) {
        this.profitPrejudice = profitPrejudice;
    }

    public double getProfitPrejudicePercent() {
        return profitPrejudicePercent;
    }

    public void setProfitPrejudicePercent(double profitPrejudicePercent) {
        this.profitPrejudicePercent = profitPrejudicePercent;
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
