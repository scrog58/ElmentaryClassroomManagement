package com.promineotech.classManagementApi.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.classManagementApi.entity.AssignmentGradeStudents;
import com.promineotech.classManagementApi.entity.AssignmentsGrades;
import com.promineotech.classManagementApi.entity.Employees;
import com.promineotech.classManagementApi.entity.Parent;
import com.promineotech.classManagementApi.entity.Student;
import com.promineotech.classManagementApi.entity.Classs;

import com.promineotech.classManagementApi.repository.AssignmentsRepository;
import com.promineotech.classManagementApi.repository.AssignmentGradeStudentsRepository;
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
	private StudentRepository studentRepo;
	
	@Autowired
	private AssignmentGradeStudentsRepository gradeRepo;
	
	public AssignmentsGrades getAssignmentById(Long id) {
		return repo.findOne(id);
	}
	
	public Iterable<AssignmentsGrades> getListAssignmentsGrades() {
		return repo.findAll();
	}
	
	
	public AssignmentsGrades newAssignment(AssignmentsGrades assign, Long getClassId) throws Exception {
		try {
			
			Classs findClass = classRepo.findOne(getClassId);
			
			assign.setClassroom(findClass);
			assign.setDate(LocalDate.now());
			return repo.save(assign);
			
		} catch(Exception e) {
			logger.error("Exception occurred while trying to create new assignment for class: " + getClassId, e);
			throw e;
		}
	}
	
	public AssignmentsGrades submitStudentsIntoAssignments(Set<Long> studentId, Long assignId, Long classId) throws Exception {
		AssignmentsGrades getAssignId = repo.findOne(assignId);
		Classs getClass = classRepo.findOne(classId);
		
		try {
			if(getAssignId == null) {
				throw new Exception("Can't find assignment");
			}
			
			AssignmentsGrades assign = initializeNewAssignmentsToStudents(studentId,getAssignId.getId(), getClass.getId());
			return repo.save(assign);
		} catch(Exception e) {
			logger.error("Exception occurred while trying to add student", e);
			throw new Exception("Unable to add students into assignment ");
		}
	}
	
	public AssignmentsGrades updateAssignment(AssignmentsGrades assign, Long id) throws Exception {
		try {
			
			AssignmentsGrades Oldassign = repo.findOne(id);
			Oldassign.setTotalGradeAmount(assign.getTotalGradeAmount());
			Oldassign.setDescription(assign.getDescription());
			return repo.save(Oldassign);
			
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
	
	private AssignmentsGrades initializeNewAssignmentsToStudents(Set<Long> studentIds, Long assignId, Long classId) {
		AssignmentsGrades assign = repo.findOne(assignId);
		Classs classroom = classRepo.findOne(classId);
		
		//add new grades
		for(Long studentId : studentIds) {
			Student student = studentRepo.findOne(studentId);
			student.setLetterGrade(caculateStudentGrade(classroom.getId(),assign.getId(),studentId));
		}
		
		assign.setStudents(convertStudentsToSet(assign.getId(),classroom.getId(),studentRepo.findAll(studentIds)));
		addAssignmentsGradesToStudents(assign);
			
		return assign;
	}
	
	private void addAssignmentsGradesToStudents(AssignmentsGrades assign) {
		Set<Student> students = assign.getStudents();
		for(Student newstudent : students) {
			if(assign.getClassroom() == newstudent.getClasss()) {
					newstudent.getAssignments().add(assign);			
			} 
		}
	}
	
	private Set<Student> convertStudentsToSet(Long assignId, Long classId, Iterable<Student> iterable) {
		Classs classroom = classRepo.findOne(classId);
		AssignmentsGrades assign = repo.findOne(assignId);
		
		
		Set<Student> set = new HashSet<Student>();
		for(Student student : iterable) {
			if(classroom.getId() == assign.getClassroom().getId()) {
				set.add(student);				
			} 
		}
		
		return set;
		
	}
	

private String caculateStudentGrade(Long getClassId, Long assignmentId, Long studentId) {
	Student student = studentRepo.findOne(studentId);
	Classs classId = classRepo.findOne(getClassId);
	AssignmentsGrades assignment = repo.findOne(assignmentId);
	
	Iterable<AssignmentGradeStudents> getGrades = gradeRepo.findAll();
	
	Set<Double> grades = new HashSet<Double>();
	Set<Double> totalPoints = new HashSet<Double>();
	double average = 0;
	double findGrade = 0;
	double total = 0;
	String Letter = "";
	
	//make sure you are pulling grade based on student id
	for(AssignmentGradeStudents findgrade : getGrades) {
		if(findgrade.getAssignmentid() == assignment.getId() && findgrade.getStudentid() == student.getId() && findgrade.getClassid() == classId.getId()) {
			grades.add(findgrade.getAssignmentGrade());
			totalPoints.add(assignment.getTotalGradeAmount());
			
			}
		}
	
		for(Double num : grades) {
			findGrade += num;
		}
	
		for(Double pointTotal : totalPoints) {
			total += pointTotal;
		}
	
		average = (findGrade/total)*100;
	 
	
		if(average >= 90) {
			Letter = "A";
		} else if(average >= 80) {
			Letter = "B";
		} else if(average >= 70) {
			Letter = "C";
		} else if(average >= 60 ) {
			Letter = "D";
		} else {
			Letter = "F";
		}
	
		return Letter;

	} 

	
}
