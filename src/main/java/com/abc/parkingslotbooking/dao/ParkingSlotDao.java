package com.abc.parkingslotbooking.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abc.parkingslotbooking.model.ParkingSlot;

@Repository
public interface ParkingSlotDao extends CrudRepository<ParkingSlot,Long> {

}
