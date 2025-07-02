package com.example.demo.services;

import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.model.Department;

import java.util.Map;
import java.util.List;

// Service interface for employee-related business logic.
public interface EmployeeService {
    /**
     * Creates a new employee.
     * @param dto The employee data to create.
     * @return The created employee details.
     */
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);

    /**
     * Retrieves an employee by their ID.
     * @param id The employee ID.
     * @return The employee details.
     */
    EmployeeResponseDTO getEmployee(Long id);

    /**
     * Retrieves all employees.
     * @return List of all employees.
     */
    List<EmployeeResponseDTO> getAllEmployees();

    /**
     * Updates an existing employee.
     * @param id The employee ID.
     * @param dto The updated employee data.
     * @return The updated employee details.
     */
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto);

    /**
     * Gets the count of employees grouped by department.
     * @return Map of Department to employee count.
     */
    Map<Department, Long> getEmployeeCountByDepartment();

    /**
     * Deletes an employee by their ID.
     * @param id The employee ID.
     */
    void deleteEmployee(Long id);
}
