package com.microservice.loan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LoanAlreadyApprovedException extends RuntimeException {

    public LoanAlreadyApprovedException(String message) {
        super(message);
    }
}
