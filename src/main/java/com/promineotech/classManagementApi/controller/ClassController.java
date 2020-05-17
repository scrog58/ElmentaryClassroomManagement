package com.promineotech.classManagementApi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.classManagementApi.entity.Class;


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
		
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<Object> newClass(@RequestBody Class getClass, @PathVariable Long id) {
			try {
				return new ResponseEntity<Object>(service.newClass(getClass, id), HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
		@RequestMapping(value="/{id}/update", method = RequestMethod.PUT)
		public ResponseEntity<Object> updateClass(@RequestBody Class getClass, @PathVariable Long id) {
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
