package com.example.demo.controller;

import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This controller manages CRUD operations for employees.
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    // Service for employee-related business logic.
    private final EmployeeService employeeService;

    /**
     * Creates a new employee.
     * @param dto The employee data to create.
     * @return The created employee details.
     */
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create(@RequestBody @Valid EmployeeRequestDTO dto) {
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    /**
     * Retrieves an employee by their ID.
     * @param id The employee ID.
     * @return The employee details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    /**
     * Retrieves all employees.
     * @return List of all employees.
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAll() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /**
     * Updates an existing employee.
     * @param id The employee ID.
     * @param dto The updated employee data.
     * @return The updated employee details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(@PathVariable Long id, @RequestBody @Valid EmployeeRequestDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    /**
     * Deletes an employee by their ID.
     * @param id The employee ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
