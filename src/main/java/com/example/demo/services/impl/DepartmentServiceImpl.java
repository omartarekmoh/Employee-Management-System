package com.example.demo.services.impl;

import com.example.demo.dto.DepartmentRequestDTO;
import com.example.demo.dto.DepartmentResponseDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.services.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepo;


    @Override
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {
        Department department = Department.builder()
                .name(dto.getName())
                .build();

        Department saved = departmentRepo.save(department);

        return mapToResponse(saved);
    }

    @Override
    public DepartmentResponseDTO getDepartment(Long id) {
        return departmentRepo.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + id));
    }

    @Override
    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentRepo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO dto) {
        Department department = departmentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + id));

        department.setName(dto.getName());

        Department updated = departmentRepo.save(department);

        return mapToResponse(updated);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepo.existsById(id)) {
            throw new EntityNotFoundException("Department not found with ID: " + id);
        }
        departmentRepo.deleteById(id);
    }

    private DepartmentResponseDTO mapToResponse(Department e) {
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        return dto;
    }
}
