/*
 * package com.abc.parkingslotbooking.service.impl;
 * 
 * 
 * import static org.mockito.ArgumentMatchers.any; import static
 * org.mockito.Mockito.verify; import static org.mockito.Mockito.when;
 * 
 * import java.util.Optional;
 * 
 * 
 * import static org.assertj.core.api.Assertions.assertThat; import static
 * org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.junit.jupiter.api.Assertions.assertNotNull; import static
 * org.junit.jupiter.api.Assertions.assertTrue; import
 * org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test; import
 * org.junit.jupiter.api.extension.ExtendWith; import org.mockito.InjectMocks;
 * import org.mockito.Mock; import org.mockito.Mockito; import
 * org.mockito.junit.jupiter.MockitoExtension;
 * 
 * 
 * 
 * import com.abc.parkingslotbooking.dao.EmployeeDao; import
 * com.abc.parkingslotbooking.dto.LoginDto; import
 * com.abc.parkingslotbooking.dto.LoginResponseDto; import
 * com.abc.parkingslotbooking.model.Employee; import
 * com.abc.parkingslotbooking.model.EmployeeType; import
 * com.abc.parkingslotbooking.service.impl.EmployeeServiceImpl;
 * 
 * 
 * 
 * 
 * @ExtendWith(MockitoExtension.class) public class EmployeeServiceImplTest{
 * 
 * 
 * Employee employee; LoginResponseDto loginResponseDto;
 * 
 * @Mock EmployeeDao employeeDao;
 * 
 * @InjectMocks EmployeeServiceImpl employeeServiceImpl;
 * 
 * LoginDto loginDto;
 * 
 * @BeforeEach public void setUp() {
 * 
 * loginResponseDto = new LoginResponseDto();
 * loginResponseDto.setMessage("Employee logged in");
 * loginResponseDto.setStatusCode(200); }
 * 
 * @Test public void loginEmployeeTest() { Employee employee = new Employee();
 * employee.setEmployeeId(2L); employee.setEmployeeName("sai");
 * employee.setEmployeeType(EmployeeType.NON_VIP);
 * employee.setExperienceInYears(5L); employee.setParkingSlotNumber(2345L);
 * employee.setPassword("1234");
 * 
 * LoginResponseDto loginResponseDto = new LoginResponseDto();
 * loginResponseDto.setMessage("Employee logged in");
 * loginResponseDto.setStatusCode(200);
 * when(employeeDao.findByEmployeeNameAndPassword("sai",
 * "1234")).thenReturn(Optional.of(employee));
 * employeeServiceImpl.loginEmployee(loginDto);
 * verify(employeeDao).findByEmployeeNameAndPassword("sai", "1234"); }
 * 
 * }
 * 
 */