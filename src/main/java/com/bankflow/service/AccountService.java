package com.bankflow.service;

import com.bankflow.entity.Account;
import com.bankflow.entity.Customer;
import com.bankflow.repository.AccountRepository;
import com.bankflow.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {

        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;

    }

    public Optional<Account> createAccount(Long customerId, Account account) {

        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {

            return Optional.empty();

        }

        account.setCustomer(customer.get());

        return Optional.of(accountRepository.save(account));

    }

    public List<Account> getAllAccounts() {

        return accountRepository.findAll();

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
}