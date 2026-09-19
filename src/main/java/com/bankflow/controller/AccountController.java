package com.bankflow.controller;

import com.bankflow.entity.Account;
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
}