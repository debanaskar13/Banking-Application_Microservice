package com.microservice.loan.service;

import com.microservice.loan.constants.LoanStatus;
import com.microservice.loan.dto.LoanRequestDto;
import com.microservice.loan.dto.LoanResponseDto;
import com.microservice.loan.dto.LoanStatusUpdateRequestDto;
import com.microservice.loan.dto.external.LoanPaymentRequestDto;
import com.microservice.loan.dto.external.LoanPaymentResponseDto;
import com.microservice.loan.exception.GeneralLoanServiceException;
import com.microservice.loan.exception.InvalidLoanStatusException;
import com.microservice.loan.exception.PaymentFailedException;
import com.microservice.loan.exception.ResourceNotFoundException;
import com.microservice.loan.external.CustomerService;
import com.microservice.loan.external.PaymentService;
import com.microservice.loan.mapper.LoanMapper;
import com.microservice.loan.model.Loan;
import com.microservice.loan.repository.LoanRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements ILoanService {

    private final LoanRepository loanRepository;
    private final PaymentService paymentService;
    private final CustomerService customerService;


    /**
     * Create a new loan.
     *
     * @param loanRequestDto the loan request object.
     */
    @Override
    public void createLoan(LoanRequestDto loanRequestDto) {

        String customerId = loanRequestDto.getCustomerId();

        Boolean customerExist = this.customerService.customerExists(customerId).getBody();

        if(!customerExist){
            throw new ResourceNotFoundException("Customer", "id", customerId);
        }

        Loan loan = LoanMapper.mapToLoan(loanRequestDto, new Loan());
        loan.setLoanStatus(LoanStatus.PENDING);
        loan.setStartDate(LocalDate.now());

        double emi = this.calculateEmi(loan);
        loan.setEmi(emi);

        try {
            this.loanRepository.save(loan);
        } catch (Exception e) {
            throw new GeneralLoanServiceException("Failed to create loan");
        }
    }

    /**
     * Update the loan status.
     *
     * @param loanStatusUpdateRequestDto the loan status update request object.
     * @return true if the loan status was updated, false otherwise.
     */
    @Override
    public boolean updateLoanStatus(long loanId, LoanStatusUpdateRequestDto loanStatusUpdateRequestDto) {
        boolean isStatusUpdated = false;
        Loan loan = this.loanRepository.findById(loanId).orElseThrow(() -> new ResourceNotFoundException("Loan", "id", String.valueOf(loanId)));

        if(loan.getLoanStatus().equals("PENDING") && loanStatusUpdateRequestDto.getLoanStatus().equals("APPROVED")) {
            loan.setLoanStatus(LoanStatus.APPROVED);
            isStatusUpdated = true;
        }else if(loan.getLoanStatus().equals("APPROVED") && loanStatusUpdateRequestDto.getLoanStatus().equals("DISBURSED")) {
            loan.setLoanStatus(LoanStatus.DISBURSED);
            isStatusUpdated = true;
        }else if(loan.getLoanStatus().equals("DISBURSED") && loanStatusUpdateRequestDto.getLoanStatus().equals("ACTIVE")) {
            loan.setLoanStatus(LoanStatus.ACTIVE);
            isStatusUpdated = true;
        }else if(loan.getLoanStatus().equals("ACTIVE") && loanStatusUpdateRequestDto.getLoanStatus().equals("COMPLETED")) {

//            If the amount paid is equal to the total amount of the loan + interest, update the loan status to "COMPLETED".

            if(loan.getEmi() * loan.getLoanDuration() == loan.getAmountPaid()){
                loan.setLoanStatus(LoanStatus.COMPLETED);
            }
            isStatusUpdated = true;

        }else{
            throw new InvalidLoanStatusException("Invalid loan status");
        }

        this.loanRepository.save(loan);
        return isStatusUpdated;
    }

    private double calculateEmi(Loan loan) {
        double annualInterestRate = loan.getInterestRate();
        int loanDuration = loan.getLoanDuration();
        double loanAmount = loan.getLoanAmount();

        double monthlyInterestRate = annualInterestRate / 12 / 100;
        double emi = loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanDuration) / (Math.pow(1 + monthlyInterestRate, loanDuration) - 1);
        return emi;

    }

    /**
     * Get a loan by its id.
     *
     * @param loanId the loan id.
     * @return the loan response object.
     */
    @Override
    public LoanResponseDto getLoanById(long loanId) {

        return this.loanRepository
                .findById(loanId)
                .map(loan -> LoanMapper.mapToLoanResponseDto(loan, new LoanResponseDto()))
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "id", String.valueOf(loanId)));

    }

    /**
     * Get all loans.
     *
     * @return a list of loan response objects.
     */
    @Override
    public List<LoanResponseDto> getAllLoans() {

        return this.loanRepository
                .findAll()
                .stream()
                .map(loan -> LoanMapper.mapToLoanResponseDto(loan, new LoanResponseDto()))
                .collect(Collectors.toList());
    }

    /**
     * Get all loans for a customer.
     *
     * @param customerId the customer id.
     * @return a list of loan response objects.
     */
    @Override
    public List<LoanResponseDto> getLoansByCustomerId(String customerId) {
        return this.loanRepository
                .findByCustomerId(customerId)
                .stream()
                .map(loan -> LoanMapper.mapToLoanResponseDto(loan, new LoanResponseDto()))
                .collect(Collectors.toList());
    }

    /**
     * Make a payment for a loan.
     *
     * @param loanId                the loan id.
     * @param loanPaymentRequestDto the loan payment request object.
     * @return the loan response object.
     */
    @Override
    public boolean makePayments(long loanId, LoanPaymentRequestDto loanPaymentRequestDto) {
        boolean isPaymentSuccessful = false;
        Loan loan = this.loanRepository.findById(loanId).orElseThrow(() -> new ResourceNotFoundException("Loan", "id", String.valueOf(loanId)));

        try {
            LoanPaymentResponseDto loanPaymentResponseDto = this.paymentService.makePayment(loanPaymentRequestDto).getBody();

            if(loanPaymentResponseDto.getStatus().equals("PAID")){
                loan.setAmountPaid(loan.getAmountPaid() + loanPaymentResponseDto.getAmount());
                this.loanRepository.save(loan);
                isPaymentSuccessful = true;
            }else{
                throw new PaymentFailedException("Payment failed");
            }


        }catch (FeignException e){
            log.error("Payment failed for loan id: {}", loanId);
        }catch (Exception e){
            log.error("Something went wrong while saving loan to the database");
        }

        return isPaymentSuccessful;
    }

    /**
     * Get the loan payments for a loan.
     *
     * @param loanId the loan id.
     * @return a list of loan payment response objects.
     */
    @Override
    public List<LoanPaymentResponseDto> getLoanPaymentsByLoanId(long loanId) {
        this.loanRepository
                .findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "id", String.valueOf(loanId)));

        try{
            return this.paymentService
                    .getLoanPaymentsByLoanId(loanId)
                    .getBody();
        }catch (FeignException.NotFound e){
            log.error("Unable to get Payments for loan id: {}", loanId);
            return List.of();
        }

    }

    /**
     * Approve a loan.
     *
     * @param loanId the loan id.
     * @return the loan response object.
     */
    @Override
    public boolean approveLoan(long loanId) {


        return false;
    }

    /**
     * Cancel a loan.
     *
     * @param loanId the loan id.
     * @return true if the loan was cancelled, false otherwise.
     */
    @Override
    public boolean cancelLoan(long loanId) {
        return false;
    }


}
