package com.microservice.account.service;

import com.microservice.account.constants.AccountStatus;
import com.microservice.account.dto.*;
import com.microservice.account.entity.Account;
import com.microservice.account.exception.AccountAlreadyExistException;
import com.microservice.account.exception.ResourceNotFoundException;
import com.microservice.account.external.CustomerService;
import com.microservice.account.mapper.AccountsMapper;
import com.microservice.account.repository.AccountRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;


    /**
     * Creates a new account.
     *
     * @param customerId the id of the customer to create the account for
     * @param accountDto the account details
     */
    @Override
    public void createAccount(String customerId, AccountDto accountDto) {
        try {
            ResponseDto responseDto = customerService.isCustomerExist(customerId);

            accountRepository.findByCustomerId(customerId).forEach(account -> {

                if (account.getAccountType().equals(accountDto.getAccountType()) && account.getBranch().equals(accountDto.getBranch())) {
                    throw new AccountAlreadyExistException("Account already exists with given account type and branch");
                }

            });

            Account account = createNewAccount(customerId, accountDto);

            accountRepository.save(account);
        }catch (FeignException.NotFound e) {
            log.error("Customer not found with id {}", customerId);
            throw new ResourceNotFoundException("Customer", "customer id", customerId);
        } catch (FeignException.InternalServerError e) {
            log.error("Something went Wrong with customer service");
            throw new RuntimeException("Something went Wrong");
        }
    }

    /**
     * Update an existing account.
     *
     * @param accountDto the account data transfer object
     */
    @Override
    public boolean updateAccount(AccountDto accountDto) {
        boolean isUpdated = false;
        Account account = accountRepository.findById(Long.parseLong(accountDto.getAccountNumber()))
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Account Number", accountDto.getAccountNumber().toString()));
        try {

            AccountsMapper.mapToAccount(accountDto, account, "update");
            accountRepository.save(account);
            isUpdated = true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Something went wrong");
        }


        return isUpdated;

    }

    /**
     * Retrieve all accounts.
     *
     * @return a list of accounts
     */
    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream().map(account -> AccountsMapper.mapToAccountsDto(account, new AccountDto())).toList();
    }

    /**
     * Retrieve a specific account by id.
     *
     * @param id the unique identifier of the account
     * @return the account DTO
     */
    @Override
    public AccountDto getAccountById(Long id) {

        Account account = accountRepository.findByAccountNumber(id).orElseThrow(() -> new ResourceNotFoundException("Account", "account number", String.valueOf(id)));
        return AccountsMapper.mapToAccountsDto(account, new AccountDto());
    }

    /**
     * Retrieve accounts by customer ID.
     *
     * @param customerId the unique identifier of the customer
     * @return a list of account DTOs associated with the customer
     */
    @Override
    public CustomerDto getAccountsByCustomerId(String customerId) {
        try {
            CustomerDto customer = customerService.getCustomer(customerId);

            List<AccountDto> accountListByCustomer = accountRepository.findByCustomerId(customerId)
                    .stream()
                    .map(account -> AccountsMapper.mapToAccountsDto(account, new AccountDto())).toList();

            customer.setAccounts(accountListByCustomer);
            return customer;
        } catch (FeignException.InternalServerError e) {
            log.error("Something went Wrong with customer service");
            throw new RuntimeException("Something went Wrong");
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Customer", "customer id", customerId);
        }
    }

    /**
     * Deletes an account by accountNumber.
     *
     * @param accountNumber the unique identifier of the account
     */
    @Override
    public boolean deleteAccount(Long accountNumber) {

        boolean isDeleted = false;
        AccountDto accountById = getAccountById(accountNumber);
        if (accountById != null) {
            accountRepository.deleteById(accountNumber);
            isDeleted = true;
        }

        return isDeleted;
    }

    /**
     * Delete all accounts associated with a customer.
     *
     * @param customerId the unique identifier of the customer
     * @return true if all accounts were deleted, false otherwise
     */
    @Override
    public boolean deleteAccountByCustomerId(String customerId) {

        try {
            customerService.getCustomer(customerId);

            accountRepository.deleteAllByCustomerId(customerId);

            return true;
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Customer", "customer id", customerId);

        } catch (FeignException.InternalServerError e) {
            log.error("Something went Wrong with customer service");
            throw new RuntimeException("Something went Wrong");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Updates the status of an account.
     *
     * @param accountStatusDto the account status data transfer object containing the account number and new status
     * @return true if the account status was successfully updated, false otherwise
     * @throws RuntimeException if an error occurs while updating the account status
     * @throws ResourceNotFoundException if the account is not found
     */
    @Override
    public boolean updateAccountStatus(AccountStatusDto accountStatusDto) {
        boolean isUpdated = false;
        try {
            Account account = accountRepository.findByAccountNumber(accountStatusDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "account number", accountStatusDto.getAccountNumber().toString()));
            account.setStatus(accountStatusDto.getStatus().name());
            accountRepository.save(account);
            isUpdated = true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Something went wrong while updating account status");
        }
        return isUpdated;
    }

    @Override
    public boolean closeAccount(Long accountNumber) {
        boolean isClosed = false;
        try {
            Account account = accountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "account number", accountNumber.toString()));
            account.setStatus(AccountStatus.CLOSED.name());
            accountRepository.save(account);
            isClosed = true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Something went wrong while closing account");
        }
        return isClosed;
    }

    /**
     * Retrieves the status of a specific account.
     *
     * @param accountNumber the unique identifier of the account
     * @return the account status DTO containing the account number and status
     * @throws ResourceNotFoundException if the account is not found
     */
    @Override
    public AccountStatusDto getAccountStatus(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "account number", accountNumber.toString()));
        return new AccountStatusDto(account.getAccountNumber(), AccountStatus.valueOf(account.getStatus()));
    }

    /**
     * Retrieves the balance of a specific account.
     *
     * @param accountNumber the unique identifier of the account
     * @return the account balance DTO containing the balance
     * @throws ResourceNotFoundException if the account is not found
     */
    @Override
    public AccountBalanceDto getAccountBalance(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "account number", accountNumber.toString()));
        return new AccountBalanceDto(account.getBalance());
    }


    /**
     * Creates a new account based on the provided customerId and accountDto.
     *
     * @param customerId the id of the customer to create the account for
     * @param accountDto the account details
     * @return the newly created account
     */
    private Account createNewAccount(String customerId, AccountDto accountDto) {

        System.out.println(accountDto);

        Account newAccount = new Account();
        newAccount.setCustomerId(customerId);
        newAccount.setAccountType(accountDto.getAccountType());
        newAccount.setBranch(accountDto.getBranch());
        newAccount.setBalance(0.0);
        newAccount.setCurrency("Rupees");
        newAccount.setStatus(AccountStatus.UNDER_REVIEW.name());

        long accNumber = 1000000000L + (long) (Math.random() * 9000000000L);

        while (accountRepository.findByAccountNumber(accNumber).isPresent()) {
            accNumber = 1000000000L + (long) (Math.random() * 9000000000L);
        }

        newAccount.setAccountNumber(accNumber);

        return newAccount;
    }

}
