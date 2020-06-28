
package com.abc.parkingslotbooking.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abc.parkingslotbooking.dao.EmployeeDao;
import com.abc.parkingslotbooking.dto.LoginDto;
import com.abc.parkingslotbooking.dto.LoginResponseDto;
import com.abc.parkingslotbooking.model.Employee;
import com.abc.parkingslotbooking.model.EmployeeType;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

	Employee employee;
	LoginResponseDto loginResponseDto;

	@Mock
	EmployeeDao employeeDao;

	@InjectMocks
	EmployeeServiceImpl employeeServiceImpl;

	LoginDto loginDto;

	@BeforeEach
	public void setUp() {

		loginResponseDto = new LoginResponseDto();
		loginResponseDto.setMessage("Employee logged in");
		loginResponseDto.setStatusCode(200);
	}

	@Test
	public void loginEmployeeTest() {
		
		Employee employee = new Employee();
		employee.setEmployeeId(1L);
		employee.setEmployeeName("sai");
		employee.setEmployeeType(EmployeeType.NON_VIP);
		employee.setExperienceInYears(5L);
		employee.setParkingSlotNumber(2345L);
		employee.setPassword("1234");
		
		LoginDto loginDto = new LoginDto();
		loginDto.setEmployeeName("sai");
		loginDto.setPassword("1234");
		
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setMessage("Employee logged in");
		loginResponseDto.setStatusCode(200);
		when(employeeDao.findByEmployeeNameAndPassword("sai","1234")).thenReturn(Optional.of(employee));
		employeeServiceImpl.loginEmployee(loginDto);
		verify(employeeDao).findByEmployeeNameAndPassword("sai", "1234");
	}

}
