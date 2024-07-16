package com.salesapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vehicleinventorysystem.model.User;

import java.time.LocalDateTime;

public class SalesDTO {

    private int saleId;
    private double salePrice;
    private int userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime saleDate;

    // Constructors, getters, and setters
    // Ensure to map from Sales entity when needed
    public SalesDTO() {
    }

    public SalesDTO(int saleId, double salePrice, int userId, LocalDateTime saleDate) {
        this.saleId = saleId;
        this.salePrice = salePrice;
        this.userId = userId;
        this.saleDate = saleDate;
    }

    // Getters and setters
    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
}
