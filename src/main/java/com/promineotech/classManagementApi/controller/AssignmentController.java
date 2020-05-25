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

import com.promineotech.classManagementApi.entity.AssignmentGradeStudents;
import com.promineotech.classManagementApi.entity.AssignmentsGrades;

import com.promineotech.classManagementApi.service.AssignmentServices;
import com.promineotech.classManagementApi.service.AssignmentGradeStudentsService;

@RestController
@RequestMapping("employees/{employeeId}/class/{classId}/assignments")
public class AssignmentController {
	
	@Autowired
	private AssignmentServices service;
	
	@Autowired
	private AssignmentGradeStudentsService gradeservice;
	
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
		return new ResponseEntity<Object>(service.getListAssignmentsGrades(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> newAssignment(@RequestBody AssignmentsGrades assign, @PathVariable Long classId) {
		try {
			
			return new ResponseEntity<Object>(service.newAssignment(assign, classId), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//add list of students to class assignment
	@RequestMapping(value="/{id}/addStudents", method = RequestMethod.PUT)
	public ResponseEntity<Object> addStudentsToAssignments(@RequestBody Set<Long> studentIds, @PathVariable Long id,@PathVariable Long classId) {
		try {
			 return new ResponseEntity<Object>(service.submitStudentsIntoAssignments(studentIds, id, classId), HttpStatus.OK);
 
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//add the grade to the student assignment
	@RequestMapping(value="/{id}/student/{studentId}", method = RequestMethod.PUT)
	public ResponseEntity<Object> addStudentPoints(@RequestBody AssignmentGradeStudents assignment, @PathVariable Long classId, @PathVariable Long id,@PathVariable Long studentId) {
		try {
			return new ResponseEntity<Object>(gradeservice.addAssignmentStudentInfo(assignment,classId, id,studentId), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//update assignment grade of the student
	@RequestMapping(value="/{id}/student/{studentId}/edit/{assignmentGradeId}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateAssignmentGrade(@RequestBody AssignmentGradeStudents assignment, @PathVariable Long assignmentGradeId) {
		try {
			return new ResponseEntity<Object>(gradeservice.updateAssignmentGrade(assignment,assignmentGradeId), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//update assignment information
	@RequestMapping(value="/{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateAssignment(@RequestBody AssignmentsGrades assignment, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateAssignment(assignment, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE) 
	public ResponseEntity<Object> deleteAssignment(@PathVariable Long id) {
		try {
			service.removeAssignment(id);
			return new ResponseEntity<Object>("Successfully delete assignment", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	

}
