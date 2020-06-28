package com.abc.parkingslotbooking.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.abc.parkingslotbooking.model.Employee;

 
public interface EmployeeDao extends CrudRepository<Employee, Long>{
   
    /**
     * @param employeeName
     * @param password
     * @return 
     */
    public Optional<Employee> findByEmployeeNameAndPassword( String employeeName,String password);
    
    
    public Optional<Employee> findByEmployeeId(Long employeeId);
    
}