package com.promineotech.classManagementApi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.classManagementApi.entity.Employees;
import com.promineotech.classManagementApi.service.EmployeesServices;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

	@Autowired
	private EmployeesServices service;
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ResponseEntity<Object> register(@RequestBody Employees employee) {
		return new ResponseEntity<Object>(service.createEmployee(employee), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody Employees employee) {
		try{
			return new ResponseEntity<Object>(service.login(employee), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getEmployee(@PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.getEmployeesById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Object> getParents() {
		return new ResponseEntity<Object>(service.getEmployees(), HttpStatus.OK);
	}
	
	@RequestMapping(value="{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateEmployee(@RequestBody Employees employee, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateEmployee(employee, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="{id}/edit", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateEmployeeUserName(@RequestBody Employees employee, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateEmployeeUserName(employee, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE) 
	public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
		try {
			service.deleteEmployee(id);
			return new ResponseEntity<Object>("Successfully delete employee", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	
}
