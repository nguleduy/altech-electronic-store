package com.example.altech.service;

import com.example.altech.dto.CustomerDTO;
import com.example.altech.mapper.CustomerMapper;
import com.example.altech.model.Customer;
import com.example.altech.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Customer Service.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    /**
     * Retrieves all customers.
     *
     * @return List CustomerDTO
     */
    public List<CustomerDTO> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        logger.info("Customers: {}", customers);
        return customers.stream().map(CustomerMapper::toDto).toList();
    }
}
