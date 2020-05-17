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

import com.promineotech.classManagementApi.entity.Parent;
import com.promineotech.classManagementApi.entity.Student;

import com.promineotech.classManagementApi.service.ParentServices;
import com.promineotech.classManagementApi.service.StudentServices;



@RestController
@RequestMapping("/parent")
public class ParentController {
	
	@Autowired
	private ParentServices service;
	
	@Autowired
	private StudentServices studentService;
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ResponseEntity<Object> register(@RequestBody Parent parent) {
		return new ResponseEntity<Object>(service.createParent(parent), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody Parent parent) {
		try{
			return new ResponseEntity<Object>(service.login(parent), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getParent(@PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.getParentById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Object> getParents() {
		return new ResponseEntity<Object>(service.getParents(), HttpStatus.OK);
	}
	
	@RequestMapping(value="{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateParent(@RequestBody Parent parent, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateParent(parent, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="{id}/edit", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateParentUserName(@RequestBody Parent parent, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateParentUserName(parent, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE) 
	public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
		try {
			service.deleteParent(id);
			return new ResponseEntity<Object>("Successfully delete parent", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
////Student Side
//	@RequestMapping(value="/{id}/student/{id}", method = RequestMethod.GET)
//	public ResponseEntity<Object> getStudent(@PathVariable Long parentid, @PathVariable Long id) {
//		try {
//			return new ResponseEntity<Object>(studentService.getStudentById(service.getParentById(parentid),id), HttpStatus.OK);
//		} catch(Exception e) {
//			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
//		}
//	}
//	
//	@RequestMapping(value="/{id}/student", method = RequestMethod.GET)
//	public ResponseEntity<Object> getStudents(@PathVariable Long parentid) {
//		try {
//			return new ResponseEntity<Object>(studentService.getStudents(service.getParentById(parentid)), HttpStatus.OK);
//		} catch(Exception e) {
//			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
//		}
//	}
//	
//	@RequestMapping(method = RequestMethod.POST)
//	public ResponseEntity<Object> newStudent(@RequestBody Set<Long> assignmentId, @PathVariable Long parentId) {
//		try {
//			return new ResponseEntity<Object>(studentService.newStudent(assignmentId, parentId), HttpStatus.OK);
//		} catch(Exception e) {
//			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
//		}
//	}
//	
//	@RequestMapping(value="/{id}/student/{id}/update", method = RequestMethod.PUT)
//	public ResponseEntity<Object> updateStudent(@PathVariable Long parentId, @RequestBody Student student, @PathVariable Long id) {
//		try {
//			return new ResponseEntity<Object>(studentService.updateStudent(parentId, student, id), HttpStatus.OK);
//		} catch(Exception e) {
//			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
//		}
//	}
//	
//	@RequestMapping(value="/{id}/student/{id}", method = RequestMethod.DELETE)
//	public ResponseEntity<Object> updateStudent(@PathVariable Long parentid, @PathVariable Long id) {
//		try {
//			studentService.deleteStudent(service.getParentById(parentid), id);
//			return new ResponseEntity<Object>("Succefull delete student.", HttpStatus.OK);
//		} catch(Exception e) {
//			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
//		}
//	}
//	
	
	

}
