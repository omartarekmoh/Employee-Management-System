package com.example.demo.controller;

import com.example.demo.dto.DepartmentRequestDTO;
import com.example.demo.dto.DepartmentResponseDTO;
import com.example.demo.services.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> create(@RequestBody @Valid DepartmentRequestDTO dto) {
        return ResponseEntity.ok(departmentService.createDepartment(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> get(@PathVariable Long id){
        return ResponseEntity.ok(departmentService.getDepartment(id));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAll(){
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> update(@PathVariable Long id, @RequestBody @Valid DepartmentRequestDTO dto) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
