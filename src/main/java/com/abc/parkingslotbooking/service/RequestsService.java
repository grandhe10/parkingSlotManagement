package com.abc.parkingslotbooking.service;

import java.util.List;

import javax.validation.Valid;

import com.abc.parkingslotbooking.dto.RequestByIdDto;
import com.abc.parkingslotbooking.dto.RequestDto;
import com.abc.parkingslotbooking.dto.RequestResponseDto;
import com.abc.parkingslotbooking.dto.ResponseDto;

public interface RequestsService {

	RequestResponseDto submitRequest(@Valid RequestDto requestDto,Long employeeId);
	
	 /**This method is used to get all the requests by employeeId and requestid
     * @param employeeId
     * @param requestId
     * @return requests by employeeId and requestId
     */
	RequestByIdDto getrequestsbyEmployeeIdAndRequestId(Long employeeId,Long requestId);
    /**This method is used to get list of requests by employeeId 
     * @param employeeId
     * @return list of requests by EmployeeID
     */
    public List<ResponseDto> getrequestsByEmployeeId(Long employeeId);

	

}
