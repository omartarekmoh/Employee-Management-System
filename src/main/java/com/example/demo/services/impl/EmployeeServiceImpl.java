package com.example.demo.services.impl;

import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepo;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        Department department = departmentRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        if (employeeRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        Employee employee = Employee.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .salary(dto.getSalary())
                .hireDate(dto.getHireDate())
                .department(department)
                .build();

        Employee saved = employeeRepo.save(employee);
        return mapToResponse(saved);
    }

    @Override
    public EmployeeResponseDTO getEmployee(Long id) {
        return employeeRepo.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));

        Department department = departmentRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        // Check email uniqueness only if changing email
        if (!employee.getEmail().equals(dto.getEmail()) && employeeRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employee.setHireDate(dto.getHireDate());
        employee.setDepartment(department);

        return mapToResponse(employeeRepo.save(employee));
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepo.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with ID: " + id);
        }
        employeeRepo.deleteById(id);
    }

    private EmployeeResponseDTO mapToResponse(Employee e) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setEmail(e.getEmail());
        dto.setSalary(e.getSalary());
        dto.setHireDate(e.getHireDate());
        dto.setDepartmentName(e.getDepartment().getName());
        return dto;
    }
}
