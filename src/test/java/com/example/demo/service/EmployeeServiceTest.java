package com.example.demo.service;

import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.services.impl.EmployeeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeRequestDTO requestDTO;
    private Department department;
    private Employee employee;

    @BeforeEach
    void setUp() {
        requestDTO = new EmployeeRequestDTO();
        requestDTO.setName("Alice");
        requestDTO.setEmail("alice@example.com");
        requestDTO.setSalary(50000.0);
        requestDTO.setHireDate(LocalDate.of(2023, 1, 15));
        requestDTO.setDepartmentId(1L);

        department = Department.builder()
                .id(1L)
                .name("Engineering")
                .build();

        employee = Employee.builder()
                .id(100L)
                .name("Alice")
                .email("alice@example.com")
                .salary(50000.0)
                .hireDate(LocalDate.of(2023, 1, 15))
                .department(department)
                .build();
    }

    @Test
    public void testCreateEmployee_Success() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.existsByEmail("alice@example.com")).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponseDTO response = employeeService.createEmployee(requestDTO);

        assertNotNull(response);
        assertEquals("Alice", response.getName());
        assertEquals("alice@example.com", response.getEmail());
        assertEquals("Engineering", response.getDepartmentName());

        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    public void testCreateEmployee_DepartmentNotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            employeeService.createEmployee(requestDTO);
        });

        assertEquals("Department not found", exception.getMessage());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    public void testCreateEmployee_EmailAlreadyExists() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.existsByEmail("alice@example.com")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee(requestDTO);
        });

        assertEquals("Email is already in use", exception.getMessage());
        verify(employeeRepository, never()).save(any());
    }
}
