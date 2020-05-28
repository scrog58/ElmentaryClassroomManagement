package com.promineotech.classManagementApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.classManagementApi.entity.Student;

import com.promineotech.classManagementApi.service.StudentServices;

@RestController
@RequestMapping("/parent/{parentId}/student")
public class StudentController {
	
	@Autowired
	private StudentServices service;
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getStudent(@PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.getStudentById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
		@RequestMapping(method = RequestMethod.GET)
		public ResponseEntity<Object> getStudents() {
			try {
				return new ResponseEntity<Object>(service.getStudents(), HttpStatus.OK);
				
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
			
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<Object> newStudent(@RequestBody Student student,@PathVariable Long parentId) {
			try {
				
				return new ResponseEntity<Object>(service.newStudent(student, parentId), HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
		@RequestMapping(value="/{id}/update", method = RequestMethod.PUT)
		public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable Long id) {
			try {
				return new ResponseEntity<Object>(service.updateStudent(student, id), HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
		@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<Object> updateStudent(@PathVariable Long id) {
			try {
				service.deleteStudent(id);
				return new ResponseEntity<Object>("Succefull delete student.", HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
	

}
