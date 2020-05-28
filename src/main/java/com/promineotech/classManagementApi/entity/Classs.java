package com.promineotech.classManagementApi.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.promineotech.classManagementApi.entity.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promineotech.classManagementApi.entity.Employees;


@Entity
public class Classs {
	
	private Long id;
	private String className;
	private String classDescription;
	
	@JsonIgnore
	private Set<Employees> teacherName;
	
	private String gradeLevel;
	private int maxNumberofStudents;
	
	@JsonIgnore
	private Set<Student> students;
	
	private LocalDate dateClassCreated;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		
		return id;
	}
	
	public void setId(Long id) {
		
		this.id = id;
	}
	
	public String getClassName() {
		
		return className;
	}

	public void setClassName(String className) {
		
		this.className = className;
	}
	
	

	public String getClassDescription() {
		return classDescription;
	}

	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "teacher_class"
	,joinColumns = @JoinColumn(name = "classId", referencedColumnName = "id")
	,inverseJoinColumns = @JoinColumn(name = "employeeId", referencedColumnName = "id"))
	public Set<Employees> getTeacherName() {
		return teacherName;
	}
	
	
	public void setTeacherName(Set<Employees> teacherName) {
		this.teacherName = teacherName;
	}
	
	public String getGradeLevel() {
		return gradeLevel;
	}
	
	public LocalDate getDateClassCreated() {
		return dateClassCreated;
	}

	public void setDateClassCreated(LocalDate dateClassCreated) {
		this.dateClassCreated = dateClassCreated;
	}

	
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	
	public int getMaxNumberofStudents() {
		return maxNumberofStudents;
	}
	
	public void setMaxNumberofStudents(int maxNumberofStudents) {
		this.maxNumberofStudents = maxNumberofStudents;
	}
	

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "class_student"
	,joinColumns = @JoinColumn(name = "classs", referencedColumnName = "id")
	,inverseJoinColumns = @JoinColumn(name = "student", referencedColumnName = "id"))
	public Set<Student> getStudents() {
		return students;
	}
	
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	

}
