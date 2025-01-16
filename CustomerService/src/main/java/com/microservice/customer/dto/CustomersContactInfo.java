package com.microservice.customer.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "customers")
public record CustomersContactInfo(String message, Map<String,String> contactDetails, List<String> onCallSupport) {
}
