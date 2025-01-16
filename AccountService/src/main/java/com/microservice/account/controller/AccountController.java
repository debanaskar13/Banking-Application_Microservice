package com.microservice.account.controller;


import com.microservice.account.annotation.ApiResponse_200_417_500;
import com.microservice.account.annotation.ApiResponse_200_500;
import com.microservice.account.annotation.ApiResponse_201_500;
import com.microservice.account.constants.AccountConstants;
import com.microservice.account.dto.*;
import com.microservice.account.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
    name = "CRUD REST APIs for Accounts",
    description = "CRUD REST APIs to CREATE, READ, UPDATE, DELETE account details"
)
@RestController
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {

    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private AccountsContactInfo accountsContactInfo;


    /**
     * The API endpoint to retrieve the build version of the application.
     * <p>
     * This endpoint is intended for monitoring and debugging purposes.
     * <p>
     * The build version is returned as a plain text string.
     * */

    @Operation(
            summary = "Get Build Information",
            description = "Get Build Information that is deployed into account microservice"
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
            description = "Get Contact Information that can be reached out if any issues with account microservice"
    )
    @ApiResponse_200_500
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfo> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfo);
    }

    /**
     * Create a new account.
     *
     * @param customerId the unique identifier of the customer
     * @return the created account
     */

    @Operation(
            summary = "Create Account REST API",
            description = "Rest API to create new account"
    )
    @ApiResponse_201_500
    @PostMapping("/create/{customerId}")
    public ResponseEntity<?> createNewAccount(@PathVariable String customerId, @Valid @RequestBody AccountDto accountDto) {
        accountService.createAccount(customerId, accountDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    /**
     * Return a list of accounts.
     *
     * @return a list of accounts
     */

    @Operation(
            summary = "Get All Accounts REST API",
            description = "Rest API to fetch all accounts"
    )
    @ApiResponse_200_500
    @GetMapping
    public ResponseEntity<?> getAccounts() {
        List<AccountDto> allAccounts = accountService.getAllAccounts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allAccounts);
    }


    /**
     * Return a specific account by id.
     *
     * @param id the unique identifier of the account
     * @return the account
     */

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "Rest API to fetch a specific account details by account number"
    )
    @ApiResponse_200_500
    @GetMapping("/fetch")
    public ResponseEntity<?> getAccount(@Pattern(regexp = "(\\d{10})", message = "account number must be 10 digits") @RequestParam(name = "accNumber") String id) {
        AccountDto account = accountService.getAccountById(Long.parseLong(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(account);
    }

    /**
     * Return a specific account by id.
     *
     * @param customerId the unique identifier of the customer
     * @return the List of account
     */

    @Operation(
            summary = "Get all accounts for a customer REST API",
            description = "Rest API to fetch all accounts for a specific customer"
    )
    @ApiResponse_200_500
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<?> getAccountsByCustomerId(@PathVariable String customerId) {
        CustomerDto accountsByCustomerId = accountService.getAccountsByCustomerId(customerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsByCustomerId);
    }

    /**
     * Update an existing account.
     *
     * @param accountDto accounts data to be updated
     * @return the updated account
     */

    @Operation(
            summary = "Update account details REST API",
            description = "Rest API to update account details"
    )
    @ApiResponse_200_417_500
    @PutMapping("/update")
    public ResponseEntity<?> updateAccountDetails(@Valid @RequestBody AccountDto accountDto) {
        boolean isUpdated = accountService.updateAccount(accountDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    /**
     * Delete an existing account.
     *
     * @param id the unique identifier of the account
     * @return the deleted account
     */

    @Operation(
            summary = "Delete account REST API",
            description = "Rest API to delete account details by account number"
    )
    @ApiResponse_200_417_500
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits") @PathVariable String id) {
        boolean isDeleted = accountService.deleteAccount(Long.parseLong(id));
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }
    }


    /**
     * Deletes all accounts associated with a customer.
     *
     * @param customerId the unique identifier of the customer
     * @return true if all accounts were deleted, false otherwise
     */
    @Operation(
            summary = "Delete all accounts for a customer REST API",
            description = "Rest API to delete all accounts for a specific customer"
    )
    @ApiResponse_200_417_500
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<?> deleteAccountByCustomerId(@PathVariable String customerId) {

        boolean isDeleted = accountService.deleteAccountByCustomerId(customerId);

        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }

    }

    /**
     * Updates the status of an account.
     *
     * @param accountStatusDto the account status data transfer object containing the account number and new status
     * @return true if the account status was successfully updated, false otherwise
     * @throws RuntimeException if an error occurs while updating the account status
     */
    @Operation(
            summary = "Update account status REST API",
            description = "Rest API to update account status"
    )
    @ApiResponse_200_417_500
    @PatchMapping("/status")
    public ResponseEntity<?> updateAccountStatus(@Valid @RequestBody AccountStatusDto accountStatusDto) {
        boolean isUpdated = accountService.updateAccountStatus(accountStatusDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    /**
     * Closes an account.
     *
     * @param accountId the unique identifier of the account
     * @return true if the account was successfully closed, false otherwise
     */
    @Operation(
            summary = "Close account REST API",
            description = "Rest API to close account"
    )
    @ApiResponse_200_417_500
    @DeleteMapping("/closure/{accountId}")
    public ResponseEntity<?> closeAccount(@PathVariable String accountId) {
        boolean isClosed = accountService.closeAccount(Long.parseLong(accountId));
        if (isClosed) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    /**
     * Retrieves the balance of an account.
     *
     * @param accNumber the unique identifier of the account
     * @return the account balance DTO
     */
    @Operation(
            summary = "Get account balance REST API",
            description = "Rest API to fetch account balance by account number"
    )
    @ApiResponse_200_500
    @GetMapping("/{accNumber}/balance")
    public ResponseEntity<AccountBalanceDto> getBalance(@Pattern(regexp = "(\\d{10})", message = "account number must be 10 digits") @PathVariable(name = "accNumber") String accNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.getAccountBalance(Long.parseLong(accNumber)));
    }

    /**
     * Retrieves the status of an account.
     *
     * @param accNumber the unique identifier of the account
     * @return the account status DTO
     */
    @Operation(
            summary = "Get account status REST API",
            description = "Rest API to fetch account status by account number"
    )
    @ApiResponse_200_500
    @GetMapping("/{accNumber}/status")
    public ResponseEntity<AccountStatusDto> getAccountStatus(@PathVariable String accNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.getAccountStatus(Long.parseLong(accNumber)));
    }
}
