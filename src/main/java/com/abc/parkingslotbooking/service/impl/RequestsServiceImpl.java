package com.abc.parkingslotbooking.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
import com.abc.parkingslotbooking.service.RequestsService;

@Service
@Component
public class RequestsServiceImpl implements RequestsService {

	private static Log logger = LogFactory.getLog(RequestsServiceImpl.class);

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	RequestsDao requestsDao;

	@Autowired
	ParkingSlotDao parkingSlotDao;

	@Autowired
	RequestsService requestsService;

	@Override
	public RequestResponseDto submitRequest(RequestDto requestDto, Long employeeId) {

		RequestResponseDto requestResponseDto = new RequestResponseDto();

		List<LocalDate> dateList = RequestsServiceImpl.getDatesBetweenUsingJava8(requestDto.getFromDate(),
				requestDto.getToDate());

		if (dateList.stream().map(date -> addRequest(date, employeeId)).collect(Collectors.toList()).contains(null)) {

			requestResponseDto.setMessage("Request cannot be submitted ");
			requestResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return requestResponseDto;
		}

		requestResponseDto.setMessage("Request submitted successfully");
		requestResponseDto.setStatusCode(HttpStatus.OK.value());
		return requestResponseDto;

	}

	private static List<LocalDate> getDatesBetweenUsingJava8(LocalDate fromDate, LocalDate toDate) {
		if (fromDate.equals(toDate)) {

			List<LocalDate> localDatelist = new ArrayList<>();
			localDatelist.add(fromDate);
			return localDatelist;
		} else {
			long numOfDaysBetween = ChronoUnit.DAYS.between(fromDate, toDate);
			return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> fromDate.plusDays(i))
					.collect(Collectors.toList());
		}
	}

	private RequestResponseDto addRequest(LocalDate date, Long employeeId) {
		RequestResponseDto requestResponseDto = new RequestResponseDto();

		Requests requests = new Requests();
		Optional<Employee> employee = employeeDao.findByEmployeeId(employeeId);
		if (employee.isPresent()) {
			if (employee.get().getEmployeeType().equals(EmployeeType.VIP)) {
				requests.setDate(date);
				requests.setEmployeeId(employeeId);
				requests.setParkingSlotNumber(employee.get().getParkingSlotNumber());
				requests.setRequestType(RequestType.RELEASE);
				requests.setStatusOptions(StatusOptions.PENDING);
				requestsDao.save(requests);
				requestResponseDto.setStatusCode(HttpStatus.OK.value());

				Optional<ParkingSlot> parkingSlotOptional = parkingSlotDao.findByDateAndEmployeeId(date, employeeId);
				if (parkingSlotOptional.isPresent()) {
					parkingSlotOptional.get().setStatusOptions(StatusOptions.UNAVAILABLE);
					parkingSlotDao.save(parkingSlotOptional.get());
				} else
					return null;
				requestResponseDto.setStatusCode(HttpStatus.OK.value());
				return requestResponseDto;

			} else if (employee.get().getEmployeeType().equals(EmployeeType.NON_VIP)) {
				requests.setDate(date);
				requests.setEmployeeId(employeeId);
				requests.setParkingSlotNumber(0L);
				requests.setRequestType(RequestType.REQUEST);
				requests.setStatusOptions(StatusOptions.PENDING);
				requestsDao.save(requests);
				requestResponseDto.setStatusCode(HttpStatus.OK.value());
				return requestResponseDto;
			} 
			else {

				return requestResponseDto;
			}

		} 
		else
			return null;
	}

	@Scheduled(fixedRate = 50000)
	public void updateStatus() {

		Optional<List<Requests>> releaseList = requestsDao.findByRequestTypeAndStatusOptionsAndDate(RequestType.RELEASE,
				StatusOptions.PENDING, LocalDate.now().plusDays(1));

		logger.info(LocalDate.now().plusDays(1));

		if (releaseList.isPresent()) {

			
			List<Long> list = releaseList.get().stream().map(Requests :: getParkingSlotNumber)
					.collect(Collectors.toList());

			Optional<List<Requests>> requestList = requestsDao.findByRequestTypeAndStatusOptionsAndDate(
					RequestType.REQUEST, StatusOptions.PENDING, LocalDate.now().plusDays(1));

			logger.info(LocalDate.now().plusDays(1));

			if (requestList.isPresent()) {

				List<Requests> list2 = requestList.get().subList(1, list.size());

				for (int i = 0; i < list.size(); i++) {
					logger.info("entered for loop1");

					for (Requests request : list2) {

						logger.info("entered for loop2");

						request.setParkingSlotNumber(list.get(i));
						request.setStatusOptions(StatusOptions.APPROVED);
						requestsDao.save(request);

						if (list.get(i++).equals(list.get(list.size() - 1)))
							break;
					}

				}
			} 
			else {
				
				releaseList.get().stream().map(request -> updateRelease(request)).collect(Collectors.toList());
				logger.info("There are no pending requests for today");

			}
		} else
			logger.info("There are no pending requests for today");

	}

	private Long updateRelease(Requests requests) {
		logger.info("entered updateRelease method");
		requests.setStatusOptions(StatusOptions.APPROVED);
		requestsDao.save(requests);
		return 1L;
	}

	@Override
	public RequestByIdDto getrequestsbyEmployeeIdAndRequestId(Long employeeId, Long requestId) {
		logger.info("Inside getrequestsbyEmployeeIdAndRequestId requestserviceimpl ");
		Optional<Requests> requestOptional = requestsDao.findByEmployeeIdAndRequestId(employeeId, requestId);
		RequestByIdDto requestByIdDto = new RequestByIdDto();
		if (requestOptional.isPresent()) {
			BeanUtils.copyProperties(requestOptional.get(), requestByIdDto);
			requestByIdDto.setMessage("Please find your details");
			requestByIdDto.setStatusCode(200);

			return requestByIdDto;
		}
		requestByIdDto.setMessage("Please VERIFY YOUR EMPLOYEE ID AND REQUEST ID");
		requestByIdDto.setStatusCode(400);

		return requestByIdDto;
	}

	/**
	 * This method is used to get list of requests by employeeId
	 * 
	 * @{inherit Doc}
	 */
	@Override
	public List<ResponseDto> getrequestsByEmployeeId(Long employeeId) {
		logger.info("Inside getrequestsByEmployeeId requestserviceimpl ");

		List<ResponseDto> responseList = new ArrayList<>();

		Optional<List<Requests>> requestsOptional = requestsDao.findByEmployeeId(employeeId);

		if (requestsOptional.isPresent())

			return requestsOptional.get().stream().map(request -> getResponseDto(request)).collect(Collectors.toList());

		else {
			return responseList;
		}
	}

	private ResponseDto getResponseDto(Requests request) {
		ResponseDto responseDto = new ResponseDto();
		BeanUtils.copyProperties(request, responseDto);
		return responseDto;
	}
}
