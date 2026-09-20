package com.bankflow.controller;

import com.bankflow.dto.CustomerResponse;
import com.bankflow.entity.Customer;
import com.bankflow.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {

        this.customerService = customerService;

    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody Customer customer) {

        Customer savedCustomer = customerService.createCustomer(customer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CustomerResponse.fromEntity(savedCustomer));

    }

    @GetMapping
    public Page<CustomerResponse> getAllCustomers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size) {

        return customerService.getAllCustomers(page, size)
                .map(CustomerResponse::fromEntity);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {

        Optional<Customer> customer = customerService.getCustomerById(id);

        if (customer.isPresent()) {

            return ResponseEntity.ok(CustomerResponse.fromEntity(customer.get()));

        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer updatedCustomer) {

        Optional<Customer> customer = customerService.updateCustomer(id, updatedCustomer);

        if (customer.isPresent()) {

            return ResponseEntity.ok(CustomerResponse.fromEntity(customer.get()));

        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {

        boolean deleted = customerService.deleteCustomer(id);

        if (deleted) {

            return ResponseEntity.noContent().build();

        }

        return ResponseEntity.notFound().build();

    }
}