package com.example.demo.repository;

import com.example.demo.model.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {
}