package com.salesapplication.repository;

import com.salesapplication.model.Sales;
import com.vehicleinventorysystem.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {
    
	   // Custom query to call a stored procedure
	   @Query(value = "CALL insert_sale_records(:userId, :vehicleDataJson, @error_message);", nativeQuery = true)
	    String insertSaleRecords(@Param("userId") int userId, @Param("vehicleDataJson") String vehicleDataJson);

	   // Query method to find sales records by user
	   List<Sales> findByUser(User user);
}
