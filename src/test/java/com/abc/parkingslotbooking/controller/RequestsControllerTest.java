package com.abc.parkingslotbooking.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

import com.abc.parkingslotbooking.dto.RequestByIdDto;
import com.abc.parkingslotbooking.dto.RequestDto;
import com.abc.parkingslotbooking.dto.RequestResponseDto;
import com.abc.parkingslotbooking.dto.ResponseDto;
import com.abc.parkingslotbooking.model.Employee;
import com.abc.parkingslotbooking.model.EmployeeType;
import com.abc.parkingslotbooking.model.RequestType;
import com.abc.parkingslotbooking.model.StatusOptions;
import com.abc.parkingslotbooking.service.RequestsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class RequestsControllerTest {

	@Mock
	RequestsService requestsService;
	MockMvc mockMvc;
	ObjectMapper objectMapper;

	@InjectMocks
	RequestsController requestsController;
	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(requestsController).build();

	}
	@Test
	public void submitRequestTest() throws JsonProcessingException, Exception
	{
	RequestResponseDto requestResponseDto = new RequestResponseDto();
	requestResponseDto.setMessage("request submitted successfully");
	requestResponseDto.setStatusCode(200);
	
	RequestDto requestDto = new RequestDto();
	LocalDate date = LocalDate.parse("2020-06-30");
	
	requestDto.setFromDate(date);
	requestDto.setToDate(LocalDate.parse("2020-07-02"));
	
	Employee employee = new Employee();
	employee.setEmployeeId(1L);
	employee.setEmployeeName("test");
	employee.setEmployeeType(EmployeeType.VIP);
	employee.setExperienceInYears(2L);
	employee.setParkingSlotNumber(1234L);
	employee.setPassword("test");
	
	/*
	 * lenient().when(requestsService.submitRequest(any(RequestDto.class),
	 * eq(1L))).thenReturn(requestResponseDto);
	 * 
	 * mockMvc.perform(post("/employees/{employeeId}/requests", 1L)
	 * .contentType(MediaType.APPLICATION_JSON_VALUE)
	 * .content(objectMapper.writeValueAsString(requestDto)))
	 * .andExpect(status().isAccepted()) .andExpect(jsonPath("$",
	 * Matchers.any(LinkedHashMap.class)));
	 * verify(requestsService).submitRequest(any(RequestDto.class), eq(1L));
	 */

}
	

	@Test
	public void getrequestsbyEmployeeIdAndRequestId() throws Exception
	{
		RequestByIdDto requestByIdDto = new RequestByIdDto();
		requestByIdDto.setDate(LocalDate.parse("2020-06-30"));
		requestByIdDto.setEmployeeId(1L);
		requestByIdDto.setParkingSlotNumber(1234L);
		requestByIdDto.setRequestId(1L);
		requestByIdDto.setRequestType(RequestType.REQUEST);
		requestByIdDto.setStatusCode(200);
		requestByIdDto.setStatusOptions(StatusOptions.PENDING);
		
		when(requestsService.getrequestsbyEmployeeIdAndRequestId(eq(1L), eq(1L))).thenReturn(requestByIdDto);
		
		  mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}",1L,1L).
				  contentType(MediaType.APPLICATION_JSON_VALUE)
				  .accept(MediaType.APPLICATION_JSON_VALUE))
		  .andExpect(status().isOk()) 
		  .andExpect(jsonPath("$", Matchers.any(LinkedHashMap.class)));
		  
		  verify(requestsService).getrequestsbyEmployeeIdAndRequestId(eq(1L), eq(1L));
	}
	
	@Test
	public void getrequestsbyEmployeeId() throws Exception
	{
		ResponseDto requestByIdDto = new ResponseDto();
		requestByIdDto.setDate(LocalDate.parse("2020-06-30"));
		requestByIdDto.setEmployeeId(1L);
		requestByIdDto.setParkingSlotNumber(1234L);
		requestByIdDto.setRequestId(1L);
		requestByIdDto.setRequestType(RequestType.REQUEST);
		requestByIdDto.setStatusOptions(StatusOptions.PENDING);
		List<ResponseDto> responseDtoList = new ArrayList<>();
		responseDtoList.add(requestByIdDto);		
		when(requestsService.getrequestsByEmployeeId(eq(1L))).thenReturn(responseDtoList);
		
		  mockMvc.perform(get("/employees/{employeeId}",1L).
				  contentType(MediaType.APPLICATION_JSON_VALUE)
				  .accept(MediaType.APPLICATION_JSON_VALUE))
		  .andExpect(status().isOk()) 
		  .andExpect(jsonPath("$.[0]", Matchers.any(LinkedHashMap.class)));
		  
		  verify(requestsService).getrequestsByEmployeeId(eq(1L));
	}
	
	
}
