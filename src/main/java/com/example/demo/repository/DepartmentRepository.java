package com.example.demo.repository;

import com.example.demo.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository interface for Department entity, providing department-specific database operations.
@Repository
public interface DepartmentRepository  extends JpaRepository<Department, Long> {
    /**
     * Checks if a department exists with the given name.
     * @param name The department name.
     * @return True if exists, false otherwise.
     */
    boolean existsByName(String name);
}
