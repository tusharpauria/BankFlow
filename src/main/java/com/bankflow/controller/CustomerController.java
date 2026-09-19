package com.bankflow.controller;

import com.bankflow.entity.Customer;
import com.bankflow.service.CustomerService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {

        Customer savedCustomer = customerService.createCustomer(customer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCustomer);

    }

    @GetMapping
    public List<Customer> getAllCustomers() {

        return customerService.getAllCustomers();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {

        Optional<Customer> customer = customerService.getCustomerById(id);

        if (customer.isPresent()) {

            return ResponseEntity.ok(customer.get());

        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer updatedCustomer) {

        Optional<Customer> customer = customerService.updateCustomer(id, updatedCustomer);

        if (customer.isPresent()) {

            return ResponseEntity.ok(customer.get());

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