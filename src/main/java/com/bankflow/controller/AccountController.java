package com.bankflow.controller;

import com.bankflow.dto.TransactionRequest;
import com.bankflow.dto.TransferRequest;
import com.bankflow.entity.Account;
import com.bankflow.entity.Transaction;
import com.bankflow.service.AccountService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Account> createAccount(@PathVariable Long customerId, @Valid @RequestBody Account account) {

        Optional<Account> savedAccount = accountService.createAccount(customerId, account);

        if (savedAccount.isPresent()) {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedAccount.get());

        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping
    public List<Account> getAllAccounts() {

        return accountService.getAllAccounts();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {

        Optional<Account> account = accountService.getAccountById(id);

        if (account.isPresent()) {

            return ResponseEntity.ok(account.get());

        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/customer/{customerId}")
    public List<Account> getAccountsByCustomer(@PathVariable Long customerId) {

        return accountService.getAccountsByCustomer(customerId);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @Valid @RequestBody Account updatedAccount) {

        Optional<Account> account = accountService.updateAccount(id, updatedAccount);

        if (account.isPresent()) {

            return ResponseEntity.ok(account.get());

        }

        return ResponseEntity.notFound().build();

    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long accountId, @Valid @RequestBody TransactionRequest request) {

        Account account = accountService.deposit(accountId, request);

        return ResponseEntity.ok(account);

    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long accountId, @Valid @RequestBody TransactionRequest request) {

        Account account = accountService.withdraw(accountId, request);

        return ResponseEntity.ok(account);

    }

    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<Void> transfer(
            @PathVariable Long accountId,
            @Valid @RequestBody TransferRequest request) {

        accountService.transfer(accountId, request);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/{accountId}/transactions")
    public List<Transaction> getTransactionHistory(@PathVariable Long accountId) {

        return accountService.getTransactionHistory(accountId);

    }
}