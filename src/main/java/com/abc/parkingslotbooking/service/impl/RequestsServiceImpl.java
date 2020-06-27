package com.abc.parkingslotbooking.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.abc.parkingslotbooking.configuration.ScheduledTasks;
import com.abc.parkingslotbooking.dao.EmployeeDao;
import com.abc.parkingslotbooking.dao.ParkingSlotDao;
import com.abc.parkingslotbooking.dao.RequestsDao;
import com.abc.parkingslotbooking.dto.RequestByIdDto;
import com.abc.parkingslotbooking.dto.RequestDto;
import com.abc.parkingslotbooking.dto.RequestResponseDto;
import com.abc.parkingslotbooking.dto.ResponseDto;
import com.abc.parkingslotbooking.model.Employee;
import com.abc.parkingslotbooking.model.EmployeeType;
import com.abc.parkingslotbooking.model.RequestType;
import com.abc.parkingslotbooking.model.Requests;
import com.abc.parkingslotbooking.model.StatusOptions;
import com.abc.parkingslotbooking.service.RequestsService;

@Service
@Component
public class RequestsServiceImpl implements RequestsService {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	private static Log logger = LogFactory.getLog(RequestsServiceImpl.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	RequestsDao requestsDao;

	@Autowired
	ParkingSlotDao parkingSlotDao;

	@Override
	public RequestResponseDto submitRequest(RequestDto requestDto, Long employeeId) {

		RequestResponseDto requestResponseDto = new RequestResponseDto();

		List<LocalDate> dateList = RequestsServiceImpl.getDatesBetweenUsingJava8(requestDto.getFromDate(),
				requestDto.getToDate());
		System.out.println(dateList.get(0));

		if (dateList.stream().map(date -> addRequest(date, employeeId)).collect(Collectors.toList()).contains(null)) {
			requestResponseDto.setMessage("Request cannot be submitted ");
			requestResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return requestResponseDto;
		}

		requestResponseDto.setMessage("Request submitted successfully");
		requestResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return requestResponseDto;

	}

	private static List<LocalDate> getDatesBetweenUsingJava8(LocalDate fromDate, LocalDate toDate) {
		if (fromDate.equals(toDate)) {
			System.out.println(fromDate.equals(toDate));
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
			} else {

				return requestResponseDto;
			}

		} else
			return requestResponseDto;
	}

	@Scheduled(fixedRate = 50000)
	public void updateStatus() {

		log.info("The time is now {}", dateFormat.format(new Date()));
		Optional<List<Requests>> releaseList = requestsDao.findByRequestTypeAndStatusOptions(RequestType.RELEASE,
				StatusOptions.PENDING);

		if (!releaseList.isPresent()) {
			releaseList.get().stream().map(request -> updateRelease(request)).collect(Collectors.toList());

			List<Long> list = releaseList.get().stream().map(request -> request.getParkingSlotNumber())
					.collect(Collectors.toList());

			Optional<List<Requests>> requestList = requestsDao.findByRequestTypeAndStatusOptions(RequestType.REQUEST,
					StatusOptions.PENDING);

			requestList.get().stream().map(request->updateRelease(request)).collect(Collectors.toList());
			List<Requests> list2 = requestList.get().subList(1, list.size());

			for (int i = 0; i < list.size(); i++) {
				log.info("entered for loop1");
				for (Requests request : list2) {
					log.info("entered for loop2");
					request.setParkingSlotNumber(list.get(i));
					request.setStatusOptions(StatusOptions.APPROVED);
					requestsDao.save(request);
					i++;
				}
				break;
			}
		}

	}

	private Long updateRelease(Requests requests) {
		log.info("entered updateRelease method");
		requests.setStatusOptions(StatusOptions.APPROVED);
		requestsDao.save(requests);
		return 1L;
	}

	/*
	 * private Long updateAndGetParkingSlot(Requests request, List<Long> list) { for
	 * (long i : list) { request.setStatusOptions(StatusOptions.APPROVED);
	 * request.setParkingSlotNumber(list.get(0)); requestsDao.save(request); return
	 * 1L; } return null; }
	 * 
	 * private Long updateAndGetParkingSlot(Requests request, Long l) {
	 * 
	 * request.setStatusOptions(StatusOptions.APPROVED);
	 * request.setParkingSlotNumber(l); requestsDao.save(request); return 1L; }
	 */

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

		List<ResponseDto> responseList = new ArrayList<ResponseDto>();

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
