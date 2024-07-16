package com.salesapplication.dto;

import com.salesapplication.model.Sales;
import com.salesapplication.model.SalesDetails;

public class SalesDetailsDTO {

    private int saleDetailId;
    private int saleId;
    private int vehicleId;
    private double price;
    private double vehicleCount;

    // Constructors
    public SalesDetailsDTO() {
    }

    public SalesDetailsDTO(int saleDetailId, int saleId, int vehicleId, double price, double vehicleCount) {
        this.saleDetailId = saleDetailId;
        this.saleId = saleId;
        this.vehicleId = vehicleId;
        this.price = price;
        this.vehicleCount = vehicleCount;
    }

    // Getters and Setters
    public int getSaleDetailId() {
        return saleDetailId;
    }

    public void setSaleDetailId(int saleDetailId) {
        this.saleDetailId = saleDetailId;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(double vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    // Conversion methods
    public static SalesDetailsDTO fromEntity(SalesDetails salesDetails) {
        return new SalesDetailsDTO(
                salesDetails.getSaleDetailId(),
                salesDetails.getSale().getSaleId(),
                salesDetails.getVehicle().getVehicleId(),
                salesDetails.getPrice(),
                salesDetails.getVehicleCount()
        );
    }

    @Override
    public String toString() {
        return "SalesDetailsDTO{" +
                "saleDetailId=" + saleDetailId +
                ", saleId=" + saleId +
                ", vehicleId=" + vehicleId +
                ", price=" + price +
                ", vehicleCount=" + vehicleCount +
                '}';
    }
}
