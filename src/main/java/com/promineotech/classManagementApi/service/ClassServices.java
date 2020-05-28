package com.promineotech.classManagementApi.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.classManagementApi.entity.Classs;
import com.promineotech.classManagementApi.entity.Employees;
import com.promineotech.classManagementApi.entity.Student;
import com.promineotech.classManagementApi.repository.ClassRepository;
import com.promineotech.classManagementApi.repository.EmployeesRepository;
import com.promineotech.classManagementApi.repository.ParentRepository;
import com.promineotech.classManagementApi.repository.StudentRepository;

	@Service
	public class ClassServices {
	
	private static final Logger logger = LogManager.getLogger(ClassServices.class);
	
	@Autowired
	private ClassRepository repo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private ParentRepository parentRepo;
	
	@Autowired
	private EmployeesRepository employeeRepo;
	
		
	public Classs getClassById(Long id) throws Exception {
		try {
			
			return repo.findOne(id);
				
		} catch(Exception e) {
			
			logger.error("Exception occurred while trying to retrive class: " + id, e);
			throw new Exception("Unable to find class");
		}
	}
		
	public Iterable<Classs> getClasses() {
		return repo.findAll();
	}
		
	public Classs newClass(Classs getClass) throws Exception {
		getClass.setDateClassCreated(LocalDate.now());
		return repo.save(getClass);
	}
	
//	//list students that are not assigned to a class
//	public Iterable<Student> getAllStudentsClassIsNull() {
//		Iterable<Student> studentIds = studentRepo.findAll(); 
//		Set<Student> list = new HashSet<Student>();
//		for(Student getId : studentIds) {
//			if(getId.getClasss().isEmpty()) {
//				list.add(getId);
//			}
//		}
//		
//		return list;
//	}
	
//Student	
	//list students that need to be assigned in a class
	public Set<String> getAllStudentsClassIsNull() {
		Iterable<Student> students = studentRepo.findAll(); 
		Set<String> list = new HashSet<String>();
		for(Student student : students) {
			if(student.getClasss().isEmpty()) {
				list.add("StudentId: "+student.getId()+", Student Name: "+student.getFirstName()+ " "+student.getLastName()+", Student Grade Level: "+ student.getGradeLevel());
			}
		}
		
		return list;
	}
	
		
	public Classs submitStudentsIntoClass(Set<Long> studentId, Long classId) throws Exception {
		Classs getClassId = repo.findOne(classId);
		
		try {
			if(getClassId == null) {
				throw new Exception("Can't find class");
			}
			
			Classs classroom = initializeNewClassStudents(studentId,getClassId.getId());
			return repo.save(classroom);
			
		} catch(Exception e) {
			logger.error("Exception occurred while tryiing to add student", e);
			throw new Exception("Unable to add students into classroom ");
		}
		
	}
	
//Employees	
	
	public Classs submitEmployeesIntoClass(Set<Long> employeeIds, Long classId) throws Exception {
		Classs getClassId = repo.findOne(classId);
		
		try {
			if(getClassId == null) {
				throw new Exception("Can't find class");
			}
			
			Classs classroom = initializeNewClassTeachers(employeeIds,getClassId.getId());
			return repo.save(classroom);
			
		} catch(Exception e) {
			logger.error("Exception occurred while tryiing to add student", e);
			throw new Exception("Unable to add teacher into classroom ");
		}
		
	}
	
//////	Class level	
	public Classs updateClass(Classs getClass, Long id) throws Exception {
		try {
			
			Classs oldClass = repo.findOne(id);
			oldClass.setClassName(getClass.getClassName());
			oldClass.setClassDescription(getClass.getClassDescription());
			
			return repo.save(oldClass);
			
		} catch(Exception e) {
			logger.error("Exception occurred while trying to update class: " + id, e);
			throw new Exception("Unable to update class");
		}
			
	}

	public void deleteClass(Long id) throws Exception{
		try {
			 repo.delete(id);
		} catch(Exception e) {
			logger.error("Exception occurred while trying to delete class: " + id, e);
			throw new Exception("Unable to delete student");
		}
		
	}
	
//Student
	private Classs initializeNewClassStudents(Set<Long> studentIds, Long classId) {
		Classs classroom = repo.findOne(classId);
		classroom.setStudents(convertStudentsToSet(classId,classroom.getGradeLevel(), studentRepo.findAll(studentIds)));
		addClassesToStudents(classroom, classroom.getGradeLevel());
		return classroom;
	}
	
	//add student to the correct grade level
	private void addClassesToStudents(Classs classroom, String gradeLevel) {
		Set<Student> students = classroom.getStudents();
		for(Student newstudent : students) {
			if(classroom.getGradeLevel().equals(newstudent.getGradeLevel())) {
					newstudent.getClasss().add(classroom);			
			} 
		}
	}
	
	private Set<Student> convertStudentsToSet(Long classId, String gradeLevel, Iterable<Student> iterable) {
		Classs classroom = repo.findOne(classId);
		Set<Student> set = new HashSet<Student>();
		int count = 0;
		
			for(Student student : iterable) {
				if(classroom.getGradeLevel().equals(student.getGradeLevel())) {
					if(count < classroom.getMaxNumberofStudents()) {
						count ++;
							set.add(student);
						
					}
					
				} 
			}
		
		return set;
		
	}
	
//Employee
	private Classs initializeNewClassTeachers(Set<Long> employeeId, Long classId) {
		Classs classroom = repo.findOne(classId);
		classroom.setTeacherName(convertEmployeesToSet(employeeRepo.findAll(employeeId)));
		addClassesToEmployee(classroom);
		return classroom;
	}
	
	//add student to the correct grade level
	private void addClassesToEmployee(Classs classroom) {
		Set<Employees> employees = classroom.getTeacherName();
		for(Employees newEmployee : employees) {
			newEmployee.getListClassroom().add(classroom);			
		}
	}
	
	private Set<Employees> convertEmployeesToSet(Iterable<Employees> iterable) {
		Set<Employees> set = new HashSet<Employees>();
			for(Employees employee : iterable) {
					set.add(employee);
					
			}
		return set;
		
	}

}
