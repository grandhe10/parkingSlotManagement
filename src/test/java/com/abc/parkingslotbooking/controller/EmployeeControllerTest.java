package com.abc.parkingslotbooking.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 

import java.util.LinkedHashMap;

 

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

 

import com.abc.parkingslotbooking.dto.LoginDto;
import com.abc.parkingslotbooking.dto.LoginResponseDto;
import com.abc.parkingslotbooking.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

	@Mock
    EmployeeService employeeService;

	MockMvc mockMvc;
    ObjectMapper objectMapper;

    @InjectMocks
    EmployeeController employeeController;

    LoginDto loginDto;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        loginDto = new LoginDto();
        loginDto.setPassword("1234");
        loginDto.setEmployeeName("sai");
    }

    	@Test
        public void loginEmployee() throws Exception
        {
            LoginResponseDto loginResponseDto=new LoginResponseDto();
            loginResponseDto.setMessage("Employee logged in");
            loginResponseDto.setStatusCode(200);
            //given
            when(employeeService.loginEmployee(any(LoginDto.class))).thenReturn(loginResponseDto);
            
            mockMvc.perform(post("/employees/login").contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(loginDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", Matchers.any(LinkedHashMap.class)));
    
            verify(employeeService).loginEmployee(any(LoginDto.class));
        }

        
    }