package com.microservice.loan.controller;


import com.microservice.loan.annotation.ApiResponse_200_417_500;
import com.microservice.loan.annotation.ApiResponse_200_500;
import com.microservice.loan.annotation.ApiResponse_201_500;
import com.microservice.loan.constants.LoanConstants;
import com.microservice.loan.dto.*;
import com.microservice.loan.dto.external.LoanPaymentRequestDto;
import com.microservice.loan.dto.external.LoanPaymentResponseDto;
import com.microservice.loan.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
@Validated
@Tag(
    name = "CRUD REST APIs for Loan",
    description = "CRUD REST APIs CREATE, READ, UPDATE, DELETE for Loan details"
)
public class LoanController {

    private final ILoanService loanService;
    private final LoansContactInfo loansContactInfo;

    @Value("${build.version}")
    private String buildVersion;

    public LoanController(ILoanService loanService, LoansContactInfo loansContactInfo) {
        this.loansContactInfo = loansContactInfo;
        this.loanService = loanService;
    }


    /**
     * The API endpoint to retrieve the build version of the application.
     * <p>
     * This endpoint is intended for monitoring and debugging purposes.
     * <p>
     * The build version is returned as a plain text string.
     * */

    @Operation(
            summary = "Get Build Information",
            description = "Get Build Information that is deployed into loan microservice"
    )
    @ApiResponse_200_500
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    /**
     * Get the contact information of the support team.
     *
     * @return the contact information of the support team
     */
    @Operation(
            summary = "Get Contact Information",
            description = "Get Contact Information that can be reached out if any issues with loan microservice"
    )
    @ApiResponse_200_500
    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfo> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(loansContactInfo);
    }




    /**
     * Rest API to create a new Loan
     *
     * @param loanRequestDto the loan request object
     * @return ResponseEntity with the newly created Loan
     */
    @Operation(
            summary = "Create a new Loan REST API",
            description = "Rest API to create a new Loan"
    )
    @ApiResponse_201_500
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@Valid LoanRequestDto loanRequestDto) {
        this.loanService.createLoan(loanRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoanConstants.STATUS_201, LoanConstants.MESSAGE_201));
    }

    /**
     * Rest API to update Loan Status
     *
     * @param loanId the loan id
     * @param loanStatusUpdateRequestDto the loan status update request object
     * @return ResponseEntity with the updated Loan
     */
    @Operation(
            summary = "Update Loan Status REST API",
            description = "Rest API to update Loan Status"
    )
    @ApiResponse_200_417_500
    @PatchMapping("/update/{loanId}")
    public ResponseEntity<ResponseDto> updateLoanStatus( @PathVariable long loanId, @Valid LoanStatusUpdateRequestDto loanStatusUpdateRequestDto) {
        boolean isUpdated = this.loanService.updateLoanStatus(loanId, loanStatusUpdateRequestDto);

        if(!isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_UPDATE));
        }
    }

    /**
     * Rest API to get Loan By Id
     *
     * @param loanId the loan id
     * @return ResponseEntity with the loan response object
     */
    @Operation(
            summary = "Get Loan By Id REST API",
            description = "Rest API to get Loan By Id"
    )
    @ApiResponse_200_500
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable long loanId) {
        LoanResponseDto loanById = this.loanService.getLoanById(loanId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanById);
    }

    /**
     * Rest API to get All Loans
     *
     * @return ResponseEntity with the list of loan response objects
     */
    @Operation(
            summary = "Get All Loans REST API",
            description = "Rest API to get All Loans"
    )
    @ApiResponse_200_500
    @GetMapping("/all")
    public ResponseEntity<List<LoanResponseDto>> getAllLoans() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.loanService.getAllLoans());
    }

    /**
     * Rest API to get Loans By Customer Id
     *
     * @param customerId the customer id
     * @return ResponseEntity with the list of loan response objects
     */
    @Operation(
            summary = "Get Loans By Customer Id REST API",
            description = "Rest API to get Loans By Customer Id"
    )
    @ApiResponse_200_417_500
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanResponseDto>> getAllLoansByCustomerId(@PathVariable String customerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.loanService.getLoansByCustomerId(customerId));
    }

    /**
     * Rest API to make Payment
     *
     * @param loanId the loan id
     * @param loanPaymentRequestDto the loan payment request object
     * @return ResponseEntity with the ResponseDto
     */
    @Operation(
            summary = "Make Payment REST API",
            description = "Rest API to make Payment"
    )
    @ApiResponse_200_417_500
    @PostMapping("/payment/{loanId}")
    public ResponseEntity<ResponseDto> makePayment(@PathVariable long loanId, @Valid LoanPaymentRequestDto loanPaymentRequestDto) {
        boolean isPaymentCompleted = this.loanService.makePayments(loanId, loanPaymentRequestDto);
        if(isPaymentCompleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_PAYMENT));
        }
    }

    /**
     * Rest API to get Loan Payments By Loan Id
     *
     * @param loanId the loan id
     * @return ResponseEntity with the list of loan payment response objects
     */
    @Operation(
            summary = "Get Loan Payments By Loan Id REST API",
            description = "Rest API to get Loan Payments By Loan Id"
    )
    @ApiResponse_200_500
    @GetMapping("/payment/{loanId}")
    public ResponseEntity<List<LoanPaymentResponseDto>> getLoanPaymentsByLoanId(@PathVariable long loanId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.loanService.getLoanPaymentsByLoanId(loanId));
    }

    /**
     * Rest API to approve Loan
     *
     * @param loanId the loan id
     * @return ResponseEntity with the ResponseDto
     */
    @Operation(
            summary = "Approve Loan REST API",
            description = "Rest API to approve Loan"
    )
    @ApiResponse_200_417_500
    @PatchMapping("/approve/{loanId}")
    public ResponseEntity<ResponseDto> approveLoan(@PathVariable long loanId) {
        boolean isApproved = this.loanService.approveLoan(loanId);
        if(isApproved) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_PAYMENT));
        }
    }

    /**
     * Rest API to cancel Loan
     *
     * @param loanId the loan id
     * @return ResponseEntity with the ResponseDto
     */
    @Operation(
            summary = "Cancel Loan REST API",
            description = "Rest API to cancel Loan"
    )
    @ApiResponse_200_417_500
    @PutMapping("/cancel/{loanId}")
    public ResponseEntity<ResponseDto> cancelLoan(@PathVariable long loanId) {
        boolean isCancelled = this.loanService.cancelLoan(loanId);
        if (isCancelled) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_PAYMENT));
        }
    }


}
