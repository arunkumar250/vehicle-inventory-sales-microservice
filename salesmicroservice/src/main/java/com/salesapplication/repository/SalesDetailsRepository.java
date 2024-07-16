package com.salesapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.salesapplication.model.SalesDetails;

public interface SalesDetailsRepository extends JpaRepository<SalesDetails, Integer> {
    
    @Procedure(name = "insert_sales_detail")
	void insert_sales_details(int saleId, int vehicleId, int count);
    
    List<SalesDetails> findBySale_SaleId(int saleId);
}
