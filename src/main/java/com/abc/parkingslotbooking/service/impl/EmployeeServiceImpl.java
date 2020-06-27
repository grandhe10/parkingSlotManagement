package com.abc.parkingslotbooking.service.impl;

 


import java.util.Optional;

 

import javax.validation.Valid;

 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

 

import com.abc.parkingslotbooking.dao.EmployeeDao;
import com.abc.parkingslotbooking.dto.LoginDto;
import com.abc.parkingslotbooking.dto.LoginResponseDto;
import com.abc.parkingslotbooking.model.Employee;
import com.abc.parkingslotbooking.service.EmployeeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @author haritha
 *
 */
@Service

 

public class EmployeeServiceImpl implements EmployeeService {

 

    private static Log logger = LogFactory.getLog(EmployeeServiceImpl.class);
    @Autowired
    EmployeeDao employeeDao;

 


    @Override
    public LoginResponseDto loginEmployee(@Valid LoginDto loginDto) {
        logger.info("Inside login Employee");
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        Optional<Employee> employee = employeeDao.findByEmployeeNameAndPassword(loginDto.getEmployeeName(),
                loginDto.getPassword());
        if (employee.isPresent()) {
            loginResponseDto.setMessage("Employee logged in Successfully");
            loginResponseDto.setStatusCode(HttpStatus.OK.value());
            logger.info("Employee logged in Successfully");
            return loginResponseDto;
        }

 

        logger.info("Invalid credentials!!!");
        loginResponseDto.setMessage("Invalid credentials!!!");
        loginResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        return loginResponseDto;

 


    }

 


}
 