package com.promineotech.classManagementApi.controller;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.classManagementApi.entity.Classs;


import com.promineotech.classManagementApi.service.ClassServices;


@RestController
@RequestMapping("/class")
public class ClassController {
	
	@Autowired
	private ClassServices service;
	

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getClass(@PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.getClassById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
		@RequestMapping(method = RequestMethod.GET)
		public ResponseEntity<Object> getClasses() {
			try {
				return new ResponseEntity<Object>(service.getClasses(), HttpStatus.OK);
				
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
		@RequestMapping(value="/unassignedstudents",method = RequestMethod.GET)
		public ResponseEntity<Object> getStudentsLinkedToClass() {
			try {
				return new ResponseEntity<Object>(service.getAllStudentsClassIsNull(), HttpStatus.OK);
				
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<Object> newClass(@RequestBody Classs getClass) {
			try {
				return new ResponseEntity<Object>(service.newClass(getClass), HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
		@RequestMapping(value="/{id}/addteachers",method = RequestMethod.POST)
		public ResponseEntity<Object> addTeachersToClass(@RequestBody Set<Long> employeeIds, @PathVariable Long id) {
			try {
				return new ResponseEntity<Object>(service.submitEmployeesIntoClass(employeeIds, id), HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
		@RequestMapping(value="/{id}/addstudents",method = RequestMethod.POST)
		public ResponseEntity<Object> addStudentsToClass(@RequestBody Set<Long> studentIds, @PathVariable Long id) {
			try {
				return new ResponseEntity<Object>(service.submitStudentsIntoClass(studentIds, id), HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
		@RequestMapping(value="/{id}/update", method = RequestMethod.PUT)
		public ResponseEntity<Object> updateClass(@RequestBody Classs getClass, @PathVariable Long id) {
			try {
				return new ResponseEntity<Object>(service.updateClass(getClass, id), HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
		@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<Object> deleteClass(@PathVariable Long id) {
			try {
				service.deleteClass(id);
				return new ResponseEntity<Object>("Succefull delete class.", HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}

}
