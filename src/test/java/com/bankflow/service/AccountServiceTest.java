package com.bankflow.service;

import com.bankflow.dto.TransactionRequest;
import com.bankflow.dto.TransferRequest;
import com.bankflow.entity.*;
import com.bankflow.exception.AccountNotFoundException;
import com.bankflow.exception.InactiveAccountException;
import com.bankflow.exception.InsufficientBalanceException;
import com.bankflow.repository.AccountRepository;
import com.bankflow.repository.CustomerRepository;
import com.bankflow.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldTransferMoneySuccessfully() {

        Customer customer = new Customer(
                "Tushar",
                "tushar@gmail.com",
                "9876543210",
                "Nagpur"
        );
        customer.setId(1L);

        Account fromAccount = new Account(
                "1000000001",
                AccountType.SAVINGS,
                new BigDecimal("10000.00"),
                AccountStatus.ACTIVE,
                customer
        );

        fromAccount.setId(1L);

        Account toAccount = new Account(
                "1000000002",
                AccountType.SAVINGS,
                new BigDecimal("5000.00"),
                AccountStatus.ACTIVE,
                customer
        );

        toAccount.setId(2L);

        when(accountRepository.findByIdForUpdate(1L))
                .thenReturn(Optional.of(fromAccount));

        when(accountRepository.findByIdForUpdate(2L))
                .thenReturn(Optional.of(toAccount));
        TransferRequest request =
                new TransferRequest(
                        2L,
                        new BigDecimal("2500.00")
                );

        accountService.transfer(1L, request);

        assertEquals(
                new BigDecimal("7500.00"),
                fromAccount.getBalance()
        );

        assertEquals(
                new BigDecimal("7500.00"),
                toAccount.getBalance()
        );

        verify(accountRepository).save(fromAccount);
        verify(accountRepository).save(toAccount);

        verify(transactionRepository, times(2))
                .save(any(Transaction.class));
    }
}
