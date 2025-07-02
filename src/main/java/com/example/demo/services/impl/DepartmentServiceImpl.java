package com.example.demo.services.impl;

import com.example.demo.dto.DepartmentRequestDTO;
import com.example.demo.dto.DepartmentResponseDTO;
import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.services.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Implementation of DepartmentService for handling department-related business logic.
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    // Repository for department data access.
    private final DepartmentRepository departmentRepo;

    /**
     * Creates a new department after checking for name uniqueness.
     */
    @Override
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {
        if (departmentRepo.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Department name is already in use");
        }

        Department department = Department.builder()
                .name(dto.getName())
                .build();

        Department saved = departmentRepo.save(department);

        return mapToResponse(saved);
    }

    /**
     * Retrieves a department by its ID.
     */
    @Override
    public DepartmentResponseDTO getDepartment(Long id) {
        return departmentRepo.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + id));
    }

    /**
     * Retrieves all departments.
     */
    @Override
    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentRepo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing department after checking for name uniqueness.
     */
    @Override
    public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO dto) {
        Department department = departmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + id));

        if (!department.getName().equals(dto.getName()) && departmentRepo.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Department name is already in use");
        }

        department.setName(dto.getName());

        Department updated = departmentRepo.save(department);
        return mapToResponse(updated);
    }

    /**
     * Deletes a department by its ID.
     */
    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepo.existsById(id)) {
            throw new EntityNotFoundException("Department not found with ID: " + id);
        }
        departmentRepo.deleteById(id);
    }

    /**
     * Maps a Department entity to a DepartmentResponseDTO.
     */
    private DepartmentResponseDTO mapToResponse(Department e) {
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        return dto;
    }
}
