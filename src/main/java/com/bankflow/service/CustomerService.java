package com.bankflow.service;

import com.bankflow.entity.Customer;
import com.bankflow.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;

    }

    public Customer createCustomer(Customer customer) {

        return customerRepository.save(customer);

    }

    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();

    }

    public Optional<Customer> getCustomerById(Long id) {

        return customerRepository.findById(id);

    }

    public Optional<Customer> updateCustomer(Long id, Customer updatedCustomer) {

        Optional<Customer> existingCustomer = customerRepository.findById(id);

        if (existingCustomer.isPresent()) {

            Customer customer = existingCustomer.get();

            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhone(updatedCustomer.getPhone());
            customer.setAddress(updatedCustomer.getAddress());

            return Optional.of(customerRepository.save(customer));

        }

        return Optional.empty();

    }

    public boolean deleteCustomer(Long id) {

        if (customerRepository.existsById(id)) {

            customerRepository.deleteById(id);
            return true;

        }

        return false;

    }
}
