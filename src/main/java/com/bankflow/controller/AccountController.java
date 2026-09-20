package com.bankflow.controller;

import com.bankflow.dto.*;
import com.bankflow.entity.Account;
import com.bankflow.entity.Transaction;
import com.bankflow.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {

        this.accountService = accountService;

    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<AccountResponse> createAccount(@PathVariable Long customerId,
                                                         @Valid @RequestBody AccountRequest request) {

        Optional<Account> account =
                accountService.createAccount(customerId, request);

        if (account.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AccountResponse.fromEntity(account.get()));

    }

    @GetMapping
    public Page<AccountResponse> getAllAccounts(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size) {

        return accountService.getAllAccounts(page, size)
                .map(AccountResponse::fromEntity);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {

        Optional<Account> account = accountService.getAccountById(id);

        if (account.isPresent()) {

            return ResponseEntity.ok(AccountResponse.fromEntity(account.get()));

        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/customer/{customerId}")
    public List<AccountResponse> getAccountsByCustomer(@PathVariable Long customerId) {

        return accountService
                .getAccountsByCustomer(customerId)
                .stream()
                .map(AccountResponse::fromEntity)
                .toList();

    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long id, @Valid @RequestBody Account updatedAccount) {

        Optional<Account> account = accountService.updateAccount(id, updatedAccount);

        if (account.isPresent()) {

            AccountResponse response = AccountResponse.fromEntity(account.get());

            return ResponseEntity.ok(response);

        }

        return ResponseEntity.notFound().build();

    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable Long accountId, @Valid @RequestBody TransactionRequest request) {

        Account account = accountService.deposit(accountId, request);

        return ResponseEntity.ok(AccountResponse.fromEntity(account));

    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable Long accountId, @Valid @RequestBody TransactionRequest request) {

        Account account = accountService.withdraw(accountId, request);

        return ResponseEntity.ok(AccountResponse.fromEntity(account));

    }

    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<TransferResponse> transfer(@PathVariable Long accountId, @Valid @RequestBody TransferRequest request) {

        TransferResponse response = accountService.transfer(accountId, request);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{accountId}/transactions")
    public Page<TransactionResponse> getTransactionHistory(@PathVariable Long accountId, @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {

        return accountService.getTransactionHistory(accountId, page, size)
                .map(TransactionResponse::fromEntity);

    }
}