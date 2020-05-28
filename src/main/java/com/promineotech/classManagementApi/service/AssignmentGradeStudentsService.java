package com.promineotech.classManagementApi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.classManagementApi.entity.AssignmentGradeStudents;
import com.promineotech.classManagementApi.entity.AssignmentsGrades;
import com.promineotech.classManagementApi.entity.Classs;
import com.promineotech.classManagementApi.entity.Employees;
import com.promineotech.classManagementApi.entity.Student;

import com.promineotech.classManagementApi.repository.AssignmentGradeStudentsRepository;
import com.promineotech.classManagementApi.repository.AssignmentsRepository;
import com.promineotech.classManagementApi.repository.ClassRepository;
import com.promineotech.classManagementApi.repository.StudentRepository;

@Service
public class AssignmentGradeStudentsService {
	
	private static final Logger logger = LogManager.getLogger(AssignmentGradeStudentsService.class);
 
	@Autowired
	private AssignmentGradeStudentsRepository repo;
	
	@Autowired
	private ClassRepository classRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private AssignmentsRepository assignRepo;	
	
	public AssignmentGradeStudents addAssignmentStudentInfo(AssignmentGradeStudents grades, Long getClassId, Long assignId, Long studentId) throws Exception{
		try {
			Classs classId =  classRepo.findOne(getClassId);
			AssignmentsGrades assignment = assignRepo.findOne(assignId);
			Student student = studentRepo.findOne(studentId);
			
			
			if(classId == null && assignment == null && student == null) {
				throw new Exception("Can't find assignment or student");
			}
			
			Double checkAssignmentPoints = grades.getAssignmentGrade();
			Double checkTotalAssignmentPoints = assignment.getTotalGradeAmount();
			
			
			//why doesn't this work
			if(checkAssignmentPoints > checkTotalAssignmentPoints) {
				checkAssignmentPoints = assignment.getTotalGradeAmount(); // checkTotalAssigmentPoints
			} else if(checkAssignmentPoints < 0.00) {
				checkAssignmentPoints = 0.00;
			}
			
			grades.setClassid(classId.getId());
			grades.setAssignmentid(assignment.getId());
			grades.setStudentid(student.getId());
			grades.setAssignmentGrade(checkAssignmentPoints);
			grades.setAssignmentTotalGradePoint(assignment.getTotalGradeAmount()); 
			student.setLetterGrade(caculateStudentGrade(classId.getId(), assignment.getId(), student.getId()));
			
			
			return repo.save(grades);
			
		} catch(Exception e) {
			logger.error("Exception occurred while tyring to add student to assignment"+ studentId, e);
			throw new Exception("Unable to add student grade info");
		}
	
	}
	
	
	public AssignmentGradeStudents updateAssignmentGrade(AssignmentGradeStudents grades, Long gradeId) throws Exception {
		try {
			
			AssignmentGradeStudents oldGrade = repo.findOne(gradeId);
			Student student = studentRepo.findOne(oldGrade.getStudentid());
			
			Double checkAssignmentPoints = grades.getAssignmentGrade();
			Double checkTotalAssignmentPoints = oldGrade.getAssignmentTotalGradePoint();
			
			
			if(checkAssignmentPoints > checkTotalAssignmentPoints) {
				checkAssignmentPoints = checkTotalAssignmentPoints; // checkTotalAssigmentPoints
			} else if(checkAssignmentPoints < 0.00) {
				checkAssignmentPoints = 0.00;
			}
			
			oldGrade.setAssignmentGrade(checkAssignmentPoints);
			
			student.setLetterGrade(caculateStudentGrade(oldGrade.getClassid(), oldGrade.getAssignmentid(), oldGrade.getStudentid()));	
			return repo.save(oldGrade);
							
		} catch(Exception e) {
			logger.error("Exception occurred while trying to update assignment grades: " + gradeId, e);
			throw new Exception("Unable to update assignment grades");
		}	
		
	}
	
	private String caculateStudentGrade(Long getClassId, Long assignmentId, Long studentId) {
		Student student = studentRepo.findOne(studentId);
		Classs classId = classRepo.findOne(getClassId);

		
		Iterable<AssignmentGradeStudents> getGrades = repo.findAll();

		
		Set<Double> grades = new HashSet<Double>();
		Set<Double> totalPoints = new HashSet<Double>();
		double average = 0;
		double findGrade = 0;
		double total = 0;
		String Letter = "";
		
		//make sure you are pulling grade based on student id
		for(AssignmentGradeStudents foundGrade : getGrades) {
			if(foundGrade.getStudentid() == student.getId() && foundGrade.getClassid() == classId.getId()) {
				grades.add(foundGrade.getAssignmentGrade());
				totalPoints.add(foundGrade.getAssignmentTotalGradePoint());
				
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
