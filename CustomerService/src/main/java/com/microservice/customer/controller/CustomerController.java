package com.microservice.customer.controller;


import com.microservice.customer.annotation.ApiResponse_200_417_500;
import com.microservice.customer.annotation.ApiResponse_200_500;
import com.microservice.customer.annotation.ApiResponse_201_500;
import com.microservice.customer.constants.AppConstants;
import com.microservice.customer.dto.CustomerDto;
import com.microservice.customer.dto.CustomerUpdateDto;
import com.microservice.customer.dto.ResponseDto;
import com.microservice.customer.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(
    name = "CRUD Rest APIs for Customer",
    description = "CRUD REST APIs to CREATE, READ, UPDATE and DELETE customer information."
)
public class CustomerController {

    private final ICustomerService ICustomerService;


    /**
     * Create a new customer with the given information.
     *
     * @param customerDto The customer information to create.
     * @return ResponseEntity containing the created CustomerDto and HTTP status.
     */
    @Operation(
            summary = "Create Customer REST API",
            description = "REST API to create a new customer"
    )
    @ApiResponse_201_500
    @PostMapping
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {

        ICustomerService.createCustomer(customerDto);
        return new ResponseEntity<>(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201), HttpStatus.CREATED);

    }


    /**
     * Retrieve a list of all customers in the database.
     *
     * @return ResponseEntity containing the List of CustomerDto and HTTP status.
     */
    @Operation(
            summary = "Get All Customer REST API",
            description = "REST API to fetch all customer details"
    )
    @ApiResponse_200_500
    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        return new ResponseEntity<>(ICustomerService.getAllCustomers(), HttpStatus.OK);
    }

    /**
     * Retrieve a specific customer by their ID.
     *
     * @param customerId The ID of the customer to retrieve.
     * @return ResponseEntity containing the CustomerDto and HTTP status.
     */
    @Operation(
            summary = "Retrieve a Customer Details REST API",
            description = "REST API to fetch a customer details by ID"
    )
    @ApiResponse_200_500
    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable String customerId) {
        return new ResponseEntity<>(ICustomerService.getCustomer(customerId), HttpStatus.OK);
    }

    /**
     * Update an existing customer with the given ID.
     *
     * @param customerId  The ID of the customer to update.
     * @param customerUpdateDto The updated customer information.
     * @return ResponseEntity containing the Response and HTTP status.
     */
    @Operation(
            summary = "Update customer details REST API",
            description = "REST API to update a customer details"
    )
    @ApiResponse_200_417_500
    @PutMapping("/{customerId}")
    public ResponseEntity<ResponseDto> updateCustomer(@PathVariable String customerId, @Valid @RequestBody CustomerUpdateDto customerUpdateDto) {

        boolean isUpdated = ICustomerService.updateCustomer(customerId, customerUpdateDto);

        if(isUpdated){
            return new ResponseEntity<>(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200), HttpStatus.OK);
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AppConstants.STATUS_417, AppConstants.MESSAGE_417_UPDATE));
        }

    }

    /**
     * Delete a customer by their ID.
     *
     * @param customerId The ID of the customer to delete.
     * @return ResponseEntity containing the Response and HTTP status.
     */
    @Operation(
            summary = "Delete customer details REST API",
            description = "REST API to delete a customer details"
    )
    @ApiResponse_200_417_500
    @DeleteMapping("/{customerId}")
    public ResponseEntity<ResponseDto> deleteCustomer(@PathVariable String customerId) {
        boolean isDeleted = ICustomerService.deleteCustomer(customerId);
        if(isDeleted){
            return new ResponseEntity<>(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200), HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AppConstants.STATUS_417, AppConstants.MESSAGE_417_DELETE));
        }
    }


    @Operation(
            summary = "Check if a customer exists REST API",
            description = "REST API to check if a customer exists"
    )
    @ApiResponse_200_500
    @GetMapping("/exists/{customerId}")
    public ResponseEntity<?> isCustomerExist(@PathVariable String customerId) {
        boolean customerExist = ICustomerService.isCustomerExist(customerId);
        if(customerExist){
            return new ResponseEntity<>(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200_EXIST), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ResponseDto(AppConstants.STATUS_404, AppConstants.MESSAGE_404), HttpStatus.NOT_FOUND);
        }
    }


}
