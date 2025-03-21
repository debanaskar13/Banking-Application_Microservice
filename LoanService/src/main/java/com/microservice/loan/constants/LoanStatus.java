package com.microservice.loan.constants;

public enum LoanStatus {
    PENDING,
    APPROVED,
    DISBURSED,
    ACTIVE,
    COMPLETED,
    DEFAULTED,
    IN_ARREARS,
    REJECTED,
    CANCELLED,
    CLOSED,
    SETTLED
}



//1. Pending
//      Description: The loan application has been submitted but not yet reviewed or approved. It is in the process of evaluation.
//      Example: A loan is under review by the bank or financial institution.
//2. Approved
//      Description: The loan has been approved by the lender, but the funds may not have been disbursed yet.
//      Example: The borrower’s loan request has been accepted, and they are waiting for the funds to be issued.
//3. Disbursed
//      Description: The loan amount has been disbursed to the borrower. The borrower can now access the funds.
//      Example: After approval, the loan amount is transferred to the borrower’s account.
//4. Active
//      Description: The loan is in repayment, and the borrower is making regular payments.
//      Example: The borrower is currently paying off the loan according to the agreed-upon terms.
//5. Completed
//      Description: The borrower has repaid the loan in full, and the loan is considered closed.
//      Example: All outstanding payments have been made, and the loan balance is zero.
//6. Defaulted
//      Description: The borrower has failed to make the required payments for an extended period, and the loan is considered in default.
//      Example: The borrower has missed multiple payments, and the lender has declared the loan as defaulted.
//7. In Arrears
//      Description: The borrower is behind on payments but has not yet reached a state of default.
//      Example: The borrower has missed a few payments, and the account is overdue.
//8. Rejected
//      Description: The loan application has been rejected by the lender.
//      Example: The borrower did not meet the eligibility criteria, or the application was not approved for other reasons.
//9. Cancelled
//      Description: The loan has been canceled, either by the borrower or the lender, before it is disbursed or completed.
//      Example: The borrower withdrew their application before approval or the lender canceled the loan after approval.
//10. Closed
//      Description: The loan is closed but not necessarily because it was fully repaid. It could have been closed due to early settlement, refinancing, or other reasons.
//      Example: The borrower decided to pay off the loan earlier than scheduled or refinanced with another loan provider.
//11. Settled
//      Description: The borrower has paid off the loan, including any interest and fees, but may have settled for less than the full amount due (e.g., a loan settlement or write-off).
//      Example: The borrower and lender agree on a reduced final settlement amount, and the loan is considered settled.


//Sample Loan Status Flow:
//Pending → The loan application is submitted.
//Approved → The application is accepted, but the loan has not yet been funded.
//Disbursed → The loan funds are transferred to the borrower.
//Active → The borrower starts repaying the loan.
//Completed → The loan is fully repaid.
//If the borrower misses payments, it may change to:
//In Arrears → Payments are overdue.
//Defaulted → The loan is considered defaulted after missed payments over a prolonged period.
//Cancelled or Rejected can occur during various points based on borrower or lender decisions.