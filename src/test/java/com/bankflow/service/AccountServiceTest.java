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
    void shouldRejectTransferToSameAccount() {

        Customer customer = new Customer(
                "Tushar",
                "tushar@gmail.com",
                "9876543210",
                "Nagpur"
        );
        customer.setId(1L);

        Account account = new Account(
                "1000000001",
                AccountType.SAVINGS,
                new BigDecimal("10000.00"),
                AccountStatus.ACTIVE,
                customer
        );

        account.setId(1L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        TransferRequest request =
                new TransferRequest(
                        1L,
                        new BigDecimal("1000.00")
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> accountService.transfer(1L, request)
        );

        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
}
