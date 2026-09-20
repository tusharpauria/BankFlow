package com.bankflow.service;

import com.bankflow.dto.TransactionRequest;
import com.bankflow.dto.TransferRequest;
import com.bankflow.dto.TransferResponse;
import com.bankflow.entity.*;
import com.bankflow.exception.AccountNotFoundException;
import com.bankflow.exception.InactiveAccountException;
import com.bankflow.exception.InsufficientBalanceException;
import com.bankflow.repository.AccountRepository;
import com.bankflow.repository.CustomerRepository;
import com.bankflow.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository, TransactionRepository transactionRepository) {

        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;

    }

    public Optional<Account> createAccount(Long customerId, Account account) {

        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {

            return Optional.empty();

        }

        account.setCustomer(customer.get());

        return Optional.of(accountRepository.save(account));

    }

    public Page<Account> getAllAccounts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return accountRepository.findAll(pageable);

    }

    public Optional<Account> getAccountById(Long id) {

        return accountRepository.findById(id);

    }

    public List<Account> getAccountsByCustomer(Long customerId) {

        return accountRepository.findByCustomerId(customerId);

    }

    public Optional<Account> updateAccount(Long id, Account updatedAccount) {

        Optional<Account> existingAccount = accountRepository.findById(id);

        if (existingAccount.isPresent()) {

            Account account = existingAccount.get();

            account.setAccountType(updatedAccount.getAccountType());
            account.setStatus(updatedAccount.getStatus());

            return Optional.of(accountRepository.save(account));

        }

        return Optional.empty();

    }

    @Transactional
    public Account deposit(Long accountId, TransactionRequest request) {

        Account account = accountRepository.findByIdForUpdate(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountId));

        if (account.getStatus() != AccountStatus.ACTIVE) {

            throw new InactiveAccountException("Account is not active");

        }

        BigDecimal amount = request.getAmount();

        account.setBalance(account.getBalance().add(amount));

        accountRepository.save(account);

        Transaction transaction = new Transaction(
                amount,
                TransactionType.DEPOSIT,
                LocalDateTime.now(),
                account
        );

        transactionRepository.save(transaction);

        return account;

    }

    @Transactional
    public Account withdraw(Long accountId, TransactionRequest request) {

        Account account = accountRepository.findByIdForUpdate(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountId));

        if (account.getStatus() != AccountStatus.ACTIVE) {

            throw new InactiveAccountException("Account is not active");

        }

        BigDecimal amount = request.getAmount();

        if (account.getBalance().compareTo(amount) < 0) {

            throw new InsufficientBalanceException("Insufficient balance");

        }

        account.setBalance(account.getBalance().subtract(amount));

        accountRepository.save(account);

        Transaction transaction = new Transaction(
                amount,
                TransactionType.WITHDRAWAL,
                LocalDateTime.now(),
                account
        );

        transactionRepository.save(transaction);

        return account;

    }

    @Transactional
    public TransferResponse transfer(Long fromAccountId, TransferRequest request) {

        Account fromAccount = accountRepository.findByIdForUpdate(fromAccountId)
                .orElseThrow(() -> new AccountNotFoundException("Source account not found: " + fromAccountId));

        Account toAccount = accountRepository.findByIdForUpdate(request.getToAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found: " + request.getToAccountId()));

        if (fromAccount.getStatus() != AccountStatus.ACTIVE || toAccount.getStatus() != AccountStatus.ACTIVE) {

            throw new InactiveAccountException("Both accounts must be active");

        }

        if (fromAccount.getId().equals(toAccount.getId())) {

            throw new IllegalArgumentException("Source and destination accounts must be different");

        }

        BigDecimal amount = request.getAmount();

        if (fromAccount.getBalance().compareTo(amount) < 0) {

            throw new InsufficientBalanceException("Insufficient balance");

        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));

        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction withdrawal = new Transaction(amount, TransactionType.TRANSFER, LocalDateTime.now(), fromAccount);

        Transaction deposit = new Transaction(amount, TransactionType.TRANSFER, LocalDateTime.now(), toAccount);

        transactionRepository.save(withdrawal);
        transactionRepository.save(deposit);

        return new TransferResponse(
                "Transfer successful",
                fromAccount.getId(),
                toAccount.getId(),
                amount,
                LocalDateTime.now()
        );

    }

    public Page<Transaction> getTransactionHistory(Long accountId, int page, int size) {

        if (!accountRepository.existsById(accountId)) {

            throw new AccountNotFoundException("Account not found: " + accountId);

        }

        Pageable pageable = PageRequest.of(page, size);

        return transactionRepository.findByAccountIdOrderByTimestampDesc(accountId, pageable);

    }
}