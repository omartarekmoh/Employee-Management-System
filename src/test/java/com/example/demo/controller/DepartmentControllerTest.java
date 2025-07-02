package com.example.demo.controller;

import com.example.demo.config.TestSecurityConfig;
import com.example.demo.dto.DepartmentRequestDTO;
import com.example.demo.dto.DepartmentResponseDTO;
import com.example.demo.services.DepartmentService;
import com.example.demo.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
@Import(TestSecurityConfig.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private DepartmentRequestDTO requestDTO;
    private DepartmentResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new DepartmentRequestDTO();
        requestDTO.setName("HR");

        responseDTO = new DepartmentResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("HR");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateDepartmentWithAdminRole() throws Exception {
        Mockito.when(departmentService.createDepartment(any(DepartmentRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("HR")));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreateDepartmentWithUserRoleShouldFail() throws Exception {
        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateDepartmentWithNoName() throws Exception {
        requestDTO.setName(null);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error.name").exists());

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getDepartmentById() throws Exception {
        Mockito.when(departmentService.getDepartment(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("HR")));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getDepartmentByInvalidId() throws Exception {
        Mockito.when(departmentService.getDepartment(1L)).thenThrow(new jakarta.persistence.EntityNotFoundException("Department not found"));


        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getDepartmentByInvalidStringId() throws Exception {
        mockMvc.perform(get("/api/departments/omar"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error", is("Method parameter 'id': Failed to convert value to required type 'Long'")));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetDepartmentsWithUserRole() throws Exception {
        Mockito.when(departmentService.getAllDepartments()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("HR")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateDepartmentWithAdminRole() throws Exception {
        DepartmentRequestDTO updateRequest = new DepartmentRequestDTO();
        updateRequest.setName("HR Updated");

        DepartmentResponseDTO updatedResponse = new DepartmentResponseDTO();
        updatedResponse.setId(1L);
        updatedResponse.setName("HR Updated");

        Mockito.when(departmentService.updateDepartment(Mockito.eq(1L), any(DepartmentRequestDTO.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("HR Updated")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteDepartmentWithAdminRole() throws Exception {
        Mockito.doNothing().when(departmentService).deleteDepartment(1L);

        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testDeleteDepartmentWithUserRoleShouldFail() throws Exception {
        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteDepartmentWhenInUseShouldReturnConflict() throws Exception {
        Mockito.doThrow(new DataIntegrityViolationException("Department is in use"))
                .when(departmentService).deleteDepartment(1L);

        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isConflict());
    }

    @Test
    void testGetDepartmentsWithoutAuthenticationShouldFail() throws Exception {
        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreateDepartmentWithoutAuthenticationShouldFail() throws Exception {
        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteDepartmentWithoutAuthenticationShouldFail() throws Exception {
        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateDepartmentWithInvalidData() throws Exception {
        DepartmentRequestDTO invalidRequest = new DepartmentRequestDTO();
        invalidRequest.setName("");
        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error.name").exists());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateNonExistentDepartment() throws Exception {
        DepartmentRequestDTO updateRequest = new DepartmentRequestDTO();
        updateRequest.setName("NonExistent");
        Mockito.when(departmentService.updateDepartment(Mockito.eq(99L), any(DepartmentRequestDTO.class)))
                .thenThrow(new jakarta.persistence.EntityNotFoundException("Department not found"));
        mockMvc.perform(put("/api/departments/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetAllDepartmentsWhenNoneExist() throws Exception {
        Mockito.when(departmentService.getAllDepartments()).thenReturn(List.of());
        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
