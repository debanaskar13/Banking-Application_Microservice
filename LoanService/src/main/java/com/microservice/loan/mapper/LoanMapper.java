package com.microservice.loan.mapper;

import com.microservice.loan.dto.LoanRequestDto;
import com.microservice.loan.dto.LoanResponseDto;
import com.microservice.loan.model.Loan;

public class LoanMapper {

    public static LoanResponseDto mapToLoanResponseDto(Loan loan, LoanResponseDto loanResponseDto) {

        loanResponseDto.setLoanId(loan.getLoanId());
        loanResponseDto.setCustomerId(loan.getCustomerId());
        loanResponseDto.setLoanType(loan.getLoanType());
        loanResponseDto.setLoanAmount(loan.getLoanAmount());
        loanResponseDto.setInterestRate(loan.getInterestRate());
        loanResponseDto.setLoanStatus(loan.getLoanStatus().name());
        loanResponseDto.setLoanDuration(loan.getLoanDuration());
        loanResponseDto.setStartDate(loan.getStartDate());
        loanResponseDto.setEmi(loan.getEmi());
        loanResponseDto.setAmountPaid(loan.getAmountPaid());
        loanResponseDto.setOutstandingAmount(loan.getOutstandingAmount());
        loanResponseDto.setLastPaymentDate(loan.getLastPaymentDate());
        loanResponseDto.setRepaymentDueDate(loan.getRepaymentDueDate());

        return loanResponseDto;
    }


    public static Loan mapToLoan(LoanRequestDto loanRequestDto, Loan loan) {

        loan.setCustomerId(loanRequestDto.getCustomerId());
        loan.setLoanType(loanRequestDto.getLoanType());
        loan.setLoanAmount(loanRequestDto.getLoanAmount());
        loan.setInterestRate(loanRequestDto.getInterestRate());
        loan.setLoanDuration(loanRequestDto.getLoanDuration());

        return loan;
    }


}
