package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Double salary;
    private LocalDate hireDate;
    private String departmentName;
}
