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
import java.util.Map;

// Implementation of EmployeeService for handling employee-related business logic.
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    // Repository for employee data access.
    private final EmployeeRepository employeeRepo;
    // Repository for department data access.
    private final DepartmentRepository departmentRepo;

    /**
     * Creates a new employee after checking for email uniqueness and valid department.
     */
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

    /**
     * Retrieves an employee by their ID.
     */
    @Override
    public EmployeeResponseDTO getEmployee(Long id) {
        return employeeRepo.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
    }

    /**
     * Retrieves all employees.
     */
    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing employee after checking for email uniqueness and valid department.
     */
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

    /**
     * Deletes an employee by their ID.
     */
    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepo.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with ID: " + id);
        }
        employeeRepo.deleteById(id);
    }

    /**
     * Gets the count of employees grouped by department.
     */
    @Override
    public Map<Department, Long> getEmployeeCountByDepartment() {
        return employeeRepo.findAllWithDepartments().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
    }

    /**
     * Maps an Employee entity to an EmployeeResponseDTO.
     */
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
