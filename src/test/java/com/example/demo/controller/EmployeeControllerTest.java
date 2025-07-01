package com.example.demo.controller;

import com.example.demo.config.TestSecurityConfig;
import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@Import(TestSecurityConfig.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeRequestDTO requestDTO;
    private EmployeeResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new EmployeeRequestDTO();
        requestDTO.setName("Alice");
        requestDTO.setEmail("alice@example.com");
        requestDTO.setSalary(60000.0);
        requestDTO.setHireDate(LocalDate.of(2023, 5, 1));
        requestDTO.setDepartmentId(1L);

        responseDTO = new EmployeeResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Alice");
        responseDTO.setEmail("alice@example.com");
        responseDTO.setSalary(60000.0);
        responseDTO.setHireDate(LocalDate.of(2023, 5, 1));
        responseDTO.setDepartmentName("Engineering");
    }

    @Test
    void testCreateEmployee() throws Exception {
        Mockito.when(employeeService.createEmployee(any(EmployeeRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Alice")))
                .andExpect(jsonPath("$.email", is("alice@example.com")))
                .andExpect(jsonPath("$.departmentName", is("Engineering")));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Mockito.when(employeeService.getEmployee(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("alice@example.com")));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        List<EmployeeResponseDTO> list = Arrays.asList(responseDTO);
        Mockito.when(employeeService.getAllEmployees()).thenReturn(list);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Alice")));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Mockito.when(employeeService.updateEmployee(Mockito.eq(1L), any(EmployeeRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Alice")));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        Mockito.doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateEmployeeValidationError() throws Exception {
        // Send an invalid request (e.g., missing name)
        requestDTO.setName(null);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error.name").exists());
    }

    @Test
    void testGetEmployeeNotFound() throws Exception {
        Mockito.when(employeeService.getEmployee(99L))
                .thenThrow(new jakarta.persistence.EntityNotFoundException("Employee not found"));

        mockMvc.perform(get("/api/employees/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error", is("Employee not found")));
    }

    @Test
    void testUnexpectedServerError() throws Exception {
        Mockito.when(employeeService.getEmployee(1L))
                .thenThrow(new RuntimeException("Database failure"));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error", is("Unexpected error: Database failure")));
    }

}
