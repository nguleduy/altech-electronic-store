package com.example.altech.repository;

import com.example.altech.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Basket Repository.
 */
@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByCustomerId(Long customerId);
}
