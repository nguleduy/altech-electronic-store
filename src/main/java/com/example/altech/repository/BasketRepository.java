package com.example.altech.repository;

import com.example.altech.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Basket Repository.
 */
@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByCustomerId(Long customerId);

    Optional<Basket> findByIdAndCustomerId(Long id, Long customerId);
}
