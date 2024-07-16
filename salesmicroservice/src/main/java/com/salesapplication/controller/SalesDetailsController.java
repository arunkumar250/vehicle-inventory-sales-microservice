package com.salesapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.salesapplication.model.SalesDetails;
import com.salesapplication.service.SalesDetailsService;

import java.util.List;
import java.util.Map;

/**
 * Controller class for managing SalesDetails-related endpoints.
 */
@RestController
@RequestMapping("/sales/sales-details")
public class SalesDetailsController {

    private final SalesDetailsService salesDetailsService;

    @Autowired
    public SalesDetailsController(SalesDetailsService salesDetailsService) {
        this.salesDetailsService = salesDetailsService;
    }

    /**
     * Endpoint to create a new SalesDetails record.
     * 
     * @param salesDetails The SalesDetails object to be created.
     * @return ResponseEntity containing a success or error message.
     */
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> createSalesDetails(@RequestBody SalesDetails salesDetails) {
        Map<String, String> response = salesDetailsService.createSalesDetails(salesDetails);
        if (response.containsKey("success")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to get all SalesDetails records.
     * 
     * @return ResponseEntity containing the list of all SalesDetails records or an error message.
     */
    @GetMapping("/getall")
    public ResponseEntity<Map<String, Object>> getAllSalesDetails() {
        Map<String, Object> response = salesDetailsService.getAllSalesDetails();
        if (response.containsKey("success")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to get a SalesDetails record by its ID.
     * 
     * @param id The ID of the SalesDetails record.
     * @return ResponseEntity containing the SalesDetails record or an error message.
     */
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Map<String, Object>> getSalesDetailsById(@PathVariable("id") int id) {
        Map<String, Object> response = salesDetailsService.getSalesDetailsById(id);
        if (response.containsKey("success")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to get SalesDetails records by Sale ID.
     * 
     * @param saleId The Sale ID associated with the SalesDetails records.
     * @return ResponseEntity containing the list of SalesDetails records or an error message.
     */
    @GetMapping("/getbysaleid/{saleId}")
    public ResponseEntity<Map<String, Object>> getSalesDetailsBySaleId(@PathVariable("saleId") int saleId) {
        Map<String, Object> response = salesDetailsService.getSalesDetailsBySaleId(saleId);
        if (response.containsKey("success")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to update a SalesDetails record by its ID.
     * 
     * @param id The ID of the SalesDetails record to update.
     * @param updatedSalesDetails The updated SalesDetails object.
     * @return ResponseEntity containing a success or error message.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateSalesDetails(@PathVariable("id") int id, @RequestBody SalesDetails updatedSalesDetails) {
        Map<String, String> response = salesDetailsService.updateSalesDetails(id, updatedSalesDetails);
        if (response.containsKey("success")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to delete a SalesDetails record by its ID.
     * 
     * @param id The ID of the SalesDetails record to delete.
     * @return ResponseEntity containing a success or error message.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteSalesDetails(@PathVariable("id") int id) {
        Map<String, String> response = salesDetailsService.deleteSalesDetails(id);
        if (response.containsKey("success")) {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
