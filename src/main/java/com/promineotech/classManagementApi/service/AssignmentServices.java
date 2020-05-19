package com.promineotech.classManagementApi.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.classManagementApi.entity.AssignmentsGrades;
import com.promineotech.classManagementApi.entity.Employees;
import com.promineotech.classManagementApi.entity.Parent;
import com.promineotech.classManagementApi.entity.Student;
import com.promineotech.classManagementApi.entity.Classs;

import com.promineotech.classManagementApi.repository.AssignmentsRepository;
import com.promineotech.classManagementApi.repository.ClassRepository;
import com.promineotech.classManagementApi.repository.EmployeesRepository;
import com.promineotech.classManagementApi.repository.ParentRepository;
import com.promineotech.classManagementApi.repository.StudentRepository;


@Service
public class AssignmentServices {
	
	private static final Logger logger = LogManager.getLogger(AssignmentsGrades.class);
	
	@Autowired
	private AssignmentsRepository repo;
	
	@Autowired
	private ClassRepository classRepo;
	
	@Autowired
	private EmployeesRepository employeeRepo;
	
	@Autowired
	private ParentRepository parentRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	public AssignmentsGrades getAssignmentById(Long id) {
		return repo.findOne(id);
	}
	
	public Iterable<AssignmentsGrades> getAssignmentsGrades() {
		return repo.findAll();
	}
	
	public AssignmentsGrades newAssignment(AssignmentsGrades assign, Long id) throws Exception {
		try {
			
			assign.setDate(LocalDate.now());
			return repo.save(assign);
			
		} catch(Exception e) {
			logger.error("Exception occurred while trying to create new assignment for class: " + id, e);
			throw e;
		}
	}
	
	public AssignmentsGrades updateAssignment(AssignmentsGrades assign, Long id) throws Exception {
		try {
			
			AssignmentsGrades Oldassgin = repo.findOne(id);
			
			Oldassgin.setDescription(assign.getDescription());
			return repo.save(Oldassgin);
			
		} catch(Exception e) {
			logger.error("Exception occurred while trying to create new assignment for class: " + id, e);
			throw e;
		}
	}
	
	public void removeAssignment(Long id) throws Exception {
		try {
			repo.delete(id);
			
		} catch(Exception e) {
			logger.error("Exception occured while trying to delete assignment: " + id, e);
			throw new Exception("Unable to delete assignment.");
		}
	}

	
}
