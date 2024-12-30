package com.microservice.account.service;

import com.microservice.account.dto.AccountBalanceDto;
import com.microservice.account.dto.AccountDto;
import com.microservice.account.dto.AccountStatusDto;
import com.microservice.account.dto.CustomerDto;

import java.util.List;

/**
 * Interface for account service operations.
 */
public interface IAccountService {

    /**
     * Create a new account.
     *
     * @param accountDto the account data transfer object
     * @return the created account
     */
    void createAccount(String customerId,AccountDto accountDto);

    /**
     * Update an existing account.
     *
     * @param accountDto the account data transfer object
     * @return the updated account
     */
    boolean updateAccount(AccountDto accountDto);

    /**
     * Retrieve all accounts.
     *
     * @return a list of accounts
     */
    List<AccountDto> getAllAccounts();

    /**
     * Retrieve a specific account by id.
     *
     * @param id the unique identifier of the account
     * @return the accountDto
     */
    AccountDto getAccountById(Long id);

    /**
     * Retrieve accounts by customer ID.
     *
     * @param customerId the unique identifier of the customer
     * @return a list of account DTOs associated with the customer
     */
    CustomerDto getAccountsByCustomerId(String customerId);


    /**
     * Delete an existing account.
     *
     * @param id the unique identifier of the account
     * @return true if the account was deleted, false otherwise
     */
    boolean deleteAccount(Long id);
    /**
     * Delete all accounts associated with a customer.
     *
     * @param customerId the unique identifier of the customer
     * @return true if all accounts were deleted, false otherwise
     */
    boolean deleteAccountByCustomerId(String customerId);

    boolean updateAccountStatus(AccountStatusDto status);

    boolean closeAccount(Long accountNumber);

    AccountStatusDto getAccountStatus(Long accountNumber);

    AccountBalanceDto getAccountBalance(Long accountNumber);
}
