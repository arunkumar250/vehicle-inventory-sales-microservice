package com.salesapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesapplication.model.SalesDetails;
import com.salesapplication.repository.SalesDetailsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class responsible for managing sales details operations.
 */
@Service
public class SalesDetailsService {

    private final SalesDetailsRepository salesDetailsRepository;

    /**
     * Constructor for SalesDetailsService.
     * 
     * @param salesDetailsRepository The repository for sales details operations.
     */
    @Autowired
    public SalesDetailsService(SalesDetailsRepository salesDetailsRepository) {
        this.salesDetailsRepository = salesDetailsRepository;
    }

    /**
     * Creates a new sales detail record.
     * 
     * @param salesDetails The SalesDetails object to be added.
     * @return A map containing the success or error message.
     */
    public Map<String, String> createSalesDetails(SalesDetails salesDetails) {
        Map<String, String> response = new HashMap<>();
        try {
            SalesDetails createdSalesDetails = salesDetailsRepository.save(salesDetails);
            response.put("success", "Sales Details Added Successfully: " + createdSalesDetails.getSaleDetailId());
        } catch (Exception e) {
            response.put("error", "Sales Details not added: " + e.getMessage());
        }
        return response;
    }

    /**
     * Retrieves all sales details records.
     * 
     * @return A map containing a list of all sales details or an error message.
     */
    public Map<String, Object> getAllSalesDetails() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<SalesDetails> allSalesDetails = salesDetailsRepository.findAll();
            response.put("success", allSalesDetails);
        } catch (Exception e) {
            response.put("error", "Could not fetch sales details: " + e.getMessage());
        }
        return response;
    }

    /**
     * Retrieves a sales detail record by its ID.
     * 
     * @param id The ID of the sales detail.
     * @return A map containing the sales detail or an error message.
     */
    public Map<String, Object> getSalesDetailsById(int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<SalesDetails> salesDetails = salesDetailsRepository.findById(id);
            if (salesDetails.isPresent()) {
                response.put("success", salesDetails.get());
            } else {
                response.put("error", "Sales Details with ID " + id + " not found");
            }
        } catch (Exception e) {
            response.put("error", "Could not fetch sales details: " + e.getMessage());
        }
        return response;
    }

    /**
     * Retrieves sales detail records by sale ID.
     * 
     * @param saleId The ID of the sale.
     * @return A map containing a list of sales details or an error message.
     */
    public Map<String, Object> getSalesDetailsBySaleId(int saleId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<SalesDetails> salesDetails = salesDetailsRepository.findBySale_SaleId(saleId);
            if (!salesDetails.isEmpty()) {
                response.put("success", salesDetails);
            } else {
                response.put("error", "Sales Details with Sale ID " + saleId + " not found");
            }
        } catch (Exception e) {
            response.put("error", "Could not fetch sales details: " + e.getMessage());
        }
        return response;
    }

    /**
     * Updates a sales detail record.
     * 
     * @param id The ID of the sales detail to be updated.
     * @param updatedSalesDetails The updated SalesDetails object.
     * @return A map containing the success or error message.
     */
    public Map<String, String> updateSalesDetails(int id, SalesDetails updatedSalesDetails) {
        Map<String, String> response = new HashMap<>();
        try {
            if (!salesDetailsRepository.existsById(id)) {
                response.put("error", "Sales Details with ID " + id + " not found");
                return response;
            }
            updatedSalesDetails.setSaleDetailId(id);
            SalesDetails updatedDetails = salesDetailsRepository.save(updatedSalesDetails);
            response.put("success", "Sales Details Updated Successfully: " + updatedDetails.getSaleDetailId());
        } catch (Exception e) {
            response.put("error", "Sales Details not updated: " + e.getMessage());
        }
        return response;
    }

    /**
     * Deletes a sales detail record by its ID.
     * 
     * @param id The ID of the sales detail to be deleted.
     * @return A map containing the success or error message.
     */
    public Map<String, String> deleteSalesDetails(int id) {
        Map<String, String> response = new HashMap<>();
        try {
            if (!salesDetailsRepository.existsById(id)) {
                response.put("error", "Sales Details with ID " + id + " not found");
                return response;
            }
            salesDetailsRepository.deleteById(id);
            response.put("success", "Sales Details Deleted Successfully");
        } catch (Exception e) {
            response.put("error", "Sales Details not deleted: " + e.getMessage());
        }
        return response;
    }
}
