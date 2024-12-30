package com.microservice.loan.external;

import com.microservice.loan.dto.external.LoanPaymentRequestDto;
import com.microservice.loan.dto.external.LoanPaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "payment-service", url = "${external.api.payment-service.url}", path = "/api/payments")
public interface PaymentService {

    @GetMapping("/make-payment")
    ResponseEntity<LoanPaymentResponseDto> makePayment(LoanPaymentRequestDto loanPaymentRequestDto);

    @GetMapping("/loan/{loanId}")
    ResponseEntity<List<LoanPaymentResponseDto>> getLoanPaymentsByLoanId(@PathVariable("loanId") long loanId);

}
