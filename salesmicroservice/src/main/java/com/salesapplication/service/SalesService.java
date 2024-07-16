package com.salesapplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesapplication.dto.SalesDTO;
import com.salesapplication.model.Sales;
import com.salesapplication.model.VehiclePurchaseRequest;
import com.salesapplication.repository.SalesRepository;
import com.vehicleinventorysystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing sales operations.
 */
@Service
public class SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${userServiceUrl}")
    private String userServiceUrl;

    /**
     * Retrieves all sales records from the repository and converts them to DTOs.
     * 
     * @return A list of SalesDTO objects representing all sales records.
     */
    public List<SalesDTO> getAllSales() {
        try {
            return salesRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch sales data", e);
        }
    }

    /**
     * Retrieves a specific sale record by its ID and converts it to a DTO.
     * 
     * @param id The ID of the sale record.
     * @return A SalesDTO object representing the sale record, or null if not found.
     */
    public SalesDTO getSaleById(int id) {
        try {
            return salesRepository.findById(id)
                    .map(this::convertToDTO)
                    .orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch sale data", e);
        }
    }

    /**
     * Updates an existing sale record with new data.
     * 
     * @param id The ID of the sale record to be updated.
     * @param salesDTO The new data for the sale record.
     * @return A SalesDTO object representing the updated sale record.
     */
    public SalesDTO updateSale(int id, SalesDTO salesDTO) {
        try {
            return salesRepository.findById(id)
                    .map(existingSale -> {
                        existingSale.setSalePrice(salesDTO.getSalePrice());
                        // Update other fields as needed
                        return convertToDTO(salesRepository.save(existingSale));
                    })
                    .orElseThrow(() -> new IllegalArgumentException("Sale with ID " + id + " not found"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update sale data", e);
        }
    }

    /**
     * Deletes a sale record by its ID.
     * 
     * @param id The ID of the sale record to be deleted.
     * @return true if the sale record was deleted, false otherwise.
     */
    public boolean deleteSaleById(int id) {
        try {
            return salesRepository.findById(id)
                    .map(sale -> {
                        salesRepository.delete(sale);
                        return true;
                    })
                    .orElse(false);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete sale record", e);
        }
    }

    /**
     * Retrieves all sales records for a specific user by their user ID.
     * 
     * @param userId The ID of the user whose sales records are to be retrieved.
     * @return A list of SalesDTO objects representing the sales records for the user.
     */
    public List<SalesDTO> getSalesByUserId(int userId) {
        try {
            User user = getUserFromUserService(userId);
            if (user == null) {
                throw new IllegalArgumentException("User with ID " + userId + " not found");
            }

            return salesRepository.findByUser(user).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch sales data for user ID " + userId, e);
        }
    }

    /**
     * Adds sales records to the system.
     * 
     * @param saleDetails A map containing the details of the sale including userId and a list of vehicles.
     * @return A response map indicating the status and message of the operation.
     */
    @Transactional
    public Map<String, Object> addSales(Map<String, Object> saleDetails) {
        Map<String, Object> response = new HashMap<>();
        try {
            int userId = (int) saleDetails.get("userId");
            List<Map<String, Object>> vehiclesList = (List<Map<String, Object>>) saleDetails.get("vehicles");

            List<VehiclePurchaseRequest> vehicles = vehiclesList.stream()
                    .map(vehicleData -> {
                        VehiclePurchaseRequest request = new VehiclePurchaseRequest();
                        request.setVehicleId((int) vehicleData.get("vehicleId"));
                        request.setCount((int) vehicleData.get("count"));
                        return request;
                    }).collect(Collectors.toList());

            processSales(vehicles, userId);

            // If processSales completes without exceptions
            response.put("status", "success");
            response.put("message", "Sales processed successfully");
            response.put("sale Details ", saleDetails);
        } catch (IllegalArgumentException e) {
            // Handle illegal argument exception
            response.put("status", "error");
            response.put("message", "Invalid input: " + e.getMessage());
        } catch (RuntimeException e) {
            // Handle runtime exception
            response.put("status", "error");
            response.put("message", "Processing error: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            response.put("status", "error");
            response.put("message", "Unexpected error: " + e.getMessage());
        }
        System.out.println(response);
        return response;
    }

    /**
     * Processes sales records by validating the user and calling the repository to save the sales.
     * 
     * @param vehicles A list of VehiclePurchaseRequest objects containing vehicle details.
     * @param userId The ID of the user making the purchase.
     * @throws JsonProcessingException If there is an error converting the vehicles list to JSON.
     * @throws IllegalArgumentException If the user with the given ID is not found.
     * @throws RuntimeException If there is an error processing the sales.
     */
    @Transactional
    public void processSales(List<VehiclePurchaseRequest> vehicles, int userId) {
        try {
            User user = getUserFromUserService(userId);
            if (user == null) {
                throw new IllegalArgumentException("User with ID " + userId + " not found");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String vehicleDataJson = objectMapper.writeValueAsString(vehicles);
            String error_message = "";

            // Call the stored procedure to insert sales records and retrieve error_message
            error_message = salesRepository.insertSaleRecords(userId, vehicleDataJson);
            System.out.println(error_message);
            // Check error_message after stored procedure call
            if (error_message != null && !error_message.isEmpty()) {
                if (error_message.equals("success")) {
                    return;
                } else {
                    throw new RuntimeException(error_message);
                }
            }
        } catch (JsonProcessingException e) {
            // Handle JSON processing exception
            System.out.println(1);
            throw new RuntimeException("Error converting vehicles to JSON: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            // Handle illegal argument exception
            System.out.println(e);
            throw e;  // Re-throw IllegalArgumentExceptions
        } catch (RuntimeException e) {
            // Handle runtime exception
            System.out.println(e);
            throw e;  // Re-throw RuntimeExceptions
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            System.out.println(3);
            throw new RuntimeException("Failed to process sale: " + e.getMessage(), e);
        }
    }

    /**
     * Fetches a user by their ID from the user service.
     * 
     * @param userId The ID of the user to be fetched.
     * @return A User object representing the user, or throws an exception if not found.
     */
    private User getUserFromUserService(int userId) {
        try {
            return restTemplate.getForObject(userServiceUrl + userId, User.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch user data", e);
        }
    }

    /**
     * Converts a Sales object to a SalesDTO object.
     * 
     * @param sales The Sales object to be converted.
     * @return A SalesDTO object representing the sale.
     */
    private SalesDTO convertToDTO(Sales sales) {
        return new SalesDTO(sales.getSaleId(), sales.getSalePrice(), sales.getUser().getUserId(), sales.getSaleDate());
    }
}
