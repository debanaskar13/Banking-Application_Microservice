package com.microservice.loan.service;

import com.microservice.loan.dto.LoanRequestDto;
import com.microservice.loan.dto.LoanResponseDto;
import com.microservice.loan.dto.LoanStatusUpdateRequestDto;
import com.microservice.loan.dto.external.LoanPaymentRequestDto;
import com.microservice.loan.dto.external.LoanPaymentResponseDto;

import java.util.List;

public interface ILoanService {

    /**
     * Create a new loan.
     *
     * @param loanRequestDto the loan request object.
     */
    void createLoan(LoanRequestDto loanRequestDto);

    /**
     * Update the loan status.
     *
     * @param loanStatusUpdateRequestDto the loan status update request object.
     * @return true if the loan status was updated, false otherwise.
     */
    boolean updateLoanStatus(long loanId, LoanStatusUpdateRequestDto loanStatusUpdateRequestDto);

    /**
     * Get a loan by its id.
     *
     * @param loanId the loan id.
     * @return the loan response object.
     */
    LoanResponseDto getLoanById(long loanId);

    /**
     * Get all loans.
     *
     * @return a list of loan response objects.
     */
    List<LoanResponseDto> getAllLoans();

    /**
     * Get all loans for a customer.
     *
     * @param customerId the customer id.
     * @return a list of loan response objects.
     */
    List<LoanResponseDto> getLoansByCustomerId(String customerId);

    /**
     * Make a payment for a loan.
     *
     * @param loanId the loan id.
     * @param loanPaymentRequestDto the loan payment request object.
     * @return the loan response object.
     */
    boolean makePayments(long loanId, LoanPaymentRequestDto loanPaymentRequestDto);

    /**
     * Get the loan payments for a loan.
     *
     * @param loanId the loan id.
     * @return a list of loan payment response objects.
     */
    List<LoanPaymentResponseDto> getLoanPaymentsByLoanId(long loanId);

    /**
     * Approve a loan.
     *
     * @param loanId the loan id.
     * @return the loan response object.
     */
    boolean approveLoan(long loanId);

    /**
     * Cancel a loan.
     *
     * @param loanId the loan id.
     * @return true if the loan was cancelled, false otherwise.
     */
    boolean cancelLoan(long loanId);

}
