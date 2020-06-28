package com.abc.parkingslotbooking.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abc.parkingslotbooking.dao.EmployeeDao;
import com.abc.parkingslotbooking.dao.ParkingSlotDao;
import com.abc.parkingslotbooking.dao.RequestsDao;
import com.abc.parkingslotbooking.dto.RequestByIdDto;
import com.abc.parkingslotbooking.dto.RequestDto;
import com.abc.parkingslotbooking.dto.RequestResponseDto;
import com.abc.parkingslotbooking.dto.ResponseDto;
import com.abc.parkingslotbooking.model.Employee;
import com.abc.parkingslotbooking.model.EmployeeType;
import com.abc.parkingslotbooking.model.ParkingSlot;
import com.abc.parkingslotbooking.model.RequestType;
import com.abc.parkingslotbooking.model.Requests;
import com.abc.parkingslotbooking.model.StatusOptions;

@ExtendWith(MockitoExtension.class)
public class RequestServiceImplTest {

	@Mock
	RequestsDao requestsDao;

	@InjectMocks
	RequestsServiceImpl requestsServiceImpl;

	@Mock
	EmployeeDao employeeDao;
	
	@Mock
	ParkingSlotDao parkingSlotDao;
	@BeforeEach
	public void setUp() {

	}

	@Test
	public void getrequestsByEmployeeIdAndREquestId() {
		RequestByIdDto requestResponseDto = new RequestByIdDto();
		requestResponseDto.setDate(LocalDate.parse("2020-06-30"));
		requestResponseDto.setMessage("your details are here");
		requestResponseDto.setParkingSlotNumber(4567L);
		requestResponseDto.setStatusCode(200);
		requestResponseDto.setStatusOptions(StatusOptions.AVAILABLE);
		requestResponseDto.setRequestType(RequestType.RELEASE);
		requestsServiceImpl.getrequestsbyEmployeeIdAndRequestId(1L, 1L);
		verify(requestsDao).findByEmployeeIdAndRequestId(1L, 1L);

	}

	@Test
	public void getrequestsByEmployeeId() {
		ResponseDto responseDto = new ResponseDto();
		List<ResponseDto> responseDtolist = new ArrayList<>();
		responseDtolist.add(responseDto);
		responseDto.setDate(LocalDate.parse("2020-06-30"));
		responseDto.setParkingSlotNumber(4567L);
		responseDto.setStatusOptions(StatusOptions.AVAILABLE);
		responseDto.setRequestType(RequestType.RELEASE);
		responseDto.setEmployeeId(1L);
		responseDto.setRequestId(1L);
		requestsServiceImpl.getrequestsByEmployeeId(1L);
		verify(requestsDao).findByEmployeeId(1L);

	}
	
	@Test
	public void submitRequest()
	{
		Requests requests = new Requests();
		requests.setDate(LocalDate.parse("2020-06-30"));
		requests.setEmployeeId(1L);
		

		Employee employee = new Employee();
		employee.setEmployeeId(1L);
		employee.setEmployeeName("test");
		employee.setEmployeeType(EmployeeType.VIP);
		employee.setExperienceInYears(2L);
		employee.setParkingSlotNumber(1234L);
		employee.setPassword("test");
		
		ParkingSlot parkingSlot = new ParkingSlot();
		parkingSlot.setDate(LocalDate.parse("2020-06-30"));
		parkingSlot.setEmployeeId(1L);
		parkingSlot.setParkingSlotId(1L);
		parkingSlot.setStatusOptions(StatusOptions.AVAILABLE);
		
		RequestDto requestDto = new RequestDto();
		requestDto.setFromDate(LocalDate.parse("2020-06-30"));
		requestDto.setToDate(LocalDate.parse("2020-07-02"));
		
		RequestResponseDto requestResponseDto = new RequestResponseDto();
		requestResponseDto.setMessage("Request submitted successfully");
		requestResponseDto.setStatusCode(202);
		
		when(employeeDao.findByEmployeeId(1L)).thenReturn(Optional.of(employee));
		assertThat(!(Optional.of(employee).get().equals(null))).isTrue();
		when(parkingSlotDao.findByDateAndEmployeeId(LocalDate.parse("2020-06-30"), 1L)).thenReturn(Optional.of(parkingSlot));
		assertThat(!(Optional.of(parkingSlot).get().equals(null))).isTrue();
		requestsServiceImpl.submitRequest(requestDto, 1L);
		

	}
}