package com.example.demo.services;

import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.model.Department;

import java.util.Map;
import java.util.List;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);
    EmployeeResponseDTO getEmployee(Long id);
    List<EmployeeResponseDTO> getAllEmployees();
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto);
    Map<Department, Long> getEmployeeCountByDepartment();
    void deleteEmployee(Long id);
}
