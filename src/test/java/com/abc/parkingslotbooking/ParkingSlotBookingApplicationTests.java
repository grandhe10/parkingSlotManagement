package com.abc.parkingslotbooking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.abc.parkingslotbooking.controller.EmployeeController;
import com.abc.parkingslotbooking.controller.RequestsController;

@SpringBootTest
class ParkingSlotBookingApplicationTests {

	@Autowired
	RequestsController requestsController;
	@Autowired
	EmployeeController employeeController;

	@Autowired

	@Test
	public void contexLoads() throws Exception {
		assertThat(requestsController).isNotNull();
	}

	@Test
	public void contexLoads1() throws Exception {
		assertThat(employeeController).isNotNull();
	}

	@Test
	public void main() {
		ParkingSlotBookingApplication.main(new String[] {});
	}
}
