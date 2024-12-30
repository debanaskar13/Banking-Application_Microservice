package com.microservice.loan.external;

import com.microservice.loan.dto.external.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "${external.api.customer-service.url}", path = "/api/customers")
public interface CustomerService {

    @GetMapping("{customerId}")
    ResponseEntity<CustomerDto> getCustomer(@PathVariable String customerId);

    @GetMapping("/exists/{customerId}")
    ResponseEntity<Boolean> customerExists(@PathVariable String customerId);

}
