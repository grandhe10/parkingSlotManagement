package com.abc.parkingslotbooking.service;

import javax.validation.Valid;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.abc.parkingslotbooking.dto.LoginDto;
import com.abc.parkingslotbooking.dto.LoginResponseDto;

 

public interface EmployeeService {
static Log logger = LogFactory.getLog(EmployeeService.class);
    
    /**
     * @param loginDto
     * @return employee logged in
     */

 

    LoginResponseDto loginEmployee(@Valid LoginDto loginDto);

 

}