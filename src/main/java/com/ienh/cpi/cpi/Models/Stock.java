package com.ienh.cpi.cpi.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ienh.cpi.cpi.DTO.StockDTO;
import com.ienh.cpi.cpi.Errors.SimpleError;
import com.ienh.cpi.cpi.Utils.Extras;

import jakarta.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock {

    // Variables

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ticker", nullable = false, unique = true, length = 6)
    private String ticker;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "type", nullable = false, length = 7)
    private String type;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "image_url")
    private String imageURL;

    // Functions

    public void validOnSave() throws SimpleError {
        if (this.ticker == null || this.ticker.isEmpty()) {
            throw new SimpleError("Invalid Ticker");
        }

        this.ticker = this.ticker.toUpperCase();

        if (this.name == null || this.name.isEmpty()) {
            throw new SimpleError("Invalid Name");
        }

        this.name = Extras.capitalize(this.name);

        if (this.type == null || this.type.isEmpty()) {
            throw new SimpleError("Invalid Type");
        }

        this.type = this.type.toUpperCase();

        if (this.price == null || this.price <= 0) {
            throw new SimpleError("Invalid Price");
        }
    }

    // Getters and Setters

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Constructors

    public Stock() {
    }

    public Stock(String ticker, String name, String type, Double price) throws SimpleError {
        this.ticker = ticker;
        this.name = name;
        this.type = type;
        this.price = price;

        validOnSave();
    }

    public Stock(StockDTO data) throws SimpleError {
        this.ticker = data.ticker();
        this.name = data.name();
        this.type = data.type();
        this.price = data.price();
        this.imageURL = data.imageURL();

        validOnSave();
    }
}
