package com.salesapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.salesapplication.dto.SalesDTO;
import com.salesapplication.service.SalesService;

import java.util.List;
import java.util.Map;

/**
 * Controller class for managing sales-related endpoints.
 */
@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    /**
     * Endpoint to get all sales records.
     * 
     * @return ResponseEntity containing the list of all sales records or an error message.
     */
    @GetMapping("/getall")
    public ResponseEntity<Map<String, Object>> getAllSales() {
        try {
            List<SalesDTO> salesList = salesService.getAllSales();
            return ResponseEntity.ok(Map.of("success", true, "data", salesList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("success", false, "message", "Failed to fetch sales data"));
        }
    }

    /**
     * Endpoint to get a sales record by its ID.
     * 
     * @param id The ID of the sales record.
     * @return ResponseEntity containing the sales record or an error message.
     */
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Map<String, Object>> getSaleById(@PathVariable int id) {
        try {
            SalesDTO salesDTO = salesService.getSaleById(id);
            if (salesDTO != null) {
                return ResponseEntity.ok(Map.of("success", true, "data", salesDTO));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "Sale record not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "Failed to fetch sale data"));
        }
    }

    /**
     * Endpoint to update a sales record.
     * 
     * @param id The ID of the sales record to update.
     * @param salesDTO The updated sales data.
     * @return ResponseEntity containing the updated sales record or an error message.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateSale(@PathVariable int id, @RequestBody SalesDTO salesDTO) {
        try {
            SalesDTO updatedSalesDTO = salesService.updateSale(id, salesDTO);
            if (updatedSalesDTO != null) {
                return ResponseEntity.ok(Map.of("success", true, "data", updatedSalesDTO, "message", "Sale updated successfully"));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "Sale record not found"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "Failed to update sale data"));
        }
    }

    /**
     * Endpoint to delete a sales record.
     * 
     * @param id The ID of the sales record to delete.
     * @return ResponseEntity indicating the success or failure of the delete operation.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteSale(@PathVariable int id) {
        try {
            boolean deleted = salesService.deleteSaleById(id);
            if (deleted) {
                return ResponseEntity.ok(Map.of("success", true, "message", "Sale record deleted successfully"));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "Sale record not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "Failed to delete sale record"));
        }
    }

    /**
     * Endpoint to get sales records by user ID.
     * 
     * @param userId The ID of the user whose sales records are to be fetched.
     * @return ResponseEntity containing the list of sales records for the user or an error message.
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<Map<String, Object>> getSalesByUserId(@PathVariable int userId) {
        try {
            List<SalesDTO> salesDTOs = salesService.getSalesByUserId(userId);
            if (!salesDTOs.isEmpty()) {
                return ResponseEntity.ok(Map.of("success", true, "data", salesDTOs));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "No sales found for user ID " + userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "Failed to fetch sales data for user ID " + userId));
        }
    }

    /**
     * Endpoint to add new sales records.
     * 
     * @param saleDetails A map containing the details of the sale including user ID and vehicle details.
     * @return ResponseEntity indicating the success or failure of the add operation.
     */
    @PostMapping("/addsales")
    public ResponseEntity<Map<String, Object>> addSales(@RequestBody Map<String, Object> saleDetails) {
        Map<String, Object> response = salesService.addSales(saleDetails);
        boolean success = response.get("status").equals("success");
        
        if (success) {
            return ResponseEntity.ok(response);
        } else {
            String errorMessage = (String) response.get("message");
            if (errorMessage.contains("User with ID") || errorMessage.contains("Insufficient inventory")) {
                return ResponseEntity.badRequest().body(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
    }

}
