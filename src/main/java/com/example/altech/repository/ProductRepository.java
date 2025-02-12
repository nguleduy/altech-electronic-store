package com.example.altech.repository;

import com.example.altech.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Product Repository.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
