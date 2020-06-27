package com.abc.parkingslotbooking.dao;

import org.springframework.data.repository.CrudRepository;

import com.abc.parkingslotbooking.model.Employee;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

 
public interface EmployeeDao extends CrudRepository<Employee, Long>{
    static Log logger = LogFactory.getLog(EmployeeDao.class);
    /**
     * @param employeeName
     * @param password
     * @return 
     */
    public Optional<Employee> findByEmployeeNameAndPassword( String employeeName,String password);
    
    
    public Optional<Employee> findByEmployeeId(Long employeeId);
    
}