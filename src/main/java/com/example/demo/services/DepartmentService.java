package com.example.demo.services;

import com.example.demo.dto.DepartmentRequestDTO;
import com.example.demo.dto.DepartmentResponseDTO;

import java.util.List;

// Service interface for department-related business logic.
public interface DepartmentService {
    /**
     * Creates a new department.
     * @param dto The department data to create.
     * @return The created department details.
     */
    DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto);

    /**
     * Retrieves a department by its ID.
     * @param id The department ID.
     * @return The department details.
     */
    DepartmentResponseDTO getDepartment(Long id);

    /**
     * Retrieves all departments.
     * @return List of all departments.
     */
    List<DepartmentResponseDTO> getAllDepartments();

    /**
     * Updates an existing department.
     * @param id The department ID.
     * @param dto The updated department data.
     * @return The updated department details.
     */
    DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO dto);

    /**
     * Deletes a department by its ID.
     * @param id The department ID.
     */
    void deleteDepartment(Long id);
}
