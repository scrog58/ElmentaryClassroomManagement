package com.promineotech.classManagementApi.repository;

import org.springframework.data.repository.CrudRepository;

import com.promineotech.classManagementApi.entity.Employees;



public interface EmployeesRepository extends CrudRepository<Employees, Long> {

	public Employees findByUsername(String username);

}
