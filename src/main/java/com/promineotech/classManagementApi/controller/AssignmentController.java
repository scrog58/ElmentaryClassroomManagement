package com.promineotech.classManagementApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.classManagementApi.entity.AssignmentsGrades;
import com.promineotech.classManagementApi.service.AssignmentServices;


@RestController
@RequestMapping("/assignments")
public class AssignmentController {
	
	@Autowired
	private AssignmentServices service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getAssignment(@PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.getAssignmentById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Object> getAssignments() {
		return new ResponseEntity<Object>(service.getAssignmentsGrades(), HttpStatus.OK);
	}
	
	@RequestMapping(value="{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateAssignment(@RequestBody AssignmentsGrades assignment, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateAssignment(assignment, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE) 
	public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
		try {
			service.removeAssignment(id);
			return new ResponseEntity<Object>("Successfully delete assignment", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	

}
