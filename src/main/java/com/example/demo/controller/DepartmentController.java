package com.example.demo.controller;

import com.example.demo.dto.DepartmentRequestDTO;
import com.example.demo.dto.DepartmentResponseDTO;
import com.example.demo.services.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This controller manages CRUD operations for departments.
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    // Service for department-related business logic.
    private final DepartmentService departmentService;

    /**
     * Creates a new department.
     * @param dto The department data to create.
     * @return The created department details.
     */
    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> create(@RequestBody @Valid DepartmentRequestDTO dto) {
        return ResponseEntity.ok(departmentService.createDepartment(dto));
    }

    /**
     * Retrieves a department by its ID.
     * @param id The department ID.
     * @return The department details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> get(@PathVariable Long id){
        return ResponseEntity.ok(departmentService.getDepartment(id));
    }

    /**
     * Retrieves all departments.
     * @return List of all departments.
     */
    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAll(){
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    /**
     * Updates an existing department.
     * @param id The department ID.
     * @param dto The updated department data.
     * @return The updated department details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> update(@PathVariable Long id, @RequestBody @Valid DepartmentRequestDTO dto) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, dto));
    }

    /**
     * Deletes a department by its ID.
     * @param id The department ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
