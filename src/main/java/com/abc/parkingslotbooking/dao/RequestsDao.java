package com.abc.parkingslotbooking.dao;

import org.springframework.data.repository.CrudRepository;

import com.abc.parkingslotbooking.model.Requests;

public interface RequestsDao extends CrudRepository<Requests, Long>{

}
