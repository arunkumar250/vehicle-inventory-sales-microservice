package com.salesapplication.model;

import com.vehicleinventorysystem.model.Vehicle;

import jakarta.persistence.*;

@Entity
@Table(name="sales_details")
public class SalesDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_detail_id")
    private int saleDetailId;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sales sale;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "price")
    private double price;
    
    @Column(name = "vehicle_count")
    private double vehicleCount;

    public double getVehicleCount() {
		return vehicleCount;
	}

	public void setVehicleCount(double vehicleCount) {
		this.vehicleCount = vehicleCount;
	}

	// Constructors
    public SalesDetails() {
        this.price = 0.0; // Set default value here
    }

    public SalesDetails(Sales sale, Vehicle vehicle, double price) {
        this.sale = sale;
        this.vehicle = vehicle;
        this.price = price;
    }

    // Getters and Setters
    public int getSaleDetailId() {
        return saleDetailId;
    }

    public void setSaleDetailId(int saleDetailId) {
        this.saleDetailId = saleDetailId;
    }

    public Sales getSale() {
        return sale;
    }

    public void setSale(Sales sale) {
        this.sale = sale;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SalesDetails{" +
                "saleDetailId=" + saleDetailId +
                ", sale=" + sale +
                ", vehicle=" + vehicle +
                ", price=" + price +
                '}';
    }
}
