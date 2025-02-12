package com.example.altech.repository;

import com.example.altech.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Promotion Repository.
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    List<Promotion> findByType(String type);
}
