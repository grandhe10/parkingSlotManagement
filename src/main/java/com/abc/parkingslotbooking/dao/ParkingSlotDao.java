package com.abc.parkingslotbooking.dao;


import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abc.parkingslotbooking.model.ParkingSlot;

@Repository
public interface ParkingSlotDao extends CrudRepository<ParkingSlot,Long> {

	Optional<ParkingSlot> findByDateAndEmployeeId(LocalDate date,Long employeeId);
}
