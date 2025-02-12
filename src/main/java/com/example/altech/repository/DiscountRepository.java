package com.example.altech.repository;

import com.example.altech.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Discount Repository.
 */
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    List<Discount> findByProductId(Long productId);

    Optional<Discount> findByProductIdAndPromotionId(Long productId, Long promotionId);
}