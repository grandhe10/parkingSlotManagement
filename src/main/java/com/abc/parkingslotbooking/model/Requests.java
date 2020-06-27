package com.abc.parkingslotbooking.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Requests {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	Long reqLongestId;
	Long employeeId;
	@Enumerated(EnumType.STRING)
	RequestType requestType;
	LocalDate date;
	@Enumerated(EnumType.STRING)
	StatusOptions statusOptions;
	Long parkingSlotNumber;

	public Long getReqLongestId() {
		return reqLongestId;
	}

	public void setReqLongestId(Long reqLongestId) {
		this.reqLongestId = reqLongestId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public StatusOptions getStatusOptions() {
		return statusOptions;
	}

	public void setStatusOptions(StatusOptions statusOptions) {
		this.statusOptions = statusOptions;
	}

	public Long getParkingSlotNumber() {
		return parkingSlotNumber;
	}

	public void setParkingSlotNumber(Long parkingSlotNumber) {
		this.parkingSlotNumber = parkingSlotNumber;
	}

}
