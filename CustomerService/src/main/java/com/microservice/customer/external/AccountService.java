package com.microservice.customer.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "account-service", url = "${external.api.account-service.url}",path = "/api/accounts")
public interface AccountService {

    @DeleteMapping("/customers/{customerId}")
    void deleteAccounts(@PathVariable String customerId);
}
