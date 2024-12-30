package com.microservice.account.external;

import com.microservice.account.dto.CustomerDto;
import com.microservice.account.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "${external.api.customer-service.url}", path = "/api/customers")
public interface CustomerService {

    /**
     * Get a customer by id.
     *
     * @param customerId the unique identifier of the customer
     * @return the customer
     */
    @GetMapping("/exists/{customerId}")
    ResponseDto isCustomerExist(@PathVariable String customerId);

    @GetMapping("/{customerId}")
    CustomerDto getCustomer(@PathVariable String customerId);

}
