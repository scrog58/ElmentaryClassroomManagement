package com.promineotech.classManagementApi.service;


import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.classManagementApi.entity.AssignmentsGrades;
import com.promineotech.classManagementApi.entity.Parent;
import com.promineotech.classManagementApi.entity.Student;
import com.promineotech.classManagementApi.entity.Classs;

import com.promineotech.classManagementApi.repository.AssignmentsRepository;
import com.promineotech.classManagementApi.repository.ParentRepository;
import com.promineotech.classManagementApi.repository.StudentRepository;
import com.promineotech.classManagementApi.repository.ClassRepository;

@Service
public class StudentServices {
	
private static final Logger logger = LogManager.getLogger(StudentServices.class);
	
@Autowired
private StudentRepository repo;

@Autowired
private ParentRepository parentRepo;

@Autowired
private AssignmentsRepository assignmentRepo;

	
public Student getStudentById(Long id) throws Exception {
	try {
		
		return repo.findOne(id);
			
	} catch(Exception e) {
		
		logger.error("Exception occurred while trying to retrive student: " + id, e);
		throw new Exception("Unable to find student");
	}
}
	
public Iterable<Student> getStudents() {
	return repo.findAll();
}

//can this work
public Set<Student> getAllStudentsClassIsNull() {
	Iterable<Student> students = repo.findAll();
	Set<Student> getStudent = new HashSet<Student>();
	for(Student foundStudent : students) {
		if(foundStudent.getClasss() == null) {
			getStudent.add(foundStudent);
		}
	}
	return getStudent;
}
	
public Student newStudent(Student student, Long parentId) throws Exception {
		try {
			
			Parent parent = parentRepo.findOne(parentId); 
			if(parent == null) {
				throw new Exception("Can't find parent");
			}
			
			int age = calculateAge(student.getBirthDate());
			student.setAge(age);
			student.setParent(parent);
			return repo.save(student);
			
		} catch(Exception e) {
			logger.error("Exception occurred while tyring to add student to parent", e);
			throw new Exception("Unable to add student ");
		}
		
}

	
public Student updateStudent(Student student, Long id) throws Exception {
	try {
		
		Student oldStudent = repo.findOne(id);
		
		oldStudent.setParent(student.getParent());
		oldStudent.setFirstName(student.getFirstName());
		oldStudent.setLastName(student.getLastName());
		oldStudent.setBirthDate(student.getBirthDate());
		oldStudent.setAge(calculateAge(student.getBirthDate()));
		oldStudent.setGradeLevel(student.getGradeLevel());
		oldStudent.setLetterGrade(caculateStudentGrade(student.getAssignments(),id));
		
		return repo.save(oldStudent);
		
	} catch(Exception e) {
		logger.error("Exception occurred while trying to update student: " + id, e);
		throw new Exception("Unable to update student");
	}
		
}

public void deleteStudent(Long id) throws Exception{
	try {
		 repo.delete(id);
	} catch(Exception e) {
		logger.error("Exception occurred while trying to update student: " + id, e);
		throw new Exception("Unable to delete student");
	}
	
}

public Student addingNewStudents(Set<Long> assignmentId, Parent parent) {
	Student student = new Student();
	
	student.setParent(student.getParent());
	student.setFirstName(student.getFirstName());
	student.setLastName(student.getLastName());
	student.setBirthDate(student.getBirthDate());
	student.setAge(calculateAge(student.getBirthDate()));
	student.setGradeLevel(student.getGradeLevel());
	student.setLetterGrade(caculateStudentGrade(student.getAssignments(),student.getId()));
	
	addStudentsToAssignmentsGrade(student);
	return student;
}

public void addStudentsToAssignmentsGrade(Student student) {
	Set<AssignmentsGrades> assignments = student.getAssignments();
	for(AssignmentsGrades assignment :  assignments) {
		assignment.getStudent().add(student);
	}
}

private String caculateStudentGrade(Set<AssignmentsGrades> getGrades, Long studentId) {
	Student student = new Student();
	Set<Double> grades = new HashSet<Double>();
	int average = 0;
	String Letter = "";
	
	//make sure you are pulling grade based on student id
	if(studentId.equals(student.getId())){
		for(AssignmentsGrades findgrade : getGrades) {
		 grades.add(findgrade.getGrade());
		}
	}
	
	for(Double num : grades) {
		average += num;
	}
	
	average = average/grades.size();
	
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
	
private int calculateAge(String date) {
	int age = 0;
	try {
		LocalDate todaysDate = LocalDate.now();
		LocalDate birthDate = LocalDate.parse(date);
		age = (int)Period.between(birthDate, todaysDate).getYears();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return age;

	}


	
	

}
