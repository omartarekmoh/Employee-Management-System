package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository interface for Employee entity, providing employee-specific database operations.
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * Checks if an employee exists with the given email.
     * @param email The employee's email.
     * @return True if exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Retrieves all employees with their associated departments using a fetch join.
     * @return List of employees with departments loaded.
     */
    @Query("SELECT e FROM Employee e JOIN FETCH e.department")
    List<Employee> findAllWithDepartments();
}
