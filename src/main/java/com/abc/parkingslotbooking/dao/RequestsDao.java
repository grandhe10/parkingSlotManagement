package com.abc.parkingslotbooking.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abc.parkingslotbooking.model.RequestType;
import com.abc.parkingslotbooking.model.Requests;
import com.abc.parkingslotbooking.model.StatusOptions;

@Repository
public interface RequestsDao extends CrudRepository<Requests, Long>{
	
	Optional<List<Requests>> findByRequestTypeAndStatusOptionsAndDate(RequestType requestType,StatusOptions statusType,LocalDate date);
	
	 /**This method is used to get all the requests by employeeId and requestid
     * @param employeeId
     * @param requestId
     * @return requests by employeeId and REquestId
     */
    public Optional<Requests> findByEmployeeIdAndRequestId(Long employeeId,Long requestId);
    /**This method is used to get list of requests by employeeId 
     * @param employeeId
     * @return list of requests by employeeId
     */
    public Optional<List<Requests>> findByEmployeeId(Long employeeId);

 
	

}
