package com.abc.parkingslotbooking.dto;

import java.time.LocalDate;

public class RequestDto {
	
	LocalDate fromDate;
	LocalDate toDate;
	
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

}
