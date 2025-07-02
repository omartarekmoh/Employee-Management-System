package com.example.demo.repository;

import com.example.demo.model.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository interface for DailySummary entity, providing CRUD operations for daily summary records.
public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {
}