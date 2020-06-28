package com.abc.parkingslotbooking.service;

import javax.validation.Valid;

import com.abc.parkingslotbooking.dto.LoginDto;
import com.abc.parkingslotbooking.dto.LoginResponseDto;

public interface EmployeeService {

    
    /**
     * @param loginDto
     * @return employee logged in
     */
    LoginResponseDto loginEmployee(@Valid LoginDto loginDto);

 

}