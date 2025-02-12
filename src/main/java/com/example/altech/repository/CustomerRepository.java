package com.example.altech.repository;

import com.example.altech.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Customer Repository.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
